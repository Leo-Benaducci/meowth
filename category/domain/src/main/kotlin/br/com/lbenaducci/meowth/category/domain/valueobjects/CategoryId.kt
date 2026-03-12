package br.com.lbenaducci.meowth.category.domain.valueobjects

import br.com.lbenaducci.meowth.category.domain.errors.ErrorCatalog
import br.com.lbenaducci.meowth.category.domain.exceptions.ValidationException
import com.github.f4b6a3.uuid.alt.GUID
import java.util.*

@JvmInline
value class CategoryId private constructor(
    val value: UUID
) {
    companion object {
        @JvmStatic
        fun generate(): CategoryId {
            return with(GUID.v7().toUUID())
        }

        @JvmStatic
        fun with(uuid: UUID): CategoryId {
            if (uuid.version() != 7) {
                throw ValidationException(ErrorCatalog.UUID_VERSION)
            }
            return CategoryId(uuid)
        }

        @JvmStatic
        fun with(id: String): CategoryId {
            try {
                return CategoryId(UUID.fromString(id))
            } catch (_: IllegalArgumentException) {
                throw ValidationException(ErrorCatalog.UUID_INVALID)
            }
        }
    }
}