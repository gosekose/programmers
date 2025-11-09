import java.util.*

class Programmers138476 {
    fun solution(k: Int, t: IntArray): Int {
        var answer: Int = 0

        val pq = PriorityQueue<Int> { a1, a2 ->
            a2 - a1
        }
        val map = mutableMapOf<Int, Int>()

        for (i in 0..t.lastIndex) {
            val v = t[i]
            map[v] = map.getOrDefault(v, 0) + 1
        }

        val mapList = map.values.sortedDescending()
        var a = 0

        for (i in 0..mapList.lastIndex) {
            a += mapList[i]
            answer++
            if (a >= k) break
        }
        return answer
    }
}