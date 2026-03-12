package br.com.lbenaducci.meowth.category.domain.errors

enum class ErrorCatalog(
    val code: String,
    val defaultMessage: String
) {
    UUID_VERSION("invalid.category.uuid.version", "'category id' must be UUID version 7"),
    UUID_INVALID("invalid.category.uuid", "'category id' must be a valid UUID"),
    NAME_BLANK("invalid.category.name.blank", "'category name' cannot be blank"),
    NAME_SHORT("invalid.category.name.short", "'category name' must be 3 characters or more"),
    NAME_LONG("invalid.category.name.long", "'category name' must be 30 characters or less")
}