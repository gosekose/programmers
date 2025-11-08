package acmicpc

import java.io.*
import java.util.*
import kotlin.math.*

fun main(args: Array<String>) {
    val br = BufferedReader(InputStreamReader(System.`in`))
    val (N, M) = br.readLine().split(" ").map { it.toInt() }

    val map = Array(N) { LongArray(M) }
    val dr = arrayOf(-1, 0, 1, 0)
    val dc = arrayOf(0, -1, 0, 1)

    val aInf = 100000L     // 쓰레기 방문
    val bInf = 1L         // 쓰레기 인접 방문 (최대 2500개 이동 가능)

    var start = Node1445(0, 0, 0L)
    var end   = Node1445(0, 0, 0L)

    for (r in 0 until N) {
        val str = br.readLine()
        for (c in 0 until M) {
            if (str[c] == 'g') map[r][c] = aInf
            else if (str[c] == 'S') start = Node1445(r, c, 0L)
            else if (str[c] == 'F') end   = Node1445(r, c, 0L)
        }
    }

    fun isGarbageAdj(r: Int, c: Int): Boolean {
        return r in 0..<N && c in 0 ..<M
                && !(r == start.r && c == start.c) && !(r == end.r && c == end.c) && map[r][c] != aInf
    }

    for (r in 0 until N) {
        for (c in 0 until M) {
            if (map[r][c] == aInf) {
                for (k in 0 .. 3) {
                    val rk = r + dr[k]
                    val ck = c + dc[k]

                    if (isGarbageAdj(rk, ck)) {
                        map[rk][ck] = bInf // 인접한 노드의 값
                    }
                }
            }
        }
    }
    val pq = PriorityQueue<Node1445>() {a, b -> a.d.compareTo(b.d)}

    pq.add(start) // 시작 노드 입력
    val INF = aInf * 1_000_000L
    val dist = Array(N) { LongArray(M) {INF} }
    dist[start.r][start.c] = 0
    dist[end.r][end.c]     = INF

    while(pq.isNotEmpty()) {
        val node = pq.poll()

        if (node.r == end.r && node.c == end.c) continue

        for (k in 0 .. 3) {
            val nr = node.r + dr[k]
            val nc = node.c + dc[k]

            if (nr < 0 || nr >= N || nc < 0 || nc >= M) continue
            val nd = node.d + map[nr][nc]

            if (nd < dist[nr][nc]) {
                dist[nr][nc] = nd
                pq.add(Node1445(nr, nc, nd))
            }
        }
    }

    var total = dist[end.r][end.c]

    var gCnt   = total / aInf
    var adjCnt =  (total % aInf) / bInf

    val sb = StringBuilder()
    sb.append(gCnt).append(" ").append(adjCnt)

    println(sb.toString())

    val test = mutableMapOf<String, String>()

    for ((key, value) in test) {

    }
}

data class Node1445(
    val r: Int,
    val c: Int,
    var d: Long,
)