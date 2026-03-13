package br.com.lbenaducci.meowth.category.domain.valueobjects

import br.com.lbenaducci.meowth.category.domain.errors.ErrorCatalog
import br.com.lbenaducci.meowth.category.domain.exceptions.ValidationException

@JvmInline
value class CategoryIcon(
    val value: String
) {
    init {
        if (value.isBlank()) {
            throw ValidationException(ErrorCatalog.ICON_BLANK)
        }
        if (value.length > 30) {
            throw ValidationException(ErrorCatalog.ICON_LONG)
        }
    }
}
