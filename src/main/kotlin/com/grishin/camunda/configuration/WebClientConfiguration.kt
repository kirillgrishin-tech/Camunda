package com.grishin.camunda.configuration

import com.mongodb.reactivestreams.client.MongoClient
import com.mongodb.reactivestreams.client.MongoClients
import io.netty.channel.ChannelOption
import io.netty.handler.timeout.ReadTimeoutHandler
import io.netty.handler.timeout.WriteTimeoutHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.Connection
import reactor.netty.http.client.HttpClient
import java.util.concurrent.TimeUnit


@Configuration
class WebClientConfiguration {
    private val BASE_URL = "http://pega-tomcat.default.svc.cluster.local:8080/prweb/api/v1/cases"
    private val LOGIN = "CLMService"
    private val PASSWORD = "1"
    val TIMEOUT = 10000

    @Bean
    fun webClientWithTimeout(): WebClient {
        val tcpClient = HttpClient
            .create()
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, TIMEOUT)
            .doOnConnected { connection: Connection ->
                connection.addHandlerLast(ReadTimeoutHandler(TIMEOUT.toLong(), TimeUnit.MILLISECONDS))
                connection.addHandlerLast(WriteTimeoutHandler(TIMEOUT.toLong(), TimeUnit.MILLISECONDS))
            }
        return WebClient.builder()
            .baseUrl(BASE_URL)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .defaultHeaders { it.setBasicAuth(LOGIN, PASSWORD) }
            .clientConnector(ReactorClientHttpConnector(tcpClient))
            .build()
    }

}