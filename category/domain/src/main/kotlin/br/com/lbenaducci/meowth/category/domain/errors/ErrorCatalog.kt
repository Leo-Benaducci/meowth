package br.com.lbenaducci.meowth.category.domain.errors

enum class ErrorCatalog(
    val code: String,
    val defaultMessage: String
) {
    UUID_VERSION("invalid.category.uuid.version", "'category id' must be UUID version 7"),
    UUID_INVALID("invalid.category.uuid", "'category id' must be a valid UUID")
}