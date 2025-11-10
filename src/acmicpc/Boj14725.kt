package acmicpc

import java.util.*
import java.io.*

fun main(args: Array<String>) {
    val br = BufferedReader(InputStreamReader(System.`in`))

    val N = br.readLine().toInt()

    val root = Node14725("", mutableMapOf(), mutableListOf())

    repeat(N){
        val str = StringTokenizer(br.readLine())
        val K = str.nextToken().toInt()

        var parentNode = root

        for (i in 0 until K) {
            val word = str.nextToken()

            /* 이미 포함되어 있으면 그대로 사용 */
            if (parentNode.childMap.containsKey(word)) {
                val current = parentNode.childMap[word]!!
                parentNode = current // 부모노드 변경하며 적용

            }
            else { /* 없는 경우 신규 할당 */
                val current = Node14725(word, mutableMapOf(), mutableListOf())
                parentNode.childMap.put(word, current)
                parentNode.child.add(current)

                parentNode = current
            }
        }
    }

    val sb = StringBuilder()

    /* 재귀적으로 순회하며 처리 */
    fun preOrder(node: Node14725, depth: Int) {
        node.child.sortWith(compareBy { it.word }) // 자식 노드 사전순 정렬

        if (root != node) {
            for (i in 0 until depth) {
                sb.append("--")
            }
            sb.append(node.word).append("\n")
        }

        for (next in node.child) {
            preOrder(next, depth + 1)
        }
    }

    preOrder(root, -1)

    val bw = BufferedWriter(OutputStreamWriter(System.out))
    bw.write(sb.toString())
    bw.flush()
}

data class Node14725(
    val word: String,
    val childMap: MutableMap<String, Node14725>,
    val child: MutableList<Node14725>
)