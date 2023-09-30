package com.grishin.camunda.dto

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("products")
data class Product(@Id val id: String?, var processId: String?, var parentId: String?, val clientType: String?)
