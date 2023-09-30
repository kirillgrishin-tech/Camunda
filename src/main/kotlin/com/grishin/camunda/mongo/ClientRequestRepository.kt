package com.grishin.camunda.mongo

import com.grishin.camunda.dto.ClientRequest
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository

@Repository
interface ClientRequestRepository : ReactiveMongoRepository<ClientRequest, String>
