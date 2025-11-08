package acmicpc

import java.util.*
import java.io.*
import kotlin.math.*

fun main(args: Array<String>) {
    val br = BufferedReader(InputStreamReader(System.`in`))
    val (n, m) = br.readLine().split(" ").map { it.toInt() } // m 가로칸

    val names = IntArray(n + 1)
    for (i in 1 .. n) {
        names[i] = br.readLine()!!.toInt()
    }

    val sums = IntArray(n + 1)
    for (i in 1 .. n) {
        sums[i] = sums[i - 1] + names[i]
    }

    fun used(i: Int, j: Int): Int {
        return sums[j] - sums[i - 1] + (j - i)
    }

    val INF = 1000L * (1000L * 1000L) // 최대 약 10억
    val dp = LongArray(n + 2) {INF}// dp[i]는 i ~ n까지 단어를 쓸 때의 최소 비용
    dp[n + 1] = 0L

    for (i in n downTo 1) {
        var j = i

        while(j <= n) {
            // u는 i ~ j까지 한 줄에 쓸 때의 사용한 칸수
            val u = used(i, j)

            if (u > m) {
                break
            }

            val cost = if (j == n) 0L else {
                val t = (m - u).toLong()
                t * t
            }

            dp[i] = min(dp[i], cost + dp[j + 1])
            j++
        }
    }

    println(dp[1])
}