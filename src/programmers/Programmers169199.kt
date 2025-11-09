import java.util.*

class Programmers169199 {

    private val rLoc = arrayOf(-1, 0, 1, 0)
    private val cLoc = arrayOf(0, -1, 0, 1)

    fun solution(board: Array<String>): Int {
        var answer: Int = -1

        val rLen = board.size
        val cLen = board[0].length

        val visited = Array(rLen) { BooleanArray(cLen) }
        val table = Array(rLen) { CharArray(cLen) { '.' } }
        val queue: Queue<Loc> = LinkedList()

        val now = Loc(0, 0, 0)
        val goal = Loc(0, 0, 0)

        for (row in 0 until rLen) {
            val str = board[row]
            for (col in 0 until cLen) {
                val char = str[col]
                table[row][col] = char // 문자 입력

                if (char == 'R') {
                    now.row = row
                    now.col = col
                }

                if (char == 'G') {
                    goal.row = row
                    goal.col = col
                }
            }
        }

        queue.add(now)
        visited[now.row][now.col] = true // 시작점 방문

        while(queue.isNotEmpty()) { // 큐가 비지 않을 때 까지 BFS
            val now = queue.poll()

            if (now.row == goal.row && now.col == goal.col) {
                answer = now.depth
                break
            }

            for (k in 0..3) {
                var nextR = now.row
                var nextC = now.col

                while(nextR >= 0 && nextR < rLen && nextC >= 0 && nextC < cLen
                    && table[nextR][nextC] != 'D') {
                    nextR -= rLoc[k]
                    nextC -= cLoc[k]
                } // nextR, nextC는 인덱스를 벗어났거나 장애물 위치까지 이동한 상태에서 종료

                nextR += rLoc[k]
                nextC += cLoc[k] // 한 단계 전으로 이동

                if (visited[nextR][nextC] || (now.row == nextR && now.col == nextC)) continue

                visited[nextR][nextC] = true
                queue.add(Loc(nextR, nextC, now.depth + 1))
            }
        }

        return answer
    }

    data class Loc(
        var row: Int,
        var col: Int,
        var depth: Int,
    )
}