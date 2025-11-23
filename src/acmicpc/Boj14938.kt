package acmicpc

import java.util.*
import java.io.*
import kotlin.math.*

data class Node14938(
    val v: Int,
    val d: Int,
) : Comparable<Node14938> {
    override fun compareTo(other: Node14938): Int {
        return this.d - other.d
    }
}

fun main(args: Array<String>) {
    val br = BufferedReader(InputStreamReader(System.`in`))
    var str = StringTokenizer(br.readLine())
    val INF = 100_000_000

    val n = str.nextToken().toInt()
    val m = str.nextToken().toInt()
    val r = str.nextToken().toInt()

    str = StringTokenizer(br.readLine())
    val arr = IntArray(n + 1)
    for (i in 1 .. n) {
        arr[i] = str.nextToken().toInt()
    }

    val graph = Array(n + 1) { mutableListOf<Node14938>() }
    for (j in 1 .. r) {
        str = StringTokenizer(br.readLine())
        val e = str.nextToken().toInt()
        val v = str.nextToken().toInt()
        val d = str.nextToken().toInt()

        graph[e].add(Node14938(v, d))
        graph[v].add(Node14938(e, d))
    }

    fun dijkstra(start: Int): Int {
        val dist = Array(n + 1) { INF }
        dist[start] = 0 // 시작 노드는 0

        val pq = PriorityQueue<Node14938>()

        pq.add(Node14938(start, 0))

        while(pq.isNotEmpty()) {
            val node = pq.poll()
            val (v, d) = node

            if (dist[v] != d) continue

            /* 다음 위치 노드 찾기 */
            for (next in graph[v]) {
                val (nv, nd) = next
                val dd = d + nd

                /* 최대 이동 거리 미만 */
                if (dd < dist[nv] && dd <= m) {
                    dist[nv] = dd
                    pq.add(Node14938(nv, dd))
                }
            }
        }

        var ans = 0
        for (i in 1 .. n) {
            if (dist[i] <= m) {
                ans += arr[i] /* 획득 가능 아이템 추가 */
            }
        }

        return ans
    }

    var res = 0
    for (i in 1 .. n) {
        val ans = dijkstra(i)

        res = max(ans, res)
    }

    val bw = BufferedWriter(OutputStreamWriter(System.out))
    bw.write("$res")
    bw.flush()
}