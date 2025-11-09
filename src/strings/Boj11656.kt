package strings

import java.io.*

fun main() = BufferedReader(InputStreamReader(System.`in`)).use {
    val strs = readlnOrNull() ?: return
    strs.indices
        .map { index -> strs.substring(index) }
        .sorted()
        .forEach(::println)
}