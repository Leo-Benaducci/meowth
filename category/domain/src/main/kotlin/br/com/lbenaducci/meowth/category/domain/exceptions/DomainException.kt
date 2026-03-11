package br.com.lbenaducci.meowth.category.domain.exceptions

open class DomainException protected constructor(
    val code: String,
    message: String
) : RuntimeException(message, null, true, false)