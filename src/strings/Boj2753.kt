package strings

import java.io.*

fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    val year = readLine().toInt()

    println(
        when {
            ((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0) -> "1"
            else -> "0"
        }
    )
}