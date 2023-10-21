package com.grishin.camunda

import io.camunda.zeebe.spring.client.EnableZeebeClient
import io.camunda.zeebe.spring.client.annotation.Deployment
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories

@SpringBootApplication
@EnableReactiveMongoRepositories
@EnableAutoConfiguration
@EnableZeebeClient
@Deployment(resources = ["classpath*:/models/*.*"])
class CamundaApplication

fun main(args: Array<String>) {
	runApplication<CamundaApplication>(*args)
}
