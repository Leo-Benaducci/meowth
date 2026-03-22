package br.com.lbenaducci.meowth.features.category.domain.valueobjects

import br.com.lbenaducci.meowth.features.category.domain.errors.CategoryErrorCatalog
import br.com.lbenaducci.meowth.shared.domain.exceptions.ValidationException
import com.github.f4b6a3.uuid.alt.GUID
import java.util.*

private const val FIELD = "category.id"

@JvmInline
value class CategoryId(
    val value: UUID
) {
    init {
        if (value.version() != 7) {
            throw ValidationException(CategoryErrorCatalog.UUID_VERSION, FIELD, value)
        }
    }

    constructor(id: String) : this(
        try {
            UUID.fromString(id)
        } catch (_: IllegalArgumentException) {
            throw ValidationException(CategoryErrorCatalog.UUID_INVALID, FIELD, id)
        }
    )

    companion object {
        fun generate(): CategoryId {
            return CategoryId(GUID.v7().toUUID())
        }
    }
}