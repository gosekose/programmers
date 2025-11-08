package acmicpc

import java.io.*
import java.util.*

fun main(args: Array<String>) {
    val br = BufferedReader(InputStreamReader(System.`in`))

    val pre = mutableListOf<Int>()
    while (true) {
        try {
            val line = br.readLine() ?: break
            val t = line.trim()
            pre.add(t.toInt())
        } catch (e : Exception) {
            break
        }
    }

    fun findListBst(): Node5639? {
        if (pre.isEmpty()) return null
        val node = Node5639(pre[0], null, null)
        val stack = ArrayDeque<Node5639>()
        stack.addLast(node)

        for (i in 1 until pre.size) {
            val x = pre[i]
            val next = Node5639(x, null, null)

            // 현재 스택보다 작으면 자식 노드임
            if (x < stack.last().v) {
                stack.last().l = next
                stack.addLast(next)
                continue
            }

            var last = stack.removeLast()

            while(stack.isNotEmpty() && stack.last().v < x) {
                last = stack.removeLast() // 현재 노드가 스택의 값보다 작을 떄 까지
            }

            last.r = next
            stack.addLast(next)
        }

        return node
    }

    val rootNode = findListBst()

    if (rootNode == null) return

    val list = mutableListOf<Node5639>()

    fun postOrder(node: Node5639) {
        if (node.l != null) {
            postOrder(node.l!!)
        }

        if (node.r != null) {
            postOrder(node.r!!)
        }

        list.add(node)
    }

    postOrder(rootNode)

    val sb = StringBuilder()

    for (num in list) {
        sb.append(num.v).append("\n")
    }

    val bw = BufferedWriter(OutputStreamWriter(System.out))

    bw.write(sb.toString())
    bw.flush()
}

data class Node5639(
    var v: Int,
    var l: Node5639?,
    var r: Node5639?
)