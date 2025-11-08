package acmicpc
import java.io.*
import kotlin.math.*

fun main(args: Array<String>) {
    val br = BufferedReader(InputStreamReader(System.`in`))
    val (N, R) = br.readLine().split(" ").map { it.toInt() }
    val graph = Array(N + 1) { mutableListOf<Node20924>() }

    repeat(N - 1) {
        val (e, v, d) = br.readLine().split(" ").map { it.toInt() }
        graph[e].add(Node20924(v, d))
        graph[v].add(Node20924(e, d))
    }

    // 루트 노드부터 기둥 노드까지 찾기
    var pillarNode = R
    var pillarDist = 0L
    val visited = BooleanArray (N + 1)

    fun findPillar(node: Int, root: Int) {
        // 현재 루트노드라면, 해당 연결 그래프는 1개여야 함
        visited[node] = true

        if (node == R) {
            if (graph[node].size == 1) {
                val next = graph[node][0]
                pillarNode = next.v
                pillarDist += (next.d).toLong()

                findPillar(next.v, node) // 다음 노드 처리
            } else return
        } else {
            if (graph[node].size == 2) { // 두개가 있는 경우
                var next = -1
                for (adj in graph[node]) {
                    if (adj.v != root) {
                        next = adj.v
                        pillarDist += (adj.d).toLong()
                    }
                }

                if (next == -1) return

                pillarNode = next
                findPillar(next, node) // 다음 노드로 처리
            } else if (graph[node].size == 1) { // 리프 노드라면
                var next = graph[node][0]
                pillarNode = next.v
            } else return // 그 외에는 기둥 노드가 될 수 없음
        }
    }


    findPillar(R, -1) // 기둥 노드 찾기

    var leafDist = 0L
    // dfs - 백트래킹 처리 기존 기둥노드로 세팅한 곳은 탐색하지 않음

    fun findLeaf(node: Int, dist: Long) {
        leafDist = max(leafDist, dist)

        for (adj in graph[node]) {
            if (visited[adj.v]) continue // 이미 방문한 곳은 탐색하지 않음
            visited[adj.v] = true
            findLeaf(adj.v, dist + (adj.d).toLong())
            visited[adj.v] = false
        }
    }

    findLeaf(pillarNode, 0L)

    val sb = StringBuilder()

    sb.append(pillarDist).append(" ").append(leafDist)

    println(sb.toString())
}

data class Node20924(
    val v: Int,
    val d: Int
)