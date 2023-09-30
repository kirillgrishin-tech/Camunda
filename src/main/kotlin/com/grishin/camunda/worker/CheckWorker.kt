package com.grishin.camunda.worker

import com.grishin.camunda.dto.Product
import io.camunda.zeebe.client.api.response.ActivatedJob
import io.camunda.zeebe.client.api.worker.JobClient
import io.camunda.zeebe.spring.client.annotation.JobWorker
import org.springframework.stereotype.Component

@Component
class CheckWorker {

    @JobWorker
    @Throws(InterruptedException::class)
    fun check(client: JobClient, job: ActivatedJob) {
        val product = job.getVariablesAsType(Product::class.java)
    }

}
