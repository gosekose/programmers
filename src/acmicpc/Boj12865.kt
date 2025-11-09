package acmicpc

import java.io.*
import kotlin.math.*

fun main(args: Array<String>) {
    val br = BufferedReader(InputStreamReader(System.`in`))

    val (n, k) = br.readLine().split(" ").map { it.toInt() }
    val dp = Array(n + 1) { IntArray(k + 1) }
    val weight = IntArray(n + 1)
    val value = IntArray(n + 1)

    for (i in 1 .. n) {
        val (w, v) = br.readLine().split(" ").map { it.toInt() }
        weight[i] = w
        value[i] = v
    }

    for (i in 1 .. n) { // 배낭의 인덱스
        for (j in 1 .. k) { // 담아야 할 무게
            val w = weight[i] // 현재 인덱스에 속한 배낭의 무게
            if (w > j) { // 배낭의 무게가 담아야 할 무게보다 크다면
                dp[i][j] = dp[i - 1][j] // 이전 인덱스의 값
            }

            else { // 배낭에 있는 무게가 담아야할 무게보다 같거나 작다면
                dp[i][j] = max(dp[i-1][j], dp[i-1][j-w] + value[i])
            }
        }
    }

    println(dp[n][k])
}

//          k
//          1     2     3     4     5     6     7
//  i   1   0     0     0     0     0     13    13
//      2   0     0     0     8     8     13    13
//      3   0     0     6     8     8     13    14
//      4   0     0     6     8     12    13    14
