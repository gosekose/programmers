package strings

import java.io.*

fun main() = BufferedReader(InputStreamReader(System.`in`)).use {
    val plainTextArray = readlnOrNull()!!.toCharArray()

    val sb = StringBuilder()
    plainTextArray.forEach {
        val rotChar = when {
            it.isLetterBetweenAndM() -> it + 13
            it.isLetterBetweenNAndZ() -> (it + 13) - 26
            else -> it
        }

        sb.append(rotChar)
    }

    println(sb)
}

fun Char.isLetterBetweenAndM(): Boolean {
    return this in 'A'..'M' || this in 'a'..'m'
}

fun Char.isLetterBetweenNAndZ(): Boolean {
    return this in 'N'..'Z' || this in 'n'..'z'
}