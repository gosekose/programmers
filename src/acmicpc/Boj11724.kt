package acmicpc

fun main(args: Array<String>) {
    // 정점, 간선
    val (N, M) = readln().split(" ").map { it.toInt() }
    val graph = Array(N + 1) { mutableListOf<Int>() }
    val visited = BooleanArray(N + 1) { false }

    repeat(M) {
        val (e, v) = readln().split(" ").map {it.toInt()}
        graph[e].add(v)
        graph[v].add(e)
    }

    var answer = 0;
    for (i in 1 .. N) {
        if (visited[i]) continue
        answer++
        val queue = ArrayDeque<Int>()
        queue.add(i);

        visited[i] = true
        while(queue.isNotEmpty()) {
            val cur = queue.removeFirst()
            for (next in graph[cur]) {
                if (visited[next]) continue
                visited[next] = true
                queue.add(next)
            }
        }
    }
    val arr = IntArray(N) { it }

    println(answer)


}