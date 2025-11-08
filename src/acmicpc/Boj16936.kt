package acmicpc

import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.StringTokenizer

fun main(args: Array<String>) {
    val br = BufferedReader(InputStreamReader(System.`in`))
    val n = br.readLine().trim().toInt()
    val st = StringTokenizer(br.readLine())
    val b = LongArray(n) { st.nextToken().toLong() }

    val set = HashSet<Long>(n * 2)
    for (x in b) set.add(x)

    var start = b[0]
    for (x in b) {
        val noMul = !(x % 2L == 0L && set.contains(x / 2L))
        val noDiv = !set.contains(x * 3L)

        if (noMul && noDiv) {
            start = x
            break
        }
    }

    val ans = ArrayList<Long>(n)
    ans.add(start)

    val used = HashSet<Long>()
    used.add(start)

    repeat(n - 1) {
        val cur = ans.last()
        var moved = false

        if (cur % 3L == 0L) {
            val next = cur / 3L
            if (next in set && next !in used) {
                ans.add(next)
                used.add(next)
                moved = true
            }
        }

        if (!moved) {
            val next = cur * 2L
            if (next in set && next !in used) {
                ans.add(next)
                used.add(next)
                moved = true
            }
        }
    }

    println(ans.joinToString(" "))
}