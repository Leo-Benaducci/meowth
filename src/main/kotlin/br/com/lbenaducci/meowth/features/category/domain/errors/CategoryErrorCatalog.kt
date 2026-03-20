package br.com.lbenaducci.meowth.features.category.domain.errors

import br.com.lbenaducci.meowth.shared.domain.errors.ErrorCatalog

enum class CategoryErrorCatalog(
    override val code: String,
    override val defaultMessage: String
) : ErrorCatalog {
    UUID_VERSION("invalid.category.uuid.version", "category id must be UUID version 7"),
    UUID_INVALID("invalid.category.uuid", "category id must be a valid UUID"),
    NAME_BLANK("invalid.category.name.blank", "category name cannot be blank"),
    NAME_SHORT("invalid.category.name.short", "category name must be 3 characters or more"),
    NAME_LONG("invalid.category.name.long", "category name must be 30 characters or less"),
    COLOR_BLANK("invalid.category.color.blank", "category color cannot be blank"),
    COLOR_INVALID("invalid.category.color", "category color must be a valid hex color code"),
    ICON_BLANK("invalid.category.icon.blank", "category icon cannot be blank"),
    ICON_LONG("invalid.category.icon.long", "category icon must be 30 characters or less"),
    REPOSITORY_ERROR("error.category.repository.unexpected", "an unexpected error occurred in the category repository")
}