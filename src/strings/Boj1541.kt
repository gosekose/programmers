package strings

import java.io.*

/* -가 나오면 +가 나올 떄 까지 모두 묶고 한 번에 빼기 */
fun main() = with(BufferedReader(InputStreamReader(System.`in`))) {
    val numberStrs = readLine().split("-")

    val result = if (numberStrs.first().isNotEmpty()) {
        numberStrs[0].split("+").sumOf { it.toInt() }
    } else {
        0
    } - numberStrs.drop(1).sumOf { it.split("+").sumOf(String::toInt) }

    println(result)
}