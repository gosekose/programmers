package acmicpc

import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.*

fun main(args: Array<String>) {
    val br = BufferedReader(InputStreamReader(System.`in`))

    val T = br.readLine().trim().toInt()
    val res = StringBuilder()
    repeat(T) {
        val n = br.readLine().trim().toInt()
        val st = StringTokenizer(br.readLine())

        val coins = mutableListOf<Int>()
        while(st.hasMoreTokens()) {
            coins += st.nextToken().toInt()
        }
        val m = br.readLine().trim().toInt()

        val dp = LongArray(m + 1)
        dp[0] = 1L

        // 현재 구해야하는 금액
        for (coin in coins) {
            for (k in coin .. m) {
                dp[k] += dp[k - coin]
            }
        }

        res.append(dp[m]).append("\n")
    }

    val bw = BufferedWriter(OutputStreamWriter(System.out))

    bw.write(res.toString())
    bw.flush()
}