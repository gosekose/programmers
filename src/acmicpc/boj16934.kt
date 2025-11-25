package acmicpc

import java.util.*
import java.io.*

fun main(args: Array<String>) {
    val br = BufferedReader(InputStreamReader(System.`in`))

    val N = br.readLine().toInt()

    val tree = TreeSet<String>()
    val counts = HashMap<String, Int>()
    val res = StringBuilder()

    for (i in 0 until N) {
        val str = br.readLine()

        var alias: String? = null
        val sb = StringBuilder()
        for (s in str) {
            sb.append(s)

            val p = sb.toString()
            val candidate = tree.ceiling(p)

            if (candidate == null || !candidate.startsWith(p)) {
                alias = p
                break
            }
        }

        val cnt = (counts[str] ?: 0) + 1
        counts[str] = cnt

        /* 축약을 할 수 없는 경우 */
        if (alias == null) {
            alias = if (cnt == 1) {
                str
            } else {
                str + cnt
            }
        }

        res.appendLine(alias)
        tree.add(str)
    }

    val bw = BufferedWriter(OutputStreamWriter(System.out))

    bw.write(res.toString())
    bw.flush()
}
