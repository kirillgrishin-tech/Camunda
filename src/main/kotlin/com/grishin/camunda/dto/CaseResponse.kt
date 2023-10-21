package com.grishin.camunda.dto

import com.fasterxml.jackson.annotation.JsonAlias

data class CaseResponse(@JsonAlias("ID") val id: String)
