package strings

import java.io.*

fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    val (a, b) = readLine().split(" ").map { it.toInt() }
    println(
        when {
            a < b -> "<"
            a == b -> "=="
            else -> ">"
        }
    )
}