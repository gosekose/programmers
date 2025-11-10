package acmicpc

import java.util.*
import java.io.*

fun main(args: Array<String>) {
    val br = BufferedReader(InputStreamReader(System.`in`))

    val (N, M) = br.readLine().split(" ").map { it.toInt() }
    val indeg = IntArray(N + 1) /* 차수 저장 */
    val graph = Array(N + 1) { mutableListOf<Int>() } /* 그래프 */
    val pq = PriorityQueue<Int>() /* 위상 정렬 */

    repeat(M) {
        val str = StringTokenizer(br.readLine())
        val n1 = str.nextToken().toInt()
        val n2 = str.nextToken().toInt()

        graph[n1].add(n2)
        indeg[n2] += 1
    }

    for (i in 1 .. N) {
        if (indeg[i] == 0) pq.add(i)
    }

    val sb = StringBuilder()

    while(pq.isNotEmpty()) {
        val current = pq.poll()
        sb.append(current).append(" ") // 출력에 저장

        /* 다음 차수 후보들 탐색 */
        for (next in graph[current]) {
            indeg[next] -= 1

            /* 현재 차감된 차수가 0이되면 뺄 수 있는 상태임 */
            if (indeg[next] == 0) {
                pq.add(next)
            }
        }
    }

    val bw = BufferedWriter(OutputStreamWriter(System.out))

    bw.write(sb.toString())
    bw.flush()
}
