package acmicpc

import java.util.*
import java.io.*
import kotlin.collections.*

class Boj1976 {
    private lateinit var graph: Array<ArrayList<Int>>
    private var n = 0
    private var m = 0

    fun solution(args: Array<String>) {
        val br = BufferedReader(InputStreamReader(System.`in`))
        n = br.readLine().toInt()
        m = br.readLine().toInt()

        graph = Array(n + 1) { ArrayList() }

        for (i in 1..n) {
            val c = br.readLine().split(" ").map { it.toInt() }

            for (j in 0 until c.size) {
                if (c[j] != 0) {
                    graph[i].add(j + 1)
                }
            }
        }

        val toGo = br.readLine().split(" ").map { it.toInt() }

        dijkstra(toGo)


    }

    private fun dijkstra(toGo: List<Int>) {
        var from = toGo[0]
        if (toGo.size == 1) {
            println("YES")
            return
        }

        for (i in 1..toGo.lastIndex) {
            val to = toGo[i]

            if (from == to) continue

            val visited = BooleanArray(n + 1)
            val queue: Queue<Int> = LinkedList()

            queue.add(from)
            visited[from] = true

            while (queue.isNotEmpty()) {
                val node = queue.poll()

                for (next in graph[node]) {
                    if (next == to) {
                        visited[next] = true
                        break
                    }
                    if (!visited[next]) {
                        queue.add(next)
                        visited[next] = true
                    }
                }
            }

            if (!visited[to]) {
                println("NO")
                return
            }

            from = to // from 초기화
        }

        println("YES")
    }
}