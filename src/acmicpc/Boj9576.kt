package acmicpc

import java.io.*
fun main(args: Array<String>) {
    val br = BufferedReader(InputStreamReader(System.`in`))
    val T = br.readLine()!!.toInt()

    val sb = StringBuilder()

    repeat(T) {
        val (N, M) = br.readLine().split(" ").map { it.toInt() }

        val parent = IntArray(N + 2) { it } // 자신으로 초기화

        val nodes = mutableListOf<Node9576>()
        repeat(M) {
            val (a, b) = br.readLine().split(" ").map { it.toInt() }
            nodes += Node9576(a, b)
        }

        nodes.sortWith { a, b -> if (a.e == b.e) a.s - b.s else a.e - b.e }

        var ans = 0

        for ((a, b) in nodes) {
            val p = find(a, parent)
            if (p <= b && p <= N){
                ans++
                union(p, p + 1, parent)
            }
        }

        sb.append(ans).append("\n")
    }

    val bw = BufferedWriter(OutputStreamWriter(System.out))
    bw.write(sb.toString())
    bw.flush()
}

fun find(a: Int, parent: IntArray): Int {
    if (a == parent[a]) return a
    parent[a] = find(parent[a], parent)
    return parent[a]
}

fun union(a: Int, b: Int, parent: IntArray) {
    val pa = find(a, parent)
    val pb = find(b, parent)
    parent[pa] = pb
}

data class Node9576(
    val s: Int, // 시작
    val e: Int, // 끝
)