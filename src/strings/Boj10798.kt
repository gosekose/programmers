package strings

import java.io.*

fun main() = BufferedReader(InputStreamReader(System.`in`)).use {
    val array = Array(5) { Array(15) { '-' } }

    for (row in 0..<5) {
        val str = readlnOrNull()!!

        str.forEachIndexed { col, c ->
            array[row][col] = c
        }
    }

    val sb = StringBuilder()
    for (col in 0..<15) {
        for (row in 0..<5) {
            if (array[row][col] != '-') {
                sb.append(array[row][col])
            }
        }
    }

    println(sb)
}