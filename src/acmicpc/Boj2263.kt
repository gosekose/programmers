package acmicpc

import java.io.*
import java.util.*
import java.lang.*

var res = mutableListOf<Int>()

fun main(args: Array<String>) {
    val br = BufferedReader(InputStreamReader(System.`in`))
    val n = br.readLine()!!.toInt()

    fun readInts(): IntArray {
        val st = StringTokenizer(br.readLine())
        val arr = IntArray(n)
        var i = 0
        while (st.hasMoreTokens()) {
            arr[i++] = st.nextToken().toInt()
        }

        return arr
    }

    val inorder = readInts()
    val postorder = readInts()

    val pos = IntArray(n + 1)
    for (i in 0 until n) pos[inorder[i]] = i

    val sb = StringBuilder()
    val stack = ArrayDeque<Seg2263>()
    stack.push(Seg2263(0, n - 1, 0, n - 1))

    while(stack.isNotEmpty()) {
        val (inL, inR, postL, postR) = stack.pop()
        if (inL > inR || postL > postR) continue

        val root = postorder[postR]
        sb.append(root).append(' ')

        val k = pos[root]
        val leftSize = k - inL
        val rightSize = inR - k

        if (rightSize > 0) {
            stack.push(Seg2263(k + 1, inR, postL + leftSize, postR - 1))
        }

        if (leftSize > 0) {
            stack.push(Seg2263(inL, k - 1, postL, postL + leftSize - 1))
        }
    }

    println(sb.toString())
}

data class Seg2263(
    val inL: Int,
    val inR: Int,
    val postL: Int,
    val postR: Int
)
