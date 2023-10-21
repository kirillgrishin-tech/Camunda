package com.grishin.camunda.controller

import com.grishin.camunda.dto.ProcessVariables
import com.grishin.camunda.service.ProcessService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import reactor.kotlin.core.publisher.toMono
import java.util.concurrent.ExecutionException

@RestController
@RequestMapping("/process")
class ProcessController {

    @Autowired
    lateinit var processService: ProcessService

    @PostMapping("/start")
    @Throws(ExecutionException::class, InterruptedException::class)
    suspend fun startProcessInstance(@RequestBody variables: ProcessVariables?): ProcessVariables? {
        return processService.create(variables!!)
    }

    @PostMapping("/message/{messageName}/{correlationKey}")
    suspend fun publishMessage(
            @PathVariable messageName: String?,
            @PathVariable correlationKey: String?,
            @RequestBody variables: ProcessVariables?) {
        LOG.info(
                "Publishing message `{}` with correlation key `{}` and variables: {}",
                messageName,
                correlationKey,
                variables)
            .toMono()
            .subscribe()
        processService.publish(messageName, correlationKey, variables)
    }

    companion object {
        private val LOG = LoggerFactory.getLogger(ProcessController::class.java)
    }
}
