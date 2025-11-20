package acmicpc

import java.util.*
import java.io.*

fun main(args: Array<String>) {
    val br = BufferedReader(InputStreamReader(System.`in`))
    val N = br.readLine().toInt()

    /* 자신보다 앞에 선행되는 개수 */
    val indegree = IntArray(N + 1)
    val time = IntArray(N + 1)
    val adj = Array(N + 1) { mutableListOf<Int>() }
    val dist = IntArray(N + 1)

    for (i in 1 .. N) {
        val str = StringTokenizer(br.readLine())

        val t = str.nextToken().toInt() // 걸리는 시간
        time[i] = t

        val p = str.nextToken().toInt() // 선행 조건 개수
        for (j in 0 until p) {
            val parent = str.nextToken().toInt() // 선행 조건 노드
            adj[parent].add(i) // 선행 -> 후행
            indegree[i]++ // 진입차수
        }
    }

    for (i in 1..N) {
        dist[i] = time[i]
    }

    val queue = ArrayDeque<Int>()

    /* 진입 차수가 0인경우 입력 */
    for (i in 1 .. N) {
        if (indegree[i] == 0) {
            queue.add(i)
        }
    }

    while(queue.isNotEmpty()) {
        val u = queue.removeFirst()

        /* 자신의 후행 노드 탐색 */
        for (v in adj[u]) {
            val candidate = dist[u] + time[v]
            if (candidate > dist[v]) {
                dist[v] = candidate
            }

            indegree[v]--

            if (indegree[v] == 0) {
                queue.addLast(v)
            }
        }
    }

    var ans = 0
    for (i in 1 .. N) {
        if (dist[i] > ans) ans = dist[i]
    }

    val bw = BufferedWriter(OutputStreamWriter(System.out))

    bw.write("$ans")
    bw.flush()
}