package br.com.lbenaducci.meowth.platform

import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.mongodb.MongoDBContainer

abstract class MongoTestContainer {
    companion object {
        private val MONGO_CONTAINER = MongoDBContainer("mongo:latest")
            .withReuse(true)
            .withReplicaSet()
            .apply { start() }

        @JvmStatic
        @DynamicPropertySource
        fun setDataSourceProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.mongodb.uri", MONGO_CONTAINER::getReplicaSetUrl)
        }
    }
}