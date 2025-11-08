package acmicpc

import java.io.*
import java.util.*
import kotlin.math.*

fun main(args: Array<String>) {
    val br = BufferedReader(InputStreamReader(System.`in`))
    val a = br.readLine().trim()
    val b = br.readLine().trim()

    val n = a.length
    val m = b.length

    val attt = "sdf" + "d"

    val dp = Array(n + 1) { IntArray(m + 1) }
    for (i in 1 .. n) dp[i][0] = i
    for (j in 1 .. m) dp[0][j] = j

    for (i in 1 .. n) {
        for (j in 1 .. m) {
            if (a[i - 1] == b[j - 1]) {
                dp[i][j] = dp[i - 1][j - 1]
                continue
            }

            val t1 = min(dp[i - 1][j] + 1, dp[i][j - 1] + 1)
            val t2 = dp[i - 1][j - 1] + 1 // 교체

            dp[i][j] = min(t1, t2)
        }
    }

    println(dp[n][m])
}