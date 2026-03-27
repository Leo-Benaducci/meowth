package br.com.lbenaducci.meowth.shared.domain.exceptions

import br.com.lbenaducci.meowth.shared.domain.errors.ErrorCatalog

class NotFoundException(
    error: ErrorCatalog
) : DomainException(error, null, null)
