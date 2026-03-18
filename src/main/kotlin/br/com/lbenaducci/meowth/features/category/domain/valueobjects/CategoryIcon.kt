package br.com.lbenaducci.meowth.features.category.domain.valueobjects

import br.com.lbenaducci.meowth.features.category.domain.errors.CategoryErrorCatalog
import br.com.lbenaducci.meowth.shared.exceptions.ValidationException

@JvmInline
value class CategoryIcon(
    val value: String
) {
    init {
        if (value.isBlank()) {
            throw ValidationException(CategoryErrorCatalog.ICON_BLANK)
        }
        if (value.length > 30) {
            throw ValidationException(CategoryErrorCatalog.ICON_LONG)
        }
    }
}
