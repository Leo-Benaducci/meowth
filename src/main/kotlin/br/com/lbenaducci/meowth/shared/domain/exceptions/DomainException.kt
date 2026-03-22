package br.com.lbenaducci.meowth.shared.domain.exceptions

import br.com.lbenaducci.meowth.shared.domain.errors.ErrorCatalog
import br.com.lbenaducci.meowth.shared.domain.errors.ErrorDetail

open class DomainException(
    error: ErrorCatalog,
    field: String? = null,
    rejectedValue: Any? = null
) : RuntimeException(null, null, true, false) {
    val detail: ErrorDetail = ErrorDetail(error, field, rejectedValue)

    override val message: String
        get() = "${this::class.simpleName}($detail)"

    override fun toString(): String {
        return message
    }
}
