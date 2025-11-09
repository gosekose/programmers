package strings

import java.io.*

fun main() = BufferedReader(InputStreamReader(System.`in`)).use {
    val mainStr = readlnOrNull() ?: throw IllegalArgumentException()
    val subStr = readlnOrNull() ?: throw IllegalArgumentException()

    val result = when {
        mainStr.length < subStr.length -> 0
        else -> if (mainStr.searchByKmp(subStr)) 1 else 0
    }

    println(result)
}

fun String.searchByKmp(pattern: String): Boolean {
    val pi = getKmpPi(pattern)
    var j = 0

    for (i in this.indices) {
        while (j > 0 && this[i] != pattern[j]) {
            j = pi[j - 1]
        }

        if (this[i] == pattern[j]) {
            if (j == pattern.length - 1) {
                return true
            } else {
                j++
            }
        }
    }

    return false
}

fun getKmpPi(pattern: String): IntArray {
    val pi = IntArray(pattern.length) { 0 }
    var j = 0

    for (i in 1..<pi.size) {
        while (j > 0 && pattern[i] != pattern[j]) {
            j = pi[j - 1]
        }

        if (pattern[i] == pattern[j]) {
            j++
            pi[i] = j
        }
    }

    return pi
}