package strings

import java.io.*

fun main() = BufferedReader(InputStreamReader(System.`in`)).use {
    val document = readlnOrNull() ?: ""
    val word = readlnOrNull() ?: ""

    var result = 0
    var skipPoint = 0
    val moveRange = document.length - word.length

    for (i in 0..moveRange) {
        if (i != 0 && i <= skipPoint) continue

        if (document.startsWithAt(i, word)) {
            result++
            skipPoint = i + word.length - 1
        }
    }

    println(result)
}

fun String.startsWithAt(index: Int, word: String): Boolean {
    for (j in word.indices) {
        if (this[index + j] != word[j]) {
            return false
        }
    }
    return true
}