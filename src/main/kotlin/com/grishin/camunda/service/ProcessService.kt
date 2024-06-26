package com.grishin.camunda.service

import com.grishin.camunda.dto.ClientRequest
import com.grishin.camunda.dto.ProcessVariables
import com.grishin.camunda.mongo.ClientRequestRepository
import io.camunda.zeebe.client.ZeebeClient
import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.concurrent.ExecutionException

@Service
@RequiredArgsConstructor
class ProcessService {

    @Autowired
    lateinit var clientRequestRepository: ClientRequestRepository

    @Autowired
    lateinit var zeebeClient: ZeebeClient

    fun myOperation(businessKey: String?): Boolean {
        return true
    }

    @Throws(ExecutionException::class, InterruptedException::class)
    fun create(variables: ProcessVariables): ProcessVariables? {
        val processInstanceId = zeebeClient
                .newCreateInstanceCommand()
                .bpmnProcessId("camunda-process")
                .latestVersion()
                .variables(variables)
                .send()
                .get()
                .processInstanceKey
        clientRequestRepository
            .insert(ClientRequest(
                products = variables.products,
                businessKey = processInstanceId.toString()))
            .subscribe()
        return ProcessVariables(
            businessKey = processInstanceId.toString()
        )
    }

    fun publish(messageName: String?, correlationKey: String?, variables: ProcessVariables?) {
        zeebeClient
                .newPublishMessageCommand()
                .messageName(messageName)
                .correlationKey(correlationKey)
                .variables(variables)
                .send()
    }
}
