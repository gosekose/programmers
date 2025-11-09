package acmicpc

import java.io.*

fun main(args: Array<String>) {
    val br = BufferedReader(InputStreamReader(System.`in`))
    val (n, m) = br.readLine().split(" ").map { it.toInt() }

    val styles = mutableListOf<String>()
    val powers = mutableListOf<Long>()

    val problems = mutableListOf<Long>()
    val set = mutableSetOf<Long>()

    for (i in 0 until n) {
        val (name, thrs) = br.readLine().split(" ")
        val thr = thrs.toLong()
        if (set.contains(thr)) continue
        styles.add(name)
        powers.add(thr)
        set.add(thr)
    }

    for (i in 0 until m) {
        problems.add(br.readLine().toLong())
    } // 출력해야할 칭호

    val buffer = StringBuffer()

    for (i in problems) { // 10만 * logn
        val idx = powers.binarySearch(i)

        if (idx >= 0) buffer.append(styles[idx]).append("\n")
        else {
            val appIdx = -1 * (idx + 1)
            buffer.append(styles[appIdx]).append("\n")
        }
    }

    println(buffer.toString())
}