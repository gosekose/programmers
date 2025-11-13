package acmicpc

import java.util.*
import java.io.*
import kotlin.math.*

fun main(args: Array<String>) {
    val br = BufferedReader(InputStreamReader(System.`in`))
    val (N, M) = br.readLine().split(" ").map { it.toInt() }
    val board = Array(N) { CharArray(M) }
    val visited = Array(N) { BooleanArray(M) }

    for (r in 0 until N) {
        val str = br.readLine()

        for (c in 0 until M) {
            board[r][c] = str[c]
        }
    }

    val dr = arrayOf(-1, 0, 1, 0)
    val dc = arrayOf(0, -1, 0, 1)

    val dp = Array(N) { IntArray(M) }
    var cycle = false

    fun dfs(row: Int, col: Int): Int {
        /* 가지치기 확인 */
        if (row !in 0 until N || col !in 0 until M) return 0
        if (board[row][col] == 'H') return 0
        if (cycle) return 0

        if (visited[row][col]) {
            cycle = true
            return 0
        }

        if (dp[row][col] != 0) return dp[row][col]
        visited[row][col] = true

        val step = board[row][col] - '0'
        var best = 0

        for (k in 0 .. 3) {
            val nr = row + (dr[k] * step)
            val nc = col + (dc[k] * step)

            if (nr in 0 until N && nc in 0 until M && board[nr][nc] != 'H') {
                best = max(best, dfs(nr, nc))
                if (cycle) {
                    visited[row][col] = false
                    return 0
                }
            }
        }

        visited[row][col] = false

        dp[row][col] = best + 1
        return dp[row][col]
    }

    val ans = dfs(0, 0)
    if (cycle) print("-1") else print("$ans")
}