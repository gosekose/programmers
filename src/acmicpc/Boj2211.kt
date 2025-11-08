package acmicpc

import java.io.*
import java.util.*

fun main(args: Array<String>) {
    val br = BufferedReader(InputStreamReader(System.`in`))

    val (N, M) = br.readLine().split(" ").map { it.toInt() }

    val graph = Array(N + 1) { mutableListOf<Node2211>() }
    val INF = 21_000_000_000_000L
    val dist = LongArray(N + 1) { INF }
    dist[1] = 0L

    repeat(M) {
        val (a, b, c) = br.readLine().split(" ").map { it.toInt() }
        graph[a] += Node2211(b, c.toLong())
        graph[b] += Node2211(a, c.toLong())
    }

    val pq = PriorityQueue<Node2211>() { a, b -> a.dist.compareTo(b.dist) }
    val parent = IntArray(N + 1) { -1 } // -1 초기화

    pq.add(Node2211(1, 0))

    while(pq.isNotEmpty()) {
        val (e, d) = pq.poll()
//        println("e: $e, d: $d")
        if (d != dist[e]) continue // 이미 더 작은 값으로 업데이트 되었을 수 있음

        for (next in graph[e]) {
            val nextE    = next.e
            val nextDist = d + next.dist

            if (nextDist < dist[nextE]) {
                dist[nextE] = nextDist
                parent[nextE] = e
                pq.add(Node2211(nextE, nextDist))
            }
        }
    }

    val sb = StringBuilder()

    var res = mutableListOf<Pair<Int,Int>>()

    for (i in 1 .. N) {
        if (parent[i] != -1) {
            res.add(i to parent[i])
        }
    }

    sb.append(res.size).append("\n")
    for (r in res) {
        sb.append(r.first).append(" ").append(r.second).append("\n")
    }

    val bw = BufferedWriter(OutputStreamWriter(System.out))
    bw.write(sb.toString())
    bw.flush()
}

data class Node2211(
    val e: Int,
    val dist: Long
)