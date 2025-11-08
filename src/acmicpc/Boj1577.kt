package acmicpc

import java.io.*
import java.util.*

fun main(args: Array<String>) {
    val br = BufferedReader(InputStreamReader(System.`in`))

    // M row, N: col
    val (N, M) = br.readLine().split(" ").map { it.toInt() }
    val k = br.readLine()!!.toInt()

    val map = mutableSetOf<String>()

    repeat(k) {
        val (a, b, c, d) = br.readLine().split(" ").map { it.toInt() }
        map.add("${b}-${a}-${d}-${c}")
        map.add("${d}-${c}-${b}-${a}")
    }

    // 이동은 반드시 상 혹은 우측 이동 -> 자신 기준 아래, 위에서 올라오는 것으로 처리
    val dr = arrayOf(-1, 0)
    val dc = arrayOf(0, -1)

    val dp = Array(M + 1) { LongArray(N + 1) }

    // row
    dp[0][0] = 1L
    for (i in 1 .. M) {
        val key = "${i-1}-0-${i}-0"
        if (map.contains(key)) continue
        dp[i][0] = dp[i - 1][0]
    }

    // col
    for (i in 1 .. N) {
        val key = "0-${i-1}-0-${i}"
        if (map.contains(key)) continue
        dp[0][i] = dp[0][i - 1]
    }

    for (r in 1 .. M) {
        for (c in 1 .. N) {
            for (k in 0 until 2) {
                val nr = r + dr[k]
                val nc = c + dc[k]

                if (nr < 0 || nr > M || nc < 0 || nc > N) continue

                val key = "${nr}-${nc}-${r}-${c}"
                if (!map.contains(key)) dp[r][c] += dp[nr][nc]
            }
        }
    }

    println(dp[M][N])
}