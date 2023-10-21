package com.grishin.camunda.service

import com.grishin.camunda.dto.Case
import com.grishin.camunda.dto.ClientRequest
import com.grishin.camunda.dto.ProcessVariables
import com.grishin.camunda.mongo.ClientRequestRepository
import io.camunda.zeebe.client.ZeebeClient
import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
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
        val clientRequest = clientRequestRepository
                .insert(ClientRequest(products = variables.products, id =null, businessKey = variables.businessKey))
        val processInstanceId = zeebeClient
                .newCreateInstanceCommand()
                .bpmnProcessId("camunda-process")
                .latestVersion()
                .variables(variables)
                .send()
                .get()
                .processInstanceKey
        clientRequest.subscribe {
            clientRequestDB: ClientRequest ->
            clientRequestRepository.save(ClientRequest(
                    products =clientRequestDB.products,
                    id =clientRequestDB.id,
                    businessKey = clientRequestDB.businessKey))
        }
        return ProcessVariables(
                businessKey = processInstanceId.toString(),
                result = null,
                products = null,
                check = null,
                count = null
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
