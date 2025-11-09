package strings

import java.io.*

enum class Score(
    val code: String,
    val score: Double,
    val passType: Boolean,
) {

    AP("A+", 4.5, false),
    AZ("A0", 4.0, false),
    BP("B+", 3.5, false),
    BZ("B0", 3.0, false),
    CP("C+", 2.5, false),
    CZ("C0", 2.0, false),
    DP("D+", 1.5, false),
    DZ("D0", 1.0, false),
    F("F", 0.0, false),
    P("P", 0.0, true);

    companion object {
        fun findByCode(code: String): Score? {
            return entries.find { it.code == code }
        }
    }
}

fun main() = BufferedReader(InputStreamReader(System.`in`)).use {

    val subjectCount = 20
    var totalTimes = 0.0
    var totalScores = 0.0

    repeat(subjectCount) {
        val (_, timeStr, scoreStr) = readlnOrNull()?.split(" ") ?: throw Exception()

        val score = Score.findByCode(scoreStr) ?: Score.P

        if (score.passType) {
            return@repeat
        }

        val time = timeStr.toDouble()
        totalTimes += time
        totalScores += time * score.score
    }

    println(totalScores / totalTimes)
}