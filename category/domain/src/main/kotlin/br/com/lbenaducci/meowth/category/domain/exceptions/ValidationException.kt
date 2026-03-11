package br.com.lbenaducci.meowth.category.domain.exceptions

import br.com.lbenaducci.meowth.category.domain.errors.ErrorCatalog

class ValidationException(
    error: ErrorCatalog
) : DomainException(error.code, error.defaultMessage)