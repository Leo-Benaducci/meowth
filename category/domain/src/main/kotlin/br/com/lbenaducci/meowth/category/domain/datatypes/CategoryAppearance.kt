package br.com.lbenaducci.meowth.category.domain.datatypes

import br.com.lbenaducci.meowth.category.domain.valueobjects.CategoryColor
import br.com.lbenaducci.meowth.category.domain.valueobjects.CategoryIcon

class CategoryAppearance private constructor(
    val icon: CategoryIcon,
    val color: CategoryColor
) {
    companion object {
        fun with(icon: String, color: String): CategoryAppearance {
            return CategoryAppearance(
                icon = CategoryIcon(icon),
                color = CategoryColor(color)
            )
        }
    }
}