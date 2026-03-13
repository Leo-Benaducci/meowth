package br.com.lbenaducci.meowth.category.domain.valueobjects

import br.com.lbenaducci.meowth.category.domain.errors.ErrorCatalog
import br.com.lbenaducci.meowth.category.domain.exceptions.ValidationException

@JvmInline
value class CategoryColor(
    val value: String
) {
    init {
        if (value.isBlank()) {
            throw ValidationException(ErrorCatalog.COLOR_BLANK)
        }
        if (!Regex("^#([A-Fa-f0-9]{3}|[A-Fa-f0-9]{6})$").matches(value)) {
            throw ValidationException(ErrorCatalog.COLOR_INVALID)
        }
    }
}
