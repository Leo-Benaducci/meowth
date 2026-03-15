package br.com.lbenaducci.meowth.infrastructure.category.persistence

import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

interface CategoryRepository : MongoRepository<CategoryMongoEntity, UUID>