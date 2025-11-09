import java.util.*
import kotlin.math.*

class Programmers155651 {

    fun solution(bookTime: Array<Array<String>>): Int {
        var answer = -1

        val pq = PriorityQueue<Time> { a1, a2 ->
            a1.e - a2.e // 종료시간으로 우선순위 큐 설정
        }

        bookTime.sortWith(compareBy {it[0]}) // 시작시간으로 정렬

        for (book in bookTime) {
            val time = getTime(book)

            if (pq.isEmpty()) pq.add(time) // 큐가 빈 경우 큐에 넣기

            else {
                if (pq.peek().e <= time.s) { // 종료시간 이내에 끝나면 큐에서 빼기
                    pq.poll()
                    pq.add(time) // 다시 큐에 추가
                } else {
                    pq.add(time) // 종료시간 이내에 끝나지 않으면 큐에 넣기
                }
            }

            answer = max(answer, pq.size) // 가장 큐가 많이 쌓인 것이 크기
        }

        return answer
    }

    private fun getTime(array: Array<String>): Time {
        val start = array[0].split(":")
        val end = array[1].split(":")
        return Time(
            s = start[0].toInt() * 60 + start[1].toInt(),
            e = end[0].toInt() * 60 + end[1].toInt() + 10 // 10분 청소시간
        )
    }

    data class Time(
        val s: Int,
        val e: Int,
    )
}