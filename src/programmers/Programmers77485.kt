import java.util.*
import kotlin.math.*

class Programmers77485 {

    private val queue: Queue<Int> = LinkedList()
    private val answer = mutableListOf<Int>()

    private lateinit var map: Array<IntArray>

    fun solution(rows: Int, columns: Int, queries: Array<IntArray>): IntArray {
        map = Array(rows) { IntArray(columns) }

        var v = 1 // 초기값 세팅
        for (i in 0 until rows) {
            for (j in 0 until columns) {
                map[i][j] = v++
            }
        }

        for (query in queries) {
            rotate(query)
        }

        return answer.toIntArray()
    }

    private fun rotate(query: IntArray) {
        var ans = Int.MAX_VALUE

        val row1 = query[0] - 1
        val row2 = query[2] - 1
        val col1 = query[1] - 1
        val col2 = query[3] - 1

        var row = row1
        var col = col1

        while (true) {
            ans = min(ans, map[row][col])
            queue.add(map[row][col]) // 큐에 할당

            if (row == row1 && col != col2) {
                col++
            } else if (col == col2 && row != row2) {
                row++
            } else if (row == row2 && col != col1) {
                col--
            } else {
                row--
            }

            if (row == row1 && col == col1) break
        }

        col += 1 // 컬럼 한칸 이동 후
        while (queue.isNotEmpty()) {
            val v = queue.poll() // 큐에 있는 값

            map[row][col] = v // 할당

            if (row == row1 && col != col2) {
                col++
            } else if (col == col2 && row != row2) {
                row++
            } else if (row == row2 && col != col1) {
                col--
            } else {
                row--
            }
        }

        answer.add(ans)
    }
}

// 시작점을 정하고
// 시작점부터 회전하는 값을 순회하여 큐에 넣기 (큐에 넣을 때 가장 작은 수 체크)
// 시작점에서 한칸 이동하여 큐에 나온 수 하나씩 넣기 시작

// 어떻게 회전 알고리즘?
// x1, y1 ->

// while (x1 != sx && y1 != yx) 처음으로 오기 전까지 반복
// if x1 && !y2 -> y++
// else if y2 && !x2 -> x++
// else if x2 && !y1 -> x--
// else if y1 -> x++
