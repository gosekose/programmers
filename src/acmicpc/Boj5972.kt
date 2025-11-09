package acmicpc

import java.util.*
import java.io.*
import kotlin.collections.ArrayList

class Boj5972 {

    private lateinit var graph: Array<ArrayList<Node>>
    private lateinit var cost: IntArray
    private val pq = PriorityQueue<Node> { a1, a2 ->
        a1.cost - a2.cost
    }

    fun solution(args: Array<String>) {
        val br = BufferedReader(InputStreamReader(System.`in`))

        val (n, m) = br.readLine().split(" ").map { it.toInt() }
        cost = IntArray(n + 1) { Int.MAX_VALUE }

        graph = Array(n + 1) { ArrayList() }
        for (i in 0 until m) {
            val (a, b, c) = br.readLine().split(" ").map { it.toInt() }
            graph[a].add(Node(b, c)) // 양방향 연결
            graph[b].add(Node(a, c))
        }

        cost[1] = 0

        pq.add(Node(1, 0))

        while (pq.isNotEmpty()) {
            val cur = pq.poll()
            for (node in graph[cur.next]) {
                val next = node.next
                val nextCost = node.cost + cur.cost

                if (cost[next] > nextCost) {
                    cost[next] = nextCost
                    pq.add(Node(next, nextCost))
                }
            }
        }

        println(cost[n])
    }

    data class Node(
        val next: Int,
        val cost: Int,
    )
}