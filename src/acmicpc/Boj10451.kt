package acmicpc

import javax.swing.text.html.HTML.Attribute.N

var answer = 0L

fun main(args: Array<String>) {
    val T = readLine()!!.toInt()
    val sb = StringBuilder()

    repeat(T) {
        answer = 0L
        val N = readLine()!!.toInt()
        val numbers = readln().split(" ").map {it.toInt()}
        val visited = BooleanArray (N + 1) { false }

        for (i in 1 .. N) {
            if (visited[i]) continue;
            dfs(i, numbers, visited)
        }

        sb.append(answer).append("\n")
    }

    val bw = System.out.bufferedWriter()
    bw.write(sb.toString())
    bw.flush()
}

fun dfs (cur: Int, numbers: List<Int>, visited: BooleanArray) {
    visited[cur] = true
    val next = numbers[cur - 1]

    // 이미 방문한 노드라면 사이클이 시작되었음
    if (visited[next]) {
        answer++
        return
    }

    dfs(next, numbers, visited)


}

var map : Array<Array<IntArray>> = arrayOf()



// 가로: col 이동, row,col 이동            0
// 세로: row 이동, row,col 이동            1
// 대각: row 이동, col 이동, row,col 이동   2