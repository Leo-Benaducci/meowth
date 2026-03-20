package br.com.lbenaducci.meowth.platform

import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import java.lang.annotation.Inherited

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Inherited
@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(CleanUpMongoExtension::class)
annotation class IntegrationTest