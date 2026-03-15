package br.com.lbenaducci.meowth.infrastructure

import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.ExtensionContext
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.FilterType
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.lang.annotation.Inherited

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Inherited
@SpringBootTest
@ActiveProfiles("test")
@ComponentScan(
    includeFilters = [
        ComponentScan.Filter(type = FilterType.REGEX, pattern = [".*MongoGateway"])
    ]
)
@ExtendWith(MongoRepositoryTest.CleanUpMongoExtension::class)
annotation class MongoRepositoryTest {
    class CleanUpMongoExtension : BeforeEachCallback {
        override fun beforeEach(context: ExtensionContext) {
            SpringExtension.getApplicationContext(context)
                .getBeansOfType(MongoRepository::class.java)
                .values.forEach { it.deleteAll() }
        }
    }
}