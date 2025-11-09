package strings

import java.io.*

fun main() = BufferedReader(InputStreamReader(System.`in`)).use {

    var str: String
    while (readlnOrNull().also { str = it ?: "END" } != "END") {
        println(str.reversed())
    }
}