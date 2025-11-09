package strings

import java.io.*

const val ALPHABET_COUNT = 26

fun main() = BufferedReader(InputStreamReader(System.`in`)).use {
    val word = readlnOrNull()!!

    val charArray = IntArray(ALPHABET_COUNT) { 0 }

    word.forEach {
        charArray[it - 'a'] += 1
    }

    println(charArray.joinToString(" "))
}