package br.com.lbenaducci.meowth.shared.domain.exceptions

import br.com.lbenaducci.meowth.shared.domain.errors.ErrorCatalog

open class DomainException(
    error: ErrorCatalog
) : RuntimeException(error.defaultMessage, null, true, false) {
    val code: String = error.code

    override fun toString(): String {
        return "${this::class.simpleName}(code='$code', message='$message')"
    }
}
