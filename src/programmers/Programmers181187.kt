import kotlin.math.*

class Programmers181187 {

    /**
     * https://school.programmers.co.kr/learn/courses/30/lessons/181187?language=kotlin
     */
    fun solution(r1: Int, r2: Int): Long {
        var answer: Long = 0

        for (i in 1..r2) {
            val s = if (i < r1) ceil(sqrt((r1.toDouble().pow(2) - i.toDouble().pow(2)))).toInt() else 0
            val e = sqrt((r2.toDouble().pow(2) - i.toDouble().pow(2))).toInt()
            answer += e - s + 1
        }

        return answer * 4

    }
}