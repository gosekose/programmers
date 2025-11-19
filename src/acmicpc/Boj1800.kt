package acmicpc

import java.util.*
import java.io.*
import kotlin.math.*

data class Node1800(
    val e: Int,
    val d: Int,
) : Comparable<Node1800> {

    override fun compareTo(other: Node1800): Int {
        return this.d - other.d
    }
}

fun main(args: Array<String>) {
    val br = BufferedReader(InputStreamReader(System.`in`))
    var str = StringTokenizer(br.readLine())

    val N = str.nextToken().toInt()
    val P = str.nextToken().toInt()
    val K = str.nextToken().toLong()

    val INF = 1_000_000_000

    var maxCost = 0

    val graph = Array(N + 1) { mutableListOf<Node1800>() }
    repeat(P) {
        str = StringTokenizer(br.readLine())

        val e = str.nextToken().toInt()
        val v = str.nextToken().toInt()
        val d = str.nextToken().toInt()

        graph[e].add(Node1800(v, d))
        graph[v].add(Node1800(e, d))

        maxCost = max(maxCost, d)
    }

    /* 내야하는 제일 가격이 비싼것이 mid 라면, mid보다 큰 값에 대해서 +1 단 N에서의 값은 K보다 같거나 작아야 함 */
    fun dijkstra(mid: Int): Boolean {
        val pq = PriorityQueue<Node1800>()
        val dist = IntArray(N + 1) { INF }
        pq.add(Node1800(1, 0))
        dist[1] = 0

        while(pq.isNotEmpty()) {
            val node = pq.poll()
            val (e, d) = node

            if (d > dist[e]) continue

            if (e == N) break

            for (next in graph[e]) {
                val add = if (next.d > mid) 1 else 0

                val nd = d + add

                if (nd < dist[next.e]) {
                    dist[next.e] = nd
                    pq.add(Node1800(next.e, nd))
                }
            }
        }

        return dist[N] <= K
    }

    var low = 0
    var hi = maxCost
    var ans = -1

    while(low <= hi) {
        val mid = (low + hi) / 2
        val isPossible = dijkstra(mid)

        /* 정답이 가능한 경우, 더 금액을 낮출 수 있는지 찾는다 */
        if (isPossible) {
            ans = mid
            hi = mid - 1
        } else {
            low = mid + 1
        }
    }

    val bw = BufferedWriter(OutputStreamWriter(System.out))
    bw.write("$ans")
    bw.flush()
}