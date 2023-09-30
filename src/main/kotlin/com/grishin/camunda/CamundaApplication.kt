package com.grishin.camunda

import io.camunda.zeebe.client.ZeebeClient
import io.camunda.zeebe.spring.client.EnableZeebeClient
import io.camunda.zeebe.spring.client.annotation.Deployment
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories

@SpringBootApplication
@EnableZeebeClient
@EnableReactiveMongoRepositories
@Deployment(resources = ["classpath*:/models/*.*"])
class CamundaApplication

fun main(args: Array<String>) {
	runApplication<CamundaApplication>(*args)
}
