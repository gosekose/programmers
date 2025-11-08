package acmicpc

import java.util.*

fun main(args: Array<String>) {
    val n = readln()!!.toInt()

    val INF = 1_000_000_000
    val board = Array(n) { IntArray(n) {INF}}

    for (i in 0 until n) {
        val str = readLine()!!
        for (j in str.indices) {
            val c = str[j]
            if (c == '1') board[i][j] = 0
            else board[i][j] = 1
        }
    }

    val dist = Array(n) { IntArray(n) {INF} }

    val dq = ArrayDeque<Pair<Int, Int>>()
    dist[0][0] = 0
    dq.addFirst(0 to 0)

    val dr = intArrayOf(-1, 0, 1, 0)
    val dc = intArrayOf(0, -1, 0, 1)


    while(dq.isNotEmpty()) {
        val (r, c) = dq.removeFirst()
        val d = dist[r][c]

        for (k in 0 until 4) {
            val nr = r + dr[k]
            val nc = c + dc[k]

            if (nr < 0 || nr >= n || nc < 0 || nc >= n) continue

            val w = board[nr][nc]
            val nd = d + w

            if (nd < dist[nr][nc]) {
                dist[nr][nc] = nd
                if (w == 0) dq.addFirst(nr to nc) else dq.addLast(nr to nc)
            }
        }
    }

    println(dist[n - 1][n - 1])
}