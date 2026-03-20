package br.com.lbenaducci.meowth.shared.domain.exceptions

import br.com.lbenaducci.meowth.shared.domain.errors.ErrorCatalog

open class DomainException(
    val error: ErrorCatalog,
    val params: Map<String, Any>
) : RuntimeException(error.defaultMessage, null, true, false) {
    val code: String
        get() = error.code

    override fun toString(): String {
        return "${this::class.simpleName}(code='$code', message='$message', params=$params)"
    }
}
