import java.util.*

class Programmers154540 {

    private var m: Int = 0
    private var n: Int = 0

    private val answer = mutableListOf<Int>()
    private val queue: Queue<Loc> = LinkedList()

    private lateinit var map: Array<IntArray>
    private lateinit var visited: Array<BooleanArray>

    private val DR = arrayOf(0, 1, 0, -1)
    private val DC = arrayOf(1, 0, -1, 0)


    fun solution(inputs: Array<String>): IntArray {

        m = inputs.size
        n = inputs[0].length

        map = Array(m) { IntArray(n) }
        visited = Array(m) { BooleanArray(n) }

        for (row in 0 until m) {
            for (col in 0 until n) {
                val ch = inputs[row][col]

                if (ch == 'X') map[row][col] = -1

                else map[row][col] = ch.toString().toInt()
            }
        }

        for (row in 0 until m) {
            for (col in 0 until n) {
                val v = map[row][col]

                if (v == -1 || visited[row][col]) continue // X거나 방문한 경우 패스

                val now = Loc(row, col, v)
                queue.add(now)
                visited[row][col] = true // 방문 설정
                bfs()
            }
        }

        if (answer.isEmpty()) return intArrayOf(-1)

        answer.sort()

        return answer.toIntArray()
    }

    private fun bfs() {
        var sum = 0

        while(queue.isNotEmpty()) {
            val now = queue.poll() // 큐에서 꺼냄
            sum += now.sum // 합계 추가

            for (k in 0..3) {
                val next = Loc(now.row, now.col, now.sum)

                next.row += DR[k]
                next.col += DC[k]

                if (isValid(next)) {
                    visited[next.row][next.col] = true // 방문처리
                    next.sum = map[next.row][next.col] // 합계 설정
                    queue.add(next)
                }
            }
        }

        answer.add(sum)
    }

    private fun isValid(next: Loc): Boolean {
        val row = next.row
        val col = next.col

        return row >= 0 && row < m
        && col >= 0 && col < n
        && !visited[row][col] && map[row][col] != -1
    }

    data class Loc(
        var row: Int,
        var col: Int,
        var sum: Int,
    )
}

// 1. for문 이중포문
// 2. 방문하지 않은 곳이 발생하면 큐를 돌면서 Bfs
// 3. 더이상 방문할 곳이 없으면 종료 후 answer 추가
// 4. for문에서 방문한 곳이면 continue
// 5. 이중포문 종료 후 answer 정렬 후 리턴