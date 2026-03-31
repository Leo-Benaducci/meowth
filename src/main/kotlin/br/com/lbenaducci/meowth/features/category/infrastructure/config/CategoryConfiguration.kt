package br.com.lbenaducci.meowth.features.category.infrastructure.config

import br.com.lbenaducci.meowth.features.category.application.usecases.create.CreateCategoryUseCase
import br.com.lbenaducci.meowth.features.category.application.usecases.retrieve.get.FindCategoryByIdUseCase
import br.com.lbenaducci.meowth.features.category.application.usecases.update.UpdateCategoryUseCase
import br.com.lbenaducci.meowth.features.category.infrastructure.persistence.CategoryMongoGateway
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CategoryConfiguration {
    @Bean
    fun createCategory(gateway: CategoryMongoGateway): CreateCategoryUseCase {
        return CreateCategoryUseCase(gateway)
    }

    @Bean
    fun findCategoryById(gateway: CategoryMongoGateway): FindCategoryByIdUseCase {
        return FindCategoryByIdUseCase(gateway)
    }

    @Bean
    fun updateCategoryById(gateway: CategoryMongoGateway): UpdateCategoryUseCase {
        return UpdateCategoryUseCase(gateway)
    }
}