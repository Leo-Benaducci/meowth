package br.com.lbenaducci.meowth

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MeowthApplication

fun main(args: Array<String>) {
    runApplication<MeowthApplication>(*args)
}