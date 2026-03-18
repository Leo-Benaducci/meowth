package br.com.lbenaducci.meowth.shared.errors

interface ErrorCatalog {
    val code: String
    val defaultMessage: String
}
