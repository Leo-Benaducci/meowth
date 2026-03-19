package br.com.lbenaducci.meowth.shared.domain.exceptions

import br.com.lbenaducci.meowth.shared.domain.errors.ErrorCatalog

class RepositoryException(
    error: ErrorCatalog
) : DomainException(error)
