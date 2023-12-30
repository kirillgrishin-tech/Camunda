package com.grishin.camunda.worker

import com.grishin.camunda.dto.ProcessVariables
import io.camunda.zeebe.client.ZeebeClient
import io.camunda.zeebe.client.api.response.ActivatedJob
import io.camunda.zeebe.client.api.worker.JobClient
import io.camunda.zeebe.spring.client.annotation.JobWorker
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class SendCompleteSignalWorker {

    @Autowired
    lateinit var zeebeClient: ZeebeClient

    @JobWorker(autoComplete = false)
    fun sendCompleteSignal(client: JobClient, job: ActivatedJob) {
        client
                .newCompleteCommand(job)
                .send()
                .thenApply<Any?> { null }
                .exceptionally { t: Throwable -> throw RuntimeException("Could not complete job: " + t.message, t) }
        val variables = job.getVariablesAsType(ProcessVariables::class.java)
        zeebeClient
                .newPublishMessageCommand()
                .messageName("end-parent")
                .correlationKey(variables.businessKey)
                .send()
    }

}
