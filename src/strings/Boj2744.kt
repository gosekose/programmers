package strings

import java.io.*

fun main() = BufferedReader(InputStreamReader(System.`in`)).use {
    val word = readlnOrNull()!!

    val result = word.map {
        when {
            it.isUpperCase() -> it.lowercase()
            else -> it.uppercase()
        }
    }.joinToString("")

    println(result)
}