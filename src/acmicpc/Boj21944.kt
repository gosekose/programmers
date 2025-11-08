package acmicpc

import java.util.*
import java.io.*

data class Node21944(
    val p: Int,
    val l: Int,
    val g: Int
)

fun main(args: Array<String>) {
    val br = BufferedReader(InputStreamReader(System.`in`))

    val N = br.readLine().toInt()

    val tree = TreeSet<Node21944>() { a, b -> if (a.l == b.l) a.p - b.p else a.l - b.l }
    val map = mutableMapOf<Int, Node21944>() // 문제번호로 노드 찾기
    val treeG = mutableMapOf<Int, TreeSet<Node21944>>()

    repeat(N) {
        val (P, L, G) = br.readLine().trim().split(" ").map { it.toInt() }

        val node = Node21944(P, L, G)
        tree.add(node)
        map[P] = node

        if (treeG[G] == null) {
            treeG[G] = TreeSet<Node21944>() {a, b -> if (a.l == b.l) a.p - b.p else a.l - b.l }
            treeG[G]!!.add(node)
        } else {
            treeG[G]!!.add(node)
        }
    }

    val m = br.readLine().toInt()
    val sb = StringBuilder()

    repeat(m) {
        val st = StringTokenizer(br.readLine())

        when(st.nextToken()) {
            "add" -> {
                val p = st.nextToken().toInt()
                val l = st.nextToken().toInt()
                val g = st.nextToken().toInt()

                val node = Node21944(p, l, g)
                map[p] = node
                tree.add(node)

                if (treeG[g] == null) {
                    treeG[g] = TreeSet<Node21944>() {a, b -> if (a.l == b.l) a.p - b.p else a.l - b.l }
                    treeG[g]!! += node
                } else {
                    treeG[g]!! += node
                }
            }

            "solved" -> {
                val p = st.nextToken().toInt()

                val node = map[p]!!

                treeG[node.g]!!.remove(node) // G에속한 node 제거
                map.remove(p) // p 제거
                tree.remove(node) // 트리 노드 제거
            }

            "recommend" -> {
                val g = st.nextToken().toInt()
                val x = st.nextToken().toInt()

                val ans = if (x == -1) treeG[g]!!.first().p else treeG[g]!!.last().p
                sb.append(ans).append("\n")
            }

            "recommend2" -> {
                val x = st.nextToken().toInt()

                val ans = if (x == -1) tree.first().p else tree.last().p
                sb.append(ans).append("\n")
            }

            else -> {
                val x = st.nextToken().toInt()
                val l = st.nextToken().toInt()

                val target = Node21944(0,l, 0)
                if (x == 1) {
                    val ans = if (tree.ceiling(target) == null) -1 else tree.ceiling(target)!!.p
                    sb.append(ans).append("\n")
                } else {
                    val ans = if (tree.floor(target) == null) -1 else tree.floor(target)!!.p
                    sb.append(ans).append("\n")
                }
            }
        }
    }

    val bw = BufferedWriter(OutputStreamWriter(System.out))

    bw.write(sb.toString())
    bw.flush()
}