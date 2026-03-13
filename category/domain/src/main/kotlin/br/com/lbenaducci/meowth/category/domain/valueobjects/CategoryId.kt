package br.com.lbenaducci.meowth.category.domain.valueobjects

import br.com.lbenaducci.meowth.category.domain.errors.ErrorCatalog
import br.com.lbenaducci.meowth.category.domain.exceptions.ValidationException
import com.github.f4b6a3.uuid.alt.GUID
import java.util.*

@JvmInline
value class CategoryId(
    val value: UUID
) {
    init {
        if (value.version() != 7) {
            throw ValidationException(ErrorCatalog.UUID_VERSION)
        }
    }

    constructor(id: String) : this(
        try {
            UUID.fromString(id)
        } catch (_: IllegalArgumentException) {
            throw ValidationException(ErrorCatalog.UUID_INVALID)
        }
    )

    companion object {
        fun generate(): CategoryId {
            return CategoryId(GUID.v7().toUUID())
        }
    }
}