package br.com.lbenaducci.meowth.shared.domain.errors

data class ErrorDetail(
    val error: ErrorCatalog,
    val field: String?,
    val rejectedValue: Any?
) {
    override fun toString(): String {
        var string = "code='${error.code}', message='${error.defaultMessage}'"
        field?.let { string += ", field='$it'" }
        rejectedValue?.let { string += ", rejectedValue='$it'" }
        return string
    }
}