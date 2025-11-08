package acmicpc

import java.util.*
import java.io.*
import kotlin.math.*

fun main(args: Array<String>) {
    val br = BufferedReader(InputStreamReader(System.`in`))
    val N = br.readLine().toInt()

    val board = Array(N + 2) { IntArray(2) }// 1base
    for (i in 1 .. N) {
        val (t, p) = br.readLine().split(" ").map { it.toInt() }
        board[i][0] = t // 걸리는 시간
        board[i][1] = p // 수익
    }

    val dp = LongArray(N + 2) { 0 }

    for (k in 1 .. N) {
        val t = board[k][0]
        val p = board[k][1]


        dp[k] = max(dp[k], dp[k - 1]) // 현재값 혹은 이전 단계에서 현재로 넘어갈 때 선택하지 않는 것

        if (k + t <= N + 1) {
            dp[k + t] = max(dp[k] + p, dp[k + t])
        }
    }

    var answer = 0L
    for (i in 1 .. N + 1) {
        answer = max(answer, dp[i])
    }

    println(answer)
}
