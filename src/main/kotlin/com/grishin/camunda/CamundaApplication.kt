package com.grishin.camunda

import com.mongodb.reactivestreams.client.MongoClient
import io.camunda.zeebe.spring.client.EnableZeebeClient
import io.camunda.zeebe.spring.client.annotation.Deployment
import io.camunda.zeebe.spring.client.properties.ZeebeClientConfigurationProperties
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.mongo.MongoProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories

//@EnableAutoConfiguration
@EnableReactiveMongoRepositories
//@EnableConfigurationProperties(ZeebeClientConfigurationProperties::class,MongoProperties::class)
@EnableZeebeClient
@Deployment(resources = ["classpath*:/models/*.*"])
@SpringBootApplication
class CamundaApplication

fun main(args: Array<String>) {
	runApplication<CamundaApplication>(*args)
}
