package strings

import java.io.*
import java.util.*

fun main() = BufferedReader(InputStreamReader(System.`in`)).use {
    val sb = StringBuilder()
    repeat(readlnOrNull()?.toInt() ?: 0) {
        val pq = PriorityQueue<String>()

        repeat(readlnOrNull()?.toInt() ?: 0) {
            pq.add(readlnOrNull())
        }

        var isConsistent = true

        while (pq.size > 1) {
            val current = pq.poll()
            if (pq.peek().startsWith(current)) {
                isConsistent = false
                break
            }
        }

        sb.append(if (isConsistent) "YES" else "NO").append("\n")
    }

    println(sb)
}