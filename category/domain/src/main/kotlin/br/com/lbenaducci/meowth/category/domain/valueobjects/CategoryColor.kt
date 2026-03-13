package br.com.lbenaducci.meowth.category.domain.valueobjects

import br.com.lbenaducci.meowth.category.domain.errors.CategoryErrorCatalog
import br.com.lbenaducci.meowth.shared.exceptions.ValidationException

@JvmInline
value class CategoryColor(
    val value: String
) {
    init {
        if (value.isBlank()) {
            throw ValidationException(CategoryErrorCatalog.COLOR_BLANK)
        }
        if (!Regex("^#([A-Fa-f0-9]{3}|[A-Fa-f0-9]{6})$").matches(value)) {
            throw ValidationException(CategoryErrorCatalog.COLOR_INVALID)
        }
    }
}
