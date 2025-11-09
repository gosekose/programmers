import kotlin.math.*

class Progammers42842 {
    fun solution(brown: Int, yellow: Int): IntArray {
        val sq = floor(sqrt(yellow.toDouble())).toInt()

        val divisors = mutableListOf<Divisor>()

        val answer = IntArray(2)

        for (y in 1..sq) {
            if (yellow % y == 0) {
                val x = yellow / y
                divisors.add(Divisor(x, y))
            }
        }

        for (dv in divisors) {
            var sum = 0
            sum += (dv.x + 2) * 2
            sum += dv.y * 2


            if (sum == brown) {
                answer[0] = (dv.x + 2)
                answer[1] = (dv.y + 2)

                break
            }
        }
        return answer
    }

    data class Divisor(
        val x: Int,
        val y: Int,
    )
}