package acmicpc

import java.io.*
import java.util.*

fun main(args: Array<String>) {
    val br = BufferedReader(InputStreamReader(System.`in`))

    val T = br.readLine()!!.toInt()

    val sb = StringBuilder()
    repeat(T) {
        val N = br.readLine()!!.toInt()

        fun readInts(): IntArray {
            val st = StringTokenizer(br.readLine())
            val a = IntArray(N)
            var i = 0
            while (st.hasMoreTokens()) a[i++] = st.nextToken().toInt()
            return a
        }

        val preorder = readInts()
        val inorder = readInts()

        val pos = IntArray(N + 1)

        for (i in 0 until N) pos[inorder[i]] = i
        fun solve(preL: Int, preR: Int, inL: Int, inR: Int) {
            if (preL > preR || inL > inR) return
            val root = preorder[preL]
            val k = pos[root]
            val leftSize = k - inL

            // 왼쪽 영역
            solve(preL + 1, preL + leftSize, inL, k - 1)

            // 오른쪽
            solve(preL + leftSize + 1, preR, k + 1, inR)

            sb.append(root).append(" ")
        }

        solve(0, N - 1, 0, N - 1)

        sb.append("\n")
    }

    val bw = BufferedWriter(OutputStreamWriter(System.out))
    bw.write(sb.toString())
    bw.flush()

}
