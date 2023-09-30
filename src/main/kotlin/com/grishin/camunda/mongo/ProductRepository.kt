package com.grishin.camunda.mongo


import com.grishin.camunda.dto.Product
import org.springframework.data.mongodb.repository.Query
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
interface ProductRepository : ReactiveMongoRepository<Product?, String?> {
    @Query(value = "{parentId:'?0'}")
    fun findByParentId(parentId: String?): Flux<Product?>?
}
