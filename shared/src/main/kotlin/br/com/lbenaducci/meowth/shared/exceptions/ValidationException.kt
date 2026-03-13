package br.com.lbenaducci.meowth.shared.exceptions

import br.com.lbenaducci.meowth.shared.errors.ErrorCatalog

class ValidationException(
    error: ErrorCatalog
) : DomainException(error.code, error.defaultMessage)
