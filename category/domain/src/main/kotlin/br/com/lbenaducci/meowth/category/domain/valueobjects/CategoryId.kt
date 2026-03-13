package br.com.lbenaducci.meowth.category.domain.valueobjects

import br.com.lbenaducci.meowth.category.domain.errors.CategoryErrorCatalog
import br.com.lbenaducci.meowth.shared.exceptions.ValidationException
import com.github.f4b6a3.uuid.alt.GUID
import java.util.*

@JvmInline
value class CategoryId(
    val value: UUID
) {
    init {
        if (value.version() != 7) {
            throw ValidationException(CategoryErrorCatalog.UUID_VERSION)
        }
    }

    constructor(id: String) : this(
        try {
            UUID.fromString(id)
        } catch (_: IllegalArgumentException) {
            throw ValidationException(CategoryErrorCatalog.UUID_INVALID)
        }
    )

    companion object {
        fun generate(): CategoryId {
            return CategoryId(GUID.v7().toUUID())
        }
    }
}