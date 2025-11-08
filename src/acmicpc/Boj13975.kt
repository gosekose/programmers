package acmicpc

import java.util.*
import java.io.*

fun main(args: Array<String>) {
    val br = BufferedReader(InputStreamReader(System.`in`))

    val T = br.readLine()!!.toInt()
    val sb = StringBuilder()

    repeat(T) {
        val K = br.readLine()!!.toInt()
        var ans = 0L

        val nums = br.readLine()!!.split(" ").map { it.toLong() }

        val pq = PriorityQueue<Long>()

        for (num in nums) {
            pq.add(num)
        }

        while(pq.isNotEmpty()) {
            val first  = pq.poll()
            val second = pq.poll()

            val sum = first + second
            ans += sum
            if (pq.isEmpty()) break

            pq.add(sum)
        }

        sb.append(ans).append("\n")
    }

    println(sb.toString())
}