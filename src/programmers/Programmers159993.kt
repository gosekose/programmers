import java.util.*

class Programmers159993 {

    private var m: Int = 0
    private var n: Int = 0
    private var answer: Int = -1

    private lateinit var s: Loc
    private lateinit var e: Loc
    private lateinit var l: Loc

    private lateinit var map: Array<CharArray>
    private lateinit var visited: Array<Array<BooleanArray>>

    private val queue: Queue<Loc> = LinkedList()

    private val DR = arrayOf(1, 0, -1, 0)
    private val DC = arrayOf(0, 1, 0, -1)


    fun solution(inputs: Array<String>): Int {

        m = inputs.size
        n = inputs[0].length

        map = Array(m) { CharArray(n) }
        visited = Array(m) { Array(n) { BooleanArray(2) } }

        for (row in 0 until m) {
            for (col in 0 until n) {
                val ch = inputs[row][col]

                map[row][col] = ch

                if (ch == 'S') s = Loc(row, col, 0, false)
                if (ch == 'E') e = Loc(row, col, 0, false)
                if (ch == 'L') l = Loc(row, col, 0, false)
            }
        }

        queue.add(s) // 시작점 queue에 넣기
        visited[s.row][s.col][0] = true

        bfs()

        return answer
    }

    private fun bfs() {
        while(queue.isNotEmpty()) {
            val now = queue.poll()

            if (now.row == e.row && now.col == e.col && now.isVisitedLever) {
                // 도착점이고 레버를 방문한 경우
                answer = now.depth
                break
            }

            for (k in 0..3) {
                val next = Loc(now.row, now.col, now.depth + 1, now.isVisitedLever)

                next.row += DR[k]
                next.col += DC[k]

                if (isValid(next)) {  // 범위가 유효한 경우
                    if (next.row == l.row && next.col == l.col) next.isVisitedLever = true

                    // next 위치 방문 처리
                    if(!next.isVisitedLever) visited[next.row][next.col][0] = true
                    else visited[next.row][next.col][1] = true

                    queue.add(next)
                }
            }
        }
    }

    private fun isValid(next: Loc): Boolean {
        if (next.isVisitedLever) {
            return next.row >= 0 && next.row < m && next.col >= 0 && next.col < n
                    && !visited[next.row][next.col][1] && map[next.row][next.col] != 'X'
        }

        return next.row >= 0 && next.row < m && next.col >= 0 && next.col < n
                && !visited[next.row][next.col][0] && map[next.row][next.col] != 'X'
    }

    data class Loc(
        var row: Int,
        var col: Int,
        var depth: Int,
        var isVisitedLever: Boolean,
    )
}