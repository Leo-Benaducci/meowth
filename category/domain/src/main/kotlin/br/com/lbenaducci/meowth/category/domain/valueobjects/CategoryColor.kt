package br.com.lbenaducci.meowth.category.domain.valueobjects

import br.com.lbenaducci.meowth.category.domain.errors.ErrorCatalog
import br.com.lbenaducci.meowth.category.domain.exceptions.ValidationException

class CategoryColor private constructor(
    val value: String
) {
    companion object {
        private val REGEX = Regex("^#([A-Fa-f0-9]{3}|[A-Fa-f0-9]{6})$")

        @JvmStatic
        fun with(value: String): CategoryColor {
            if (value.isBlank()) {
                throw ValidationException(ErrorCatalog.COLOR_BLANK)
            }
            if (!REGEX.matches(value)) {
                throw ValidationException(ErrorCatalog.COLOR_INVALID)
            }
            return CategoryColor(value.uppercase())
        }
    }
}
