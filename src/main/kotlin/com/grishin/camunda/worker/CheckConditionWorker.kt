package com.grishin.camunda.worker

import com.grishin.camunda.dto.WaitForConditionDto
import io.camunda.zeebe.client.api.response.ActivatedJob
import io.camunda.zeebe.client.api.worker.JobClient
import kotlinx.coroutines.future.await
import org.camunda.community.extension.coworker.spring.annotation.Coworker
import org.springframework.stereotype.Component

@Component
class CheckConditionWorker {

    @Coworker
    suspend fun checkCondition(client: JobClient, job: ActivatedJob) {
        val waitForConditionDto = job.getVariablesAsType(WaitForConditionDto::class.java)
        when (waitForConditionDto.condition) {
            "AllChildProcessesCompleted" -> waitForConditionDto.success = true
            else -> waitForConditionDto.success = false
        }
        client.newCompleteCommand(job.key).variables(waitForConditionDto).send().await()
    }

}
