package br.com.lbenaducci.meowth.features.category.domain.valueobjects

import br.com.lbenaducci.meowth.features.category.domain.errors.CategoryErrorCatalog
import br.com.lbenaducci.meowth.shared.exceptions.ValidationException

@JvmInline
value class CategoryName(
    val value: String
) {
    init {
        if (value.isBlank()) {
            throw ValidationException(CategoryErrorCatalog.NAME_BLANK)
        }
        if (value.length < 3) {
            throw ValidationException(CategoryErrorCatalog.NAME_SHORT)
        }
        if (value.length > 30) {
            throw ValidationException(CategoryErrorCatalog.NAME_LONG)
        }
    }
}