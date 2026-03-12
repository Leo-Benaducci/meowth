package br.com.lbenaducci.meowth.category.domain.valueobjects

import br.com.lbenaducci.meowth.category.domain.errors.ErrorCatalog
import br.com.lbenaducci.meowth.category.domain.exceptions.ValidationException

@JvmInline
value class CategoryName private constructor(
    val value: String
) {
    companion object {
        @JvmStatic
        fun with(value: String): CategoryName {
            if(value.isBlank()) {
                throw ValidationException(ErrorCatalog.NAME_BLANK)
            }
            if(value.length < 3) {
                throw ValidationException(ErrorCatalog.NAME_SHORT)
            }
            if(value.length > 30) {
                throw ValidationException(ErrorCatalog.NAME_LONG)
            }
            return CategoryName(value)
        }
    }
}