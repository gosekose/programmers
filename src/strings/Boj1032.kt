package strings

import java.io.*

fun main() = BufferedReader(InputStreamReader(System.`in`)).use {
    val fileNames = List(readlnOrNull()!!.toInt()) {readlnOrNull()!!}

    val standardFileNameArray = fileNames[0].toCharArray()
    val matchedFileNameFlagArray = BooleanArray(standardFileNameArray.size) { true }

    fileNames.forEach {
        it.forEachIndexed {idx, char ->
            if (matchedFileNameFlagArray[idx] && standardFileNameArray[idx] != char) {
                matchedFileNameFlagArray[idx] = false
            }
        }
    }

    val sb = StringBuilder()
    standardFileNameArray.forEachIndexed { idx, char ->
        val word = when (matchedFileNameFlagArray[idx]) {
            true -> char
            else -> "?"
        }
        sb.append(word)
    }

    println(sb)
}