package acmicpc

import java.util.*
import java.io.*

fun main(args: Array<String>) {
    val test = Test16929()

    test.solve()
}

class Test16929 {
    val dr = arrayOf(-1, 0, 1, 0)
    val dc = arrayOf(0, -1, 0, 1)

    fun solve() {
        val br = BufferedReader(InputStreamReader(System.`in`))
        val (N, M) = br.readLine().split(" ").map { it.toInt() }

        val board = Array(N) { CharArray(M) }

        for (i in 0 until N) {
            val str = br.readLine()

            for (j in str.indices) {
                board[i][j] = str[j]
            }
        }

        for (i in 0 until N) {
            for (j in 0 until M) {
                val visited = Array(N) { BooleanArray(M) }
                visited[i][j] = true
                if (dfs(N, M, board, visited, i to j, -1 to -1)) {
                    println("Yes")
                    return
                }
            }
        }

        println("No")
    }

    fun dfs(n: Int, m: Int, board: Array<CharArray>, visited: Array<BooleanArray>, start: Pair<Int, Int>, parent: Pair<Int, Int>): Boolean {
        for (k in 0 .. 3) {
            val nr = start.first + dr[k]
            val nc = start.second + dc[k]

            if (nr < 0 || nr >= n || nc < 0 || nc >= m) continue
            if (board[nr][nc] != board[start.first][start.second]) continue

            // 이전 부모 노드가 아닌데 이미 방문한 곳을 방문하는 경우라면 사이클임
            if (visited[nr][nc] && !(nr == parent.first && nc == parent.second)) {
                return true
            }

            if (visited[nr][nc]) continue
            visited[nr][nc] = true

            if (dfs(n, m, board, visited, nr to nc, start)) return true
        }

        return false
    }
}