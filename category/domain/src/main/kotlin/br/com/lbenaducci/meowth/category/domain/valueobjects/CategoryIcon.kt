package br.com.lbenaducci.meowth.category.domain.valueobjects

import br.com.lbenaducci.meowth.category.domain.errors.ErrorCatalog
import br.com.lbenaducci.meowth.category.domain.exceptions.ValidationException

@JvmInline
value class CategoryIcon private constructor(
    val value: String
) {
    companion object {
        @JvmStatic
        fun with(value: String): CategoryIcon {
            if (value.isBlank()) {
                throw ValidationException(ErrorCatalog.ICON_BLANK)
            }
            if (value.length > 30) {
                throw ValidationException(ErrorCatalog.ICON_LONG)
            }
            return CategoryIcon(value)
        }
    }
}
