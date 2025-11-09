package strings

import java.io.*

fun main() = BufferedReader(InputStreamReader(System.`in`)).use {

    var sum = 0

    val nChar = readlnOrNull()?.toCharArray()?.sortedArray() ?: charArrayOf()
    val len = nChar.size

    val sb = buildString {
        for (i in len - 1 downTo 0) {
            val num = nChar[i] - '0'
            sum += num
            append(num)
        }
    }

    if (nChar[0] != '0' || sum % 3 != 0) {
        println(-1)
        return
    }

    println(sb)
}