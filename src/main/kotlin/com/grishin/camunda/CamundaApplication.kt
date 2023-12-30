package com.grishin.camunda

import io.camunda.zeebe.spring.client.EnableZeebeClient
import io.camunda.zeebe.spring.client.annotation.Deployment
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories

@EnableReactiveMongoRepositories
@EnableZeebeClient
@Deployment(resources = ["classpath*:/models/*.*"])
@SpringBootApplication
class CamundaApplication

fun main(args: Array<String>) {
	runApplication<CamundaApplication>(*args)
}
