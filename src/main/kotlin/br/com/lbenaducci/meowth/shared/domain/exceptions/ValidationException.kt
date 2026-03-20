package br.com.lbenaducci.meowth.shared.domain.exceptions

import br.com.lbenaducci.meowth.shared.domain.errors.ErrorCatalog

class ValidationException(
    error: ErrorCatalog,
    params: Map<String, Any> = emptyMap()
) : DomainException(error, params) {
    constructor(error: ErrorCatalog, invalidValue: Any) : this(error, mapOf("invalid" to invalidValue))
}
