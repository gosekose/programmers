package strings

import java.io.*

fun main() = BufferedReader(InputStreamReader(System.`in`)).use {
    val sb = StringBuilder()

    generateSequence { readlnOrNull()!!.toInt() }
        .takeWhile { it != 0 }
        .forEach { n ->
            val words = List(n) {
                val wordStr = readlnOrNull()!!
                Word(wordStr, wordStr.uppercase())
            }.sortedBy { it.uppercaseWord }

            sb.append(words.first()).append("\n")
        }

    println(sb)
}

data class Word(
    val word: String,
    val uppercaseWord: String,
) {
    override fun toString(): String {
        return word
    }
}