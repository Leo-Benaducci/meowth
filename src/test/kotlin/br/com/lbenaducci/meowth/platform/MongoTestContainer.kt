package br.com.lbenaducci.meowth.platform

import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.mongodb.MongoDBContainer

@Testcontainers
abstract class MongoTestContainer {
    companion object {
        @Container
        private val MONGO_CONTAINER = MongoDBContainer("mongo:latest")
            .withReuse(true)
            .withReplicaSet()

        @JvmStatic
        @DynamicPropertySource
        fun setDataSourceProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.mongodb.uri", MONGO_CONTAINER::getReplicaSetUrl)
        }
    }
}