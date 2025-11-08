package acmicpc

import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.StringTokenizer
import java.util.PriorityQueue
import kotlin.math.min

data class Edge(val to: Int, val w: Int)
data class Node(val v: Int, val d: Int)

fun main() {
    val br = BufferedReader(InputStreamReader(System.`in`))

    fun st(): StringTokenizer = StringTokenizer(br.readLine())
    var tok = st()
    val V = tok.nextToken().toInt()
    val E = tok.nextToken().toInt()

    val g = Array(V + 1) { mutableListOf<Edge>() }
    repeat(E) {
        tok = st()
        val u = tok.nextToken().toInt()
        val v = tok.nextToken().toInt()
        val w = tok.nextToken().toInt()
        g[u].add(Edge(v, w))
        g[v].add(Edge(u, w))
    }

    tok = st()
    val mCnt = tok.nextToken().toInt()
    val x = tok.nextToken().toInt()
    tok = st()
    val mVs = IntArray(mCnt) { tok.nextToken().toInt() }

    tok = st()
    val sCnt = tok.nextToken().toInt()
    val y = tok.nextToken().toInt()
    tok = st()
    val sVs = IntArray(sCnt) { tok.nextToken().toInt() }

    val isMc = BooleanArray(V + 1)
    val isSb = BooleanArray(V + 1)
    for (v in mVs) isMc[v] = true
    for (v in sVs) isSb[v] = true

    val INF = 1_000_000_000

    fun multiSourceDijkstra(src: IntArray): IntArray {
        val dist = IntArray(V + 1) { INF }
        val pq = PriorityQueue<Node>(compareBy { it.d })
        for (s in src) {
            dist[s] = 0
            pq.add(Node(s, 0))
        }
        while (pq.isNotEmpty()) {
            val cur = pq.poll()
            if (cur.d > dist[cur.v]) continue
            for (e in g[cur.v]) {
                val nd = cur.d + e.w
                if (nd < dist[e.to]) {
                    dist[e.to] = nd
                    pq.add(Node(e.to, nd))
                }
            }
        }
        return dist
    }

    val distM = multiSourceDijkstra(mVs) // 각 정점 -> 가장 가까운 맥도날드까지
    val distS = multiSourceDijkstra(sVs) // 각 정점 -> 가장 가까운 스타벅스까지

    var ans = INF
    for (v in 1..V) {
        // 집 후보(맥/스 위치는 집이 아님)
        if (isMc[v] || isSb[v]) continue
        if (distM[v] <= x && distS[v] <= y) {
            ans = min(ans, distM[v] + distS[v])
        }
    }

    println(if (ans == INF) -1 else ans)
}