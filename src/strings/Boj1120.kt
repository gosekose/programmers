package strings

import java.io.*

fun main() = BufferedReader(InputStreamReader(System.`in`)).use {
    val (strA, strB) = readlnOrNull()?.split(" ") ?: listOf("a", "b")

    val result = when {
        strA.length == strB.length -> calculateSameLetterAtSameLength(strA, strB)
        else -> calculateSameLetterAtDifferentLength(strA, strB)
    }

    println(result)
}

fun calculateSameLetterAtSameLength(strA: String, strB: String): Int {
    return strA.indices.count { strA[it] != strB[it] }
}

fun calculateSameLetterAtDifferentLength(strA: String, strB: String): Int {
    val step = strB.length - strA.length

    return (0..step).minOfOrNull { offset ->
        strA.indices.count { strA[it] != strB[it + offset] }
    } ?: -1
}