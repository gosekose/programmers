package strings

import java.io.*

fun main() = BufferedReader(InputStreamReader(System.`in`)).use {
    val plainArray = readlnOrNull()!!.toCharArray()
    val keyStr = readlnOrNull()!!

    val keyArray = CharArray(plainArray.size) { ' ' }

    for (i in plainArray.indices) {
        keyArray[i] = keyStr[i % keyStr.length]
    }

    val encryptedText = plainArray.mapIndexed { idx, code ->
        val key = keyArray[idx]
        when {
            code == ' ' -> code
            code - key <= 0 -> 'z' + (code - key)
            else -> 'a' + (code - key - 1)
        }
    }.joinToString("")

    println(encryptedText)
}