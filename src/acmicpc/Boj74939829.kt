package acmicpc

import java.io.BufferedReader
import java.io.InputStreamReader

class Boj74939829 {
    val map = mutableListOf<List<String>>()
    var answer = 0

    val rowDR = arrayOf(0, 1)
    val rowDC = arrayOf(1, 1)

    val colDR = arrayOf(1, 1)
    val colDC = arrayOf(0, 1)

    val diaDR = arrayOf(0, 1, 1)
    val diaDC = arrayOf(1, 1, 0)
    var n = 0

    lateinit var visited: Array<BooleanArray>

    fun solution(args: Array<String>) {
        val br = BufferedReader(InputStreamReader(System.`in`))

        n = br.readLine().toInt()

        for (i in 1..n) {
            val str = br.readLine().split(" ")
            map.add(str)
        }

        visited = Array(n) { BooleanArray(n) }
        visited[0][0] = true
        visited[0][1] = true

        val loc = Loc(0, 1, 0)
        dfs(loc)

        println(answer)
    }

    private fun dfs(loc: Loc) {
        if (loc.row == n - 1 && loc.col == n - 1) {
            answer++
            return
        }

        if (loc.status == 0) { // row 이동
            for (i in 0..1) {
                val nextR = loc.row + rowDR[i]
                val nextC = loc.col + rowDC[i]

                val nextStatus = if (i == 0) 0 else 2

                if (isValid(nextR, nextC, i)) {
                    visited[nextR][nextC] = true
                    dfs(Loc(nextR, nextC, nextStatus))
                    visited[nextR][nextC] = false
                }
            }
        } else if (loc.status == 1) { // col 이동
            for (i in 0..1) {
                val nextR = loc.row + colDR[i]
                val nextC = loc.col + colDC[i]

                val nextStatus = if (i == 0) 1 else 2

                if (isValid(nextR, nextC, i)) {
                    visited[nextR][nextC] = true
                    dfs(Loc(nextR, nextC, nextStatus))
                    visited[nextR][nextC] = false
                }
            }
        } else { // 대각선 이동
            for (i in 0..2) {
                val nextR = loc.row + diaDR[i]
                val nextC = loc.col + diaDC[i]

                val nextStatus = if (i == 0) 0 else if (i == 1) 2 else 1

                if (isValid(nextR, nextC, i)) {
                    visited[nextR][nextC] = true
                    dfs(Loc(nextR, nextC, nextStatus))
                    visited[nextR][nextC] = false
                }
            }
        }
    }

    // 이동하려는 곳의 row, col
    private fun isValid(row: Int, col: Int, idx: Int): Boolean {
        if (!(row >= 0 && row < n && col >= 0 && col < n && !visited[row][col] && map[row][col] != "1")) return false

        if (idx == 1) {
            return (map[row][col - 1] != "1") && (map[row - 1][col] != "1")
        }

        return true
    }


    data class Loc(
        val row: Int,
        val col: Int,
        val status: Int, // 마지막 점 0: 가로, 1: 세로, 2: 대각선
    )
}