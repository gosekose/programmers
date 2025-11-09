package strings

import java.io.*

fun main() = BufferedReader(InputStreamReader(System.`in`)).use {

    val startTime = readlnOrNull()?.timeToSeconds() ?: throw IllegalArgumentException()
    val endTime = readlnOrNull()?.timeToSeconds() ?: throw IllegalArgumentException()

    val result = when {
        endTime - startTime <= 0 -> (endTime + 24 * 3600 - startTime).toTimeString()
        else -> (endTime - startTime).toTimeString()
    }

    println(result)
}


fun String.timeToSeconds(): Int {
    val (hour, minute, seconds) = this.split(":").map { it.toInt() }
    return hour * 3600 + minute * 60 + seconds
}

fun Int.toTimeString(): String {
    val hours = this / 3600
    val minutes = (this % 3600) / 60
    val seconds = this % 60

    return String.format("%02d:%02d:%02d", hours, minutes, seconds)
}
