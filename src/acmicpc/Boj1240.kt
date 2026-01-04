package acmicpc

import java.util.*
import java.io.*

class Solution1240 {
    val INF = 1_000_000_000
    var graph: Array<MutableList<Node1240>> = arrayOf()

    fun solve() {
        val br = BufferedReader(InputStreamReader(System.`in`))
        val (N, M) = br.readLine().split(" ").map { it.toInt() }

        graph = Array(N + 1) { mutableListOf<Node1240>() }

        for (i in 0 until N - 1) {
            val (e, v, d) = br.readLine().split(" ").map { it.toInt() }

            graph[e].add(Node1240(v, d))
            graph[v].add(Node1240(e, d))
        }

        val sb = StringBuilder()
        for (i in 0 until M) {
            val (e, v) = br.readLine().split(" ").map { it.toInt() }

            val res = dijkstra(e, v, N)
            sb.appendLine(res)
        }

        val bw = BufferedWriter(OutputStreamWriter(System.out))
        bw.write(sb.toString())
        bw.flush()
    }

    private fun dijkstra(targetE: Int, targetV: Int, n: Int): Int {
        val dist = Array(n + 1) { INF }
        val queue = PriorityQueue<Node1240>()

        dist[targetE] = 0
        queue.add(Node1240(targetE, 0))

        while(queue.isNotEmpty()) {
            val (e, d) = queue.poll()

            if (dist[e] != d) continue

            for (eNode in graph[e]) {
                val (ne, nd) = eNode
                val ndd = d + nd
                if (ndd < dist[ne]) {
                    dist[ne] = ndd
                    queue.add(Node1240(ne, ndd))
                }
            }
        }

        return dist[targetV]
    }

    data class Node1240(
        val e: Int,
        val d: Int,
    ): Comparable<Node1240> {
        override fun compareTo(other: Node1240): Int {
            return this.d - other.d
        }
    }
}

fun main(args: Array<String>) {
    val test = Solution1240()

    test.solve()
}