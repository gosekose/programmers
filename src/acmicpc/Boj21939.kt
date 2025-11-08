package acmicpc

import java.util.*
import java.io.*
import java.lang.*

data class Node21939(
    val p: Int,
    val l: Int
)
fun main(args: Array<String>) {
    val br = BufferedReader(InputStreamReader(System.`in`))
    val N = br.readLine().toInt()

    val tree = TreeSet<Node21939>() { a, b -> if (a.l == b.l) a.p - b.p else a.l - b.l }

    val map = mutableMapOf<Int, Int>() // P문제의 L 난이도

    repeat(N) {
        val (p, l) = br.readLine().trim().split(" ").map { it.toInt() }
        val node = Node21939(p, l)
        map[p] = l
        tree.add(node)
    }

    val m = br.readLine().toInt()
    val sb = StringBuilder()

    repeat(m) {
        val strs = StringTokenizer(br.readLine())
        when(strs.nextToken()) {
            "add" -> {
                // 추가라면
                val p = strs.nextToken().toInt()
                val l = strs.nextToken().toInt()
                map[p] = l // p 문제의 난이도
                tree.add(Node21939(p, l))
            }

            "solved" -> {
                val p = strs.nextToken().toInt()

                val l = map[p]!! // 제거해야하는 값
                tree.remove(Node21939(p, l))
                map.remove(p)
            }

            else -> {
                val x = strs.nextToken().toInt()

                val ans = if (x == 1) tree.last().p else tree.first().p
                sb.append(ans).append("\n")
            }
        }
    }

    val bw = BufferedWriter(OutputStreamWriter(System.out))
    bw.write(sb.toString())
    bw.flush()
}