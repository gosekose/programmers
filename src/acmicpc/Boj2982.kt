package acmicpc

import java.io.*
import java.util.*

fun main(args: Array<String>) {
    val br = BufferedReader(InputStreamReader(System.`in`))
    val (N, M) = br.readLine().split(" ").map { it.toInt() }

    val (A, B, K, G) = br.readLine().split(" ").map { it.toInt() }
    val kingMoves = mutableListOf<Int>()
    if (G > 0) {
        val stG = StringTokenizer(br.readLine())
        repeat(G) { kingMoves += stG.nextToken().toInt() }
    }

    val graph = Array(N + 1) { mutableListOf<Node2982>() }
    repeat(M) {
        val (U, V, L) = br.readLine().split(" ").map { it.toInt() }
        graph[U] += Node2982(V, L.toLong())
        graph[V] += Node2982(U, L.toLong())
    }

    val kingTimes = mutableMapOf<String, Pair<Long, Long>>()
    var times = 0L
    for (i in 0 until kingMoves.size - 1) {
        val node = kingMoves[i]
        val next = kingMoves[i + 1]

        for (adj in graph[node]) {
            if (adj.e == next) {
                val key = "$node,$next"
                val rKey = "$next,$node"

                val start = times
                times += adj.d
                val end = times

                kingTimes[key]  = Pair(start, end)
                kingTimes[rKey] = Pair(start, end)
                break
            }
        }
    }

    val dist = LongArray(N + 1) { 200_000_000_000L }
    val pq = PriorityQueue<Node2982>() {a, b -> a.d.compareTo(b.d) } // 작은 순서대로 큐

    pq.add(Node2982(A, K.toLong())) // 왕 출발 후 K분 시작 보정
    dist[A] = K.toLong()

    while(pq.isNotEmpty()) {
        val (e, d) = pq.poll()

        if (d != dist[e]) continue

        for (next in graph[e]) {
            val (ne, nd) = next

            // 다음번에 갈 곳이 왕이 지나가는 경로라면
            val key = "$e,$ne"
            if (kingTimes[key] != null) {
                val start = kingTimes[key]!!.first
                val end   = kingTimes[key]!!.second

                // 만약 현재까지 온 시간이 왕 이동 시간에 겹친다면
                if (d in start..end) {
                    // 끝나는 시간까지 대기해야함 -> 그 후 이동
                    val nextD = end + nd // 끝나는 시간 기다린 후 이동

                    if (nextD < dist[ne]) {
                        dist[ne] = nextD
                        pq.add(Node2982(ne, nextD))
                    }
                } else { // 안기다려도 되는 경우
                    val nextD = d + nd
                    if (nextD < dist[ne]) {
                        dist[ne] = nextD
                        pq.add(Node2982(ne, nextD))
                    }
                }
            } else {
                val nextD = d + nd
                if (nextD < dist[ne]) {
                    dist[ne] = nextD
                    pq.add(Node2982(ne, nextD))
                }
            }
        }
    }

    val ans = dist[B] - K // K 시작 시간 보정
    println(ans)
}

data class Node2982(
    val e: Int,
    val d: Long,
)