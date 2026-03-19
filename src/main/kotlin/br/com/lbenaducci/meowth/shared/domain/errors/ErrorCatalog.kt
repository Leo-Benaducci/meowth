package br.com.lbenaducci.meowth.shared.domain.errors

interface ErrorCatalog {
    val code: String
    val defaultMessage: String
}
