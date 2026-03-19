package br.com.lbenaducci.meowth.shared.domain.exceptions

import br.com.lbenaducci.meowth.shared.domain.errors.ErrorCatalog

class ValidationException(
    error: ErrorCatalog
) : DomainException(error)
