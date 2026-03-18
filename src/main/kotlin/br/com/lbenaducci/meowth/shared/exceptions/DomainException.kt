package br.com.lbenaducci.meowth.shared.exceptions

open class DomainException protected constructor(
    val code: String,
    message: String
) : RuntimeException(message, null, true, false)
