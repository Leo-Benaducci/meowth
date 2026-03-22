package br.com.lbenaducci.meowth.features.category.domain.valueobjects

import br.com.lbenaducci.meowth.features.category.domain.errors.CategoryErrorCatalog
import br.com.lbenaducci.meowth.shared.domain.exceptions.ValidationException

private const val FIELD = "category.appearance.color"

@JvmInline
value class CategoryColor(
    val value: String
) {
    init {
        if (value.isBlank()) {
            throw ValidationException(CategoryErrorCatalog.COLOR_BLANK, FIELD)
        }
        if (!Regex("^#([A-Fa-f0-9]{3}|[A-Fa-f0-9]{6})$").matches(value)) {
            throw ValidationException(CategoryErrorCatalog.COLOR_INVALID, FIELD, value)
        }
    }
}
