package br.com.lbenaducci.meowth.shared.domain.exceptions

import br.com.lbenaducci.meowth.shared.domain.errors.ErrorCatalog

class RepositoryException(
    error: ErrorCatalog,
    params: Map<String, Any> = emptyMap()
) : DomainException(error, params)
