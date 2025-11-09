import kotlin.math.*

class Programmers62048 {
    private var y = 0 // 절편
    fun solution(w: Int, h: Int): Long {
        y = h
        var answer: Long = 0

        val a = ((h - 0).toDouble() / (0 - w).toDouble()) // 기울기

        // 하부분
        for (x in 1..w) { // 현재 좌표 기준으로 적분
            val y1 = fx(a, x)
            answer += y1
        }
        return answer * 2 // 대칭
    }

    fun fx(a: Double, x: Int): Long {
        return floor((a * x + y).toDouble()).toLong()
    }
}
// 직선을 그리고
// 직선 기준 상 하 두 가지 영역으로 나눔
// x: 0 ~ w 좌표까지 순회
// 하 부분 f(x) 의 정수 y 좌표의 정수형만큼 직사각형 그리기 가능
// 상 부분 f(x) 의 (h - f(x)) 내림 만큼 정사각형 그릴 수 있음
// 내림 floor