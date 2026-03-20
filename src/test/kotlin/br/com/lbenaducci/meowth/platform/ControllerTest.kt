package br.com.lbenaducci.meowth.platform

import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest
import org.springframework.core.annotation.AliasFor
import org.springframework.test.context.ActiveProfiles
import java.lang.annotation.Inherited
import kotlin.reflect.KClass

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Inherited
@WebMvcTest
@ActiveProfiles("test")
@ExtendWith(CleanUpMongoExtension::class)
annotation class ControllerTest(
    @get:AliasFor(annotation = WebMvcTest::class, attribute = "controllers")
    val controllers: Array<KClass<*>> = []

)