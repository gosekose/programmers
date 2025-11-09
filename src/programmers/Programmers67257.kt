import java.util.*
import kotlin.math.*

class Programmers67257 {
    lateinit var parent: IntArray
    lateinit var sub: LongArray

    val points = mutableListOf<Pair<Int, Char>>()
    val numbers = mutableListOf<Long>()

    private val PL = arrayOf(1, 1, 2, 3, 2, 3)
    private val MI = arrayOf(2, 3, 1, 1, 3, 2)
    private val MU = arrayOf(3, 2, 3, 2, 1, 1)

    val pq = PriorityQueue<Point> { a1, a2 ->
        if (a1.pS == a2.pS) a1.current - a2.current
        else a1.pS - a2.pS
    }

    fun solution(expression: String): Long {
        var answer = mutableListOf<Long>()

        var number = ""
        for (e in expression) {
            if (e == '-' || e == '*' || e == '+') {
                numbers.add(number.toLong())
                points.add(Pair(numbers.lastIndex, e))
                number = ""
            } else {
                number += e
            }
        }
        numbers.add(number.toLong())

        for (i in 0..5) { // 각 우선순위 설정
            parent = IntArray(numbers.size) // 유니온 파인드
            sub = LongArray(numbers.size)

            for (i in 0..numbers.lastIndex) {
                parent[i] = i // 유니온 파인드 초기화
                sub[i] = numbers[i] // 처음 자신의 값으로 초기화
            }

            for (point in points) {
                val p = if (point.second == '+') PL[i] else if (point.second == '-') MI[i]
                else MU[i]
                pq.add(Point(point.first, point.second, p))
            }

            var sum = 0L
            while (pq.isNotEmpty()) {
                val p = pq.poll() // point

                val x = find(p.current) // 유니온 파인드에서 찾은 부모 노드
                val nextIdx = find(p.current + 1) // 원래 노드의 +1 한 것의 부모 노드

                sum = operator(sub[x], sub[nextIdx], p.p)
                union(x, nextIdx, sum) // 유니온 파인드 진행
            }
            answer.add(abs(sum))
        }

        answer.sortDescending()

        return answer[0]
    }

    private fun find(x: Int): Int {
        if (parent[x] == x) return x
        return find(parent[x])
    }

    private fun getSub(x: Int): Long {
        return sub[find(x)] // sub에 있는 x의 값
    }

    private fun union(x: Int, y: Int, sum: Long) {
        val px = find(x) // 인덱스
        val py = find(y) // 인덱스

        parent[px] = py // 유니온 파인드로 py 합쳐주기
        sub[px] = sum // 합 설정
        sub[py] = sum
    }

    private fun operator(x: Long, y: Long, o: Char): Long {
        return if (o == '+') x + y else if (o == '-') x - y else x * y
    }

    data class Point(
        val current: Int, // 피연산자 인덱스
        val p: Char,
        val pS: Int,
    )
}
// 연산자 재정의 가능
// 최종 결과 절대값
// 같은 연산자끼리는 앞에 있는 것이 우선순위
// 연산자 우선순위 우선순위 큐 ?
// 묶이는 얘들은 어떻게 처리?
// 100-(200*300)-500+20
// 100-60000-500+20
//
// 유니온 파인드?
// 100 * 200 - 300 + 300
// - + *순서라고 하면
// 200, -300 -> -100을 가리키는 집합
// 100 * -100 + 300
// -100 + 300 = 200 -> 이것의 부모가 되고
// 나머지 집합은 전부 200을 가리킴
// 1. 연산자와 인덱스를 우선순위 큐로 일단 넣고
// 2. 피연산자 / 연산자 / 피연산자 계산
// 6. 두 집합이 서로 합쳐진다면? --> 추가되는 인덱스로 다시 바꿔주기