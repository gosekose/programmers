import java.util.*

class Programmers142085 {

    fun solution(n: Int, k: Int, enemy: IntArray): Int {
        var answer: Int = 0

        val pq = PriorityQueue<Int> { a1, a2 ->
            a2 - a1 // 내림차순으로 정렬
        }

        var varN = n
        var varK = k

        for (i in 0..enemy.lastIndex) {
            if(varN < enemy[i] && varK == 0) break

            pq.offer(enemy[i]) // 적군 수 우선순위 큐에 추가

            if (varN < enemy[i]) { // 병사가 부족할 경우
                varN += pq.poll() // 병사 꺼내서 추가
                varK--; // 무적권 하나 제거
            }

            varN -= enemy[i] // 병사 사용
            answer++
        }

        return answer
    }
}