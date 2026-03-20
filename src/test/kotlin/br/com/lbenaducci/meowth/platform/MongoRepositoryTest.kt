package br.com.lbenaducci.meowth.platform

import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.data.mongodb.test.autoconfigure.DataMongoTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.FilterType
import org.springframework.test.context.ActiveProfiles
import java.lang.annotation.Inherited

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Inherited
@DataMongoTest
@ActiveProfiles("test")
@ComponentScan(
    includeFilters = [
        ComponentScan.Filter(type = FilterType.REGEX, pattern = [".[MongoGateway]"])
    ]
)
@ExtendWith(CleanUpMongoExtension::class)
annotation class MongoRepositoryTest