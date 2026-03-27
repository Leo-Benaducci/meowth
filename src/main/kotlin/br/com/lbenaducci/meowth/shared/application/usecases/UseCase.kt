package br.com.lbenaducci.meowth.shared.application.usecases

fun interface UseCase<I, O> {
    fun execute(input: I): O
}