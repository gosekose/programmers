package acmicpc

import java.io.*
import java.util.*

fun main(args: Array<String>) {
    val br = BufferedReader(InputStreamReader(System.`in`))

    val N = br.readLine().toInt()

    val deque = ArrayDeque<Int>()
    val sb = StringBuilder()

    repeat(N) {
        val str = StringTokenizer(br.readLine())
        val command = str.nextToken()

        if (command == "push") {
            val num = str.nextToken().toInt()
            deque.add(num)
        } else if (command == "pop") {
            if (deque.isEmpty()) {
                sb.append("-1").append("\n")
            } else {
                val num = deque.removeFirst()
                sb.append(num).append("\n")
            }
        } else if (command == "size") {
            sb.append(deque.size).append("\n")
        } else if (command == "empty") {
            val target = if (deque.isEmpty()) "1" else "0"

            sb.append(target).append("\n")
        } else if (command == "front") {
            if (deque.isEmpty()) {
                sb.append("-1").append("\n")
            } else {
                val num = deque.peekFirst()
                sb.append("$num").append("\n")
            }
        } else if (command == "back") {
            if (deque.isEmpty()) {
                sb.append("-1").append("\n")
            } else {
                val num = deque.peekLast()
                sb.append("$num").append("\n")
            }
        }
    }

    val bw = BufferedWriter(OutputStreamWriter(System.out))
    bw.write(sb.toString())
    bw.flush()
}