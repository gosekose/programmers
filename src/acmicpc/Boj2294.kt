package acmicpc

import java.io.*
import kotlin.math.*

class Boj2294 {
    fun solution(args: Array<String>) {
        val br = BufferedReader(InputStreamReader(System.`in`))
        val (n, k) = br.readLine().split(" ").map { it.toInt() }

        val arr = IntArray(n + 1) // 동전의 값을 넣는 배열
        val dp = IntArray(k + 1) { Int.MAX_VALUE } // 개수의 합이 되는 최소값을 넣는 dp

        for (i in 1 .. n) {
            arr[i] = br.readLine().toInt()
        }

        dp[0] = 0

        // 일단 동전 순회를 함
        for (i in 1 .. n) {
            // j는 합계 dp에 사용할 인덱스
            for (j in arr[i] .. k) { // j는 동전 금액
                if (dp[j - arr[i]] != Int.MAX_VALUE) { // 한번 초기화가 되었다면?
                    dp[j] = min(dp[j], dp[j - arr[i]] + 1)
                }
            }
        }

        if (dp[k] == Int.MAX_VALUE) {
            println(-1)
            return
        }
        println(dp[k])
    }
}
// 1
// 1 ~ 6
// dp[1] = min(max, dp[0]) -> 1
// dp[2] = min(dp[2], dp[1]) -> 1
// dp[3] = min(dp[3], dp[2]) -> 2
// dp[4] = min(dp[4], dp[3]) -> 3
// dp[5] = min(dp[5], do[4]) -> 4
// dp[6] = min(dp[6], dp[5]) -> 5