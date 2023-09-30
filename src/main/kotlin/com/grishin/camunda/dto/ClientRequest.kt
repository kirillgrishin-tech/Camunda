package com.grishin.camunda.dto

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("clientRequest")
data class ClientRequest(@Id var id: String?, val products: MutableList<Product?>?, val businessKey: String?)
