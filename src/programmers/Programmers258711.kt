import kotlin.collections.*
import kotlin.math.*
import java.util.*

class Programmers258711 {
    private val result = IntArray(4)
    private lateinit var graph: Array<ArrayList<Int>>

    var n = 0
    fun solution(edges: Array<IntArray>): IntArray {

        val endpoint = getEndPoints(edges)
        result[0] = endpoint

        getResult(endpoint, edges)

        return result
    }

    private fun getEndPoints(edges: Array<IntArray>): Int {
        val possible = mutableMapOf<Int, Int>()
        val impossible = mutableSetOf<Int>()

        for (edge in edges) {
            val s = edge[0]
            val e = edge[1]

            n = max(s, n)
            n = max(e, n)

            possible[s] = possible.getOrDefault(s, 0) + 1
            impossible.add(e)

            if (possible.containsKey(e)) {
                possible.remove(e)
            }

            if (impossible.contains(s)) {
                possible.remove(s)
            }
        }

        val result = possible.maxByOrNull { it.value }
        return result!!.key
    }

    private fun getResult(start: Int, edges: Array<IntArray>) {
        graph = Array(n + 1) { ArrayList() }
        val visited = BooleanArray(n + 1)

        for (edge in edges) {
            val e = edge[0]
            val s = edge[1]

            graph[e].add(s) // 간선 연결
        }

        visited[start] = true

        for (node in graph[start]) { // 정점에 연결된 각 노드
            val queue: Queue<Int> = LinkedList()
            queue.add(node)

            var start = node
            var end = -1

            var maxEdge = 0 // 만약 시작점을 제외하고 maxEdge가 2 이상이면 8자
            // maxEdge가 1인 경우 자기자신으로 돌아오는 간선이 있으면 도넛

            var isLoopBack = false
            var isSizeOneEgiht = false

            while (queue.isNotEmpty()) {
                val ad = queue.poll()

                maxEdge = max(maxEdge, graph[ad].size)

                if (maxEdge >= 2) break

                for (add in graph[ad]) {
                    if (add == ad) { // 자기자신으로 돌아옴
                        isLoopBack = true
                        break
                    }

                    if (add == start) { // 시작 노드임
                        end = start
                        if (graph[start].contains(add)) {
                            isSizeOneEgiht = true
                        }
                        break
                    }
                    end = add
                    queue.add(add) // 큐에 다음 노드 넣기
                }
            }

            if (isLoopBack) {
                result[1] += 1
            } else if (maxEdge >= 2 || isSizeOneEgiht) {
                result[3] += 1
            } else if (start == end) {
                result[1] += 1
            } else if (start != end) {
                result[2] += 1
            }
        }
    }
}