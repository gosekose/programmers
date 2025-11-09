package strings

import java.io.*

const val LAST_TIME_SECONDS_OF_DAY: Int = 3600 * 23 + 59 * 60 + 59
const val START_TIME_SECONDS_OF_DAY: Int = 0

fun main() = BufferedReader(InputStreamReader(System.`in`)).use {

    val sb = StringBuilder()
    repeat(3) {
        val (startTimeSeconds, endTimeSeconds) = readlnOrNull()!!.split(" ").map { it.toTimeSeconds() }

        val result = when {
            startTimeSeconds <= endTimeSeconds -> calculateMultipleOfThreeBetween(startTimeSeconds, endTimeSeconds)
            else -> calculateMultipleOfThreeBetween(startTimeSeconds, LAST_TIME_SECONDS_OF_DAY) +
                    calculateMultipleOfThreeBetween(START_TIME_SECONDS_OF_DAY, endTimeSeconds)
        }

        sb.append(result).append("\n")
    }

    println(sb)
}

private fun calculateMultipleOfThreeBetween(startTimeSeconds: Int, endTimeSeconds: Int): Int {
    var targetTimeSeconds = startTimeSeconds
    var result = 0

    while (targetTimeSeconds <= endTimeSeconds) {

        val longOfTime = targetTimeSeconds.transformTimeToLiteralNumber()
        if (longOfTime % 3 == 0) {
            result++
        }

        if (targetTimeSeconds == LAST_TIME_SECONDS_OF_DAY) {
            break
        }

        targetTimeSeconds++
    }

    return result
}

fun Int.transformTimeToLiteralNumber(): Int {
    val hours = this / 3600
    val minutes = (this % 3600) / 60
    val seconds = this % 60
    return hours * 10000 + minutes * 100 + seconds
}

fun String.toTimeSeconds(): Int {
    val (hours, minutes, seconds) = this.split(":").map { it.toInt() }
    return hours * 3600 + minutes * 60 + seconds
}