package br.com.lbenaducci.meowth.platform

import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.test.context.junit.jupiter.SpringExtension

class CleanUpMongoExtension : BeforeEachCallback {
    override fun beforeEach(context: ExtensionContext) {
        SpringExtension.getApplicationContext(context)
            .getBeansOfType(MongoRepository::class.java)
            .values.forEach { it.deleteAll() }
    }
}