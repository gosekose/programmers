package acmicpc

import java.io.*
import java.util.*
import kotlin.math.*

fun main(args: Array<String>) {
    val br = BufferedReader(InputStreamReader(System.`in`))
    val (N, M) = br.readLine().split(" ").map { it.toInt() }

    val map = Array(N) { IntArray(M) }
    val visited = Array(N) { BooleanArray(M) }
    val pq = PriorityQueue<Node1113>()

    for (r in 0 until N) {
        val str = br.readLine()
        for (c in 0 until M) {
            map[r][c] = str[c] - '0'
        }
    }

    /* 가장 바깥 외벽부터 탐색 */
    for (r in 0 until N) {
        pq.add(Node1113(r, 0, map[r][0]))
        pq.add(Node1113(r, M-1, map[r][M-1]))

        visited[r][0] = true
        visited[r][M-1] = true
    }

    for (c in 1 until M-1) {
        pq.add(Node1113(0, c, map[0][c]))
        pq.add(Node1113(N-1, c, map[N-1][c]))

        visited[0][c] = true
        visited[N-1][c] = true
    }

    var ans = 0
    val dr = arrayOf(-1, 0, 1, 0)
    val dc = arrayOf(0, -1, 0, 1)

    while(pq.isNotEmpty()) {
        val node = pq.poll()

        for (k in 0 .. 3) {
            val nr = node.row + dr[k]
            val nc = node.col + dc[k]

            /* out of bound, 방문 확인 */
            if (!(nr in 0 until N && nc in 0 until M && !visited[nr][nc])) continue

            /* 벽 높이가 더 큰 경우 물이 고일 수 있음 */
            if (map[nr][nc] < node.height) {
                ans += node.height - map[nr][nc]
            }

            pq.add(Node1113(nr, nc, max(node.height, map[nr][nc])))
            visited[nr][nc] = true
        }
    }

    val bw = BufferedWriter(OutputStreamWriter(System.out))
    bw.write("$ans")
    bw.flush()
}

data class Node1113(
    val row: Int,
    val col: Int,
    val height: Int,
): Comparable<Node1113> {
    /* 높이 오름차순 정렬 */
    override fun compareTo(other: Node1113): Int {
        return this.height - other.height
    }
}