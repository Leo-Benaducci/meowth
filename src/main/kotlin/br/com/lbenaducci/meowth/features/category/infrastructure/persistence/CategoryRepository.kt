package br.com.lbenaducci.meowth.features.category.infrastructure.persistence

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CategoryRepository : MongoRepository<CategoryMongoEntity, UUID>