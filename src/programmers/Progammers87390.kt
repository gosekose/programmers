
import java.io.*
import java.util.*
import kotlin.math.*

class Progammers87390 {
    fun solution(n: Int, left: Long, right: Long): IntArray {
        val answer = IntArray((right - left + 1).toInt())

        var i = 0
        for (k in left..right) {
            val row = k / n
            val col = k % n

            answer[i++] = (max(row, col) + 1).toInt()
        }

        return answer
    }
}

// 2중 포문 불가
// o(n)으로 right-left 값만 넣어줄 수 있는 방법?

// k:3 => 3 / 3 + 3 % 3 -> 1, 0
// k:4 => 4 / 3 + 4 % 3 -> 1, 1

// 1 2 3 4
// 2 2 3 4
// 3 3 3 4    (2, 3)
// 4 4 4 4    (3, 3) -> 가장 큰 인덱스 + 1

// 1. left ~ right까지 1차원 포문
// 2. k -> / n + % n 연산해서 나온 몫, 나머지가 각각 인덱스 -> 가장 큰 인덱스 + 1이 실제 값