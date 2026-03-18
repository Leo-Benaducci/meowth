package br.com.lbenaducci.meowth.features.category.infrastructure.persistence

import br.com.lbenaducci.meowth.features.category.domain.datatypes.CategoryType
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.time.Instant
import java.util.*

@Document(collection = "categories")
class CategoryMongoEntity(
    @Id
    var id: UUID,
    @Field(name = "created_at")
    var createdAt: Instant,
    var name: String,
    var type: CategoryType,
    var icon: String,
    var color: String
)