package acmicpc

import java.util.*
import java.io.*

fun main(args: Array<String>) {
    val br = BufferedReader(InputStreamReader(System.`in`))
    val N = br.readLine().toInt()

    val root = Node7432()

    repeat(N) {
        val lines = br.readLine()
        val parts = lines.split("\\\\".toRegex())

        var cur = root
        for (dir in parts) {
            cur = cur.children.getOrPut(dir) { Node7432(dir) }
        }
    }

    val sb = StringBuilder()

    fun dfs(node: Node7432, depth: Int) {
        if (node.name.isNotEmpty() && depth >= 0) {
            repeat(depth) { sb.append(' ') }
            sb.appendLine(node.name)
        }

        for (child in node.children.values) {
            dfs(child, depth + 1)
        }
    }

    dfs(root, -1)
    val bw = BufferedWriter(OutputStreamWriter(System.out))
    bw.write(sb.toString())
    bw.flush()
}

class Node7432(
    val name: String = ""
) {
    val children: TreeMap<String, Node7432> = TreeMap()
}