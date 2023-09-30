package com.grishin.camunda.worker

import com.grishin.camunda.dto.ProcessVariables
import com.grishin.camunda.mongo.ProductRepository
import com.grishin.camunda.service.ProcessService
import io.camunda.zeebe.client.ZeebeClient
import io.camunda.zeebe.client.api.response.ActivatedJob
import io.camunda.zeebe.client.api.worker.JobClient
import kotlinx.coroutines.future.await
import org.camunda.community.extension.coworker.spring.annotation.Coworker
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import reactor.kotlin.core.publisher.toMono

@Component
class StartProductProcessesWorker {

    @Autowired
    private lateinit var processService: ProcessService

    @Autowired
    private lateinit var productRepository: ProductRepository

    @Autowired
    lateinit var zeebeClient: ZeebeClient

    @Coworker(type = "startProductProcesses", name = "startProductProcesses")
    suspend fun startProductProcesses(client: JobClient, job: ActivatedJob) {
        val variables = job.getVariablesAsType(ProcessVariables::class.java)
        variables.businessKey = job.processInstanceKey.toString()
        variables.result = processService.myOperation(variables.businessKey)
        LOG.info("Test").toMono().subscribe()
        variables.products
                ?.forEach { product ->
                    product?.parentId = job.processInstanceKey.toString()
                    val processInstanceId = zeebeClient
                            .newCreateInstanceCommand()
                            .bpmnProcessId(product?.processId)
                            .latestVersion()
                            .variables(product)
                            .send()
                            .get()
                            .processInstanceKey
                    product?.processId = processInstanceId.toString()
                }
        variables.products?.let { productRepository.saveAll(it).subscribe() }
        variables.count = variables.products?.size
        client.newCompleteCommand(job.key).variables(variables).send().await()
    }

    companion object {
        private val LOG = LoggerFactory.getLogger(StartProductProcessesWorker::class.java)
    }
}
