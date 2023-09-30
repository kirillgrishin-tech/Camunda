package com.grishin.camunda.worker

import com.grishin.camunda.dto.Product
import io.camunda.zeebe.client.ZeebeClient
import io.camunda.zeebe.client.api.response.ActivatedJob
import io.camunda.zeebe.client.api.worker.JobClient
import io.camunda.zeebe.spring.client.annotation.JobWorker
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class InitWorker {

    @Autowired
    lateinit var zeebeClient: ZeebeClient

    @JobWorker
    @Throws(InterruptedException::class)
    fun init(client: JobClient, job: ActivatedJob): Product? {
        val product = job.getVariablesAsType(Product::class.java)
        zeebeClient
                .newPublishMessageCommand()
                .messageName("UpdatedCMDs")
                .correlationKey(product.parentId)
                .send()
        return product // new object to avoid sending unchanged variables
    }

}
