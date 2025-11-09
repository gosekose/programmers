package acmicpc

import java.util.*
import java.io.*
import kotlin.math.*

class Boj2589 {
    private lateinit var map: Array<CharArray>
    private var n = 0
    private var m = 0
    private var result = 0

    private val DX = arrayOf(-1, 0, 1, 0)
    private val DY = arrayOf(0, -1, 0, 1)

    fun solution(args: Array<String>) {
        val br = BufferedReader(InputStreamReader(System.`in`))
        val (a, b) = br.readLine().split(" ").map { it.toInt() }
        n = a
        m = b
        map = Array(n + 1) { CharArray(m + 1) }
        for (i in 1..n) {
            val str = br.readLine()
            for (j in 1..str.length) {
                map[i][j] = str[j - 1]
            }
        }

        for (i in 1..n) {
            for (j in 1..m) {
                if (map[i][j] == 'L') {
                    bfs(Node(i, j, 0))
                }
            }
        }

        println(result)
    }

    private fun bfs(start: Node) {
        val queue: Queue<Node> = LinkedList()
        val visited = Array(n + 1) { BooleanArray(m + 1) }
        queue.add(start)

        visited[start.row][start.col] = true

        while (queue.isNotEmpty()) {
            val node = queue.poll()

            result = max(result, node.cost)

            for (k in 0..3) {
                val nextRow = node.row + DX[k]
                val nextCol = node.col + DY[k]
                val nextCost = node.cost + 1

                if (validate(nextRow, nextCol, visited)) {
                    visited[nextRow][nextCol] = true
                    queue.add(Node(nextRow, nextCol, nextCost))
                }
            }
        }

    }

    private fun validate(row: Int, col: Int, visited: Array<BooleanArray>): Boolean {
        return row > 0 && col > 0 && row <= n && col <= m && !visited[row][col] && map[row][col] == 'L'
    }


    data class Node(
        val row: Int,
        val col: Int,
        val cost: Int,
    )
}
