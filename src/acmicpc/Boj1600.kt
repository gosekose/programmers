package acmicpc

import java.util.*
import java.io.*

data class Node1600(
    val row: Int,
    val col: Int,
    val k: Int,
    val d: Int
)

fun main(args: Array<String>) {
    val br = BufferedReader(InputStreamReader(System.`in`))

    val K = br.readLine().toInt()

    var str = StringTokenizer(br.readLine())
    val W = str.nextToken().toInt()
    val H = str.nextToken().toInt()

    val dr = arrayOf(-1, 0, 1, 0)
    val dc = arrayOf(0, -1, 0, 1)
    val hdr = arrayOf(2, 1, 2, 1, -2, -1, -2, -1)
    val hdc = arrayOf(1, 2, -1, -2, 1, 2, -1, -2)

    val map = Array(H) { IntArray(W) }

    for (r in 0 until H) {
        str = StringTokenizer(br.readLine())
        for (c in 0 until W) {
            map[r][c] = str.nextToken().toInt()
        }
    }

    /* 출발지 혹은 도착지가 장애물인 경우 불가 */
    if (map[0][0] == 1 || map[H-1][W-1] == 1) {
        println(-1)
        return
    }

    val visited = Array(H) { Array(W) { BooleanArray(K + 1) }}

    val pq = ArrayDeque<Node1600>()

    pq.add(Node1600(0, 0, 0, 0))
    visited[0][0][0] = true

    var ans = -1
    while(pq.isNotEmpty()) {
        val node = pq.poll()
        val (row, col, k, d) = node

        if (row == H-1 && col == W-1) {
            ans = d
            break
        }

        for (t in 0 until 4) {
            val nr = row + dr[t]
            val nc = col + dc[t]

            if (nr < 0 || nr >= H || nc < 0 || nc >= W || visited[nr][nc][k] || map[nr][nc] == 1) continue

            visited[nr][nc][k] = true
            pq.add(Node1600(nr, nc, k, d + 1))
        }

        if (k >= K) continue

        for (t in 0 until 8) {
            val nr = row + hdr[t]
            val nc = col + hdc[t]
            val nk = k + 1

            if (nr < 0 || nr >= H || nc < 0 || nc >= W || visited[nr][nc][nk] || map[nr][nc] == 1) continue
            visited[nr][nc][nk] = true
            pq.add(Node1600(nr, nc, nk, d + 1))
        }
    }

    val bw = BufferedWriter(OutputStreamWriter(System.out))
    bw.write("$ans")
    bw.flush()
}