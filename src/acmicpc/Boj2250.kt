package acmicpc

import java.util.*
import java.io.*
import kotlin.math.*

data class Node2250(var e: Int, var l: Node2250?, var r: Node2250?, var depth: Int)

fun main(args: Array<String>) {
    val br = BufferedReader(InputStreamReader(System.`in`))
    val N = br.readLine()!!.toInt()
    val nodes = Array(N + 1) { Node2250(it, null, null, 0) }

    val hasParent = BooleanArray(N + 1)
    repeat(N) {
        val st = StringTokenizer(br.readLine())

        val e = st.nextToken().toInt()
        val l = st.nextToken().toInt()
        val r = st.nextToken().toInt()
        val node = nodes[e]
        if (l != -1) {
            node.l = nodes[l]
            hasParent[l] = true
        }

        if (r != -1) {
            node.r = nodes[r]
            hasParent[r] = true
        }
    }

    var rootIdx = 1
    for (i in 1 .. N) {
        if(!hasParent[i]) {
            rootIdx = i
            break
        }
    }

    val root = nodes[rootIdx]
    root.depth = 1

    // 노드의 최대 너비는 N, 노드 인오더 계산
    val inorders = mutableListOf<Node2250>()
    fun inOrder(node: Node2250) {
        if (node.l != null) {
            inOrder(node.l!!)
        }

        inorders += node

        if (node.r != null) {
            inOrder(node.r!!)
        }
    }

    inOrder(root) // inOrders로 left, right 처리

    val pos = Array(N + 1) { it }
    for (i in 0 until N) {
        val v = inorders[i] // 값으로 인덱스 찾기
        pos[v.e] = i
    }
    //pos는 현재 노드가 위치하게 되는 인덱스임

    val depth = Array(N + 1) { IntArray(2) }
    for (i in 0 .. N) {
        depth[i][0] = N
        depth[i][1] = 0
    }

    val queue = ArrayDeque<Node2250>()
    queue.add(root) // 루트 노드 입력

    while(queue.isNotEmpty()) {
        val node = queue.poll()
        val to   = node.depth
        val col  = pos[node.e] // 열 번호 (이것보다 작은 개수)

        // 각 depth의 가장 작은, 큰 열 구하기
        depth[to][0] = min(depth[to][0], col)
        depth[to][1] = max(depth[to][1], col)

        if(node.l != null) {
            val nextL = node.l
            nextL!!.depth = node.depth + 1
            queue.add(nextL)
        }

        if (node.r != null) {
            val nextR = node.r
            nextR!!.depth = node.depth + 1
            queue.add(nextR)
        }
    }

    var ansVal = -1
    var ansNum = 0
    for (i in 1 .. N) {
        val tmp = depth[i][1] - depth[i][0]
        if (tmp > ansVal) {
            ansNum = i
            ansVal = tmp
        }
    }

    val sb = StringBuilder()
    sb.append(ansNum).append(" ").append((ansVal + 1))

    val bw = BufferedWriter(OutputStreamWriter(System.out))
    bw.write(sb.toString())
    bw.flush()
}