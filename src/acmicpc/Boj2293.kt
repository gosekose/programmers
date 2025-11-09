package acmicpc

import java.io.*

class Boj2293 {
    fun solution(args: Array<String>) {
        val br = BufferedReader(InputStreamReader(System.`in`))

        val (n, k) = br.readLine().split(" ").map { it.toInt() }

        val arr = IntArray(n + 1)
        val dp = IntArray(k + 1)

        dp[0] = 1

        for (i in 1 .. n) {
            arr[i] = br.readLine().toInt()
        }

        // 입력한 동전을 순회
        for (i in 1 .. n) {
            // 입력한 동전에서부터 구해야 하는 입력값까지 순회
            for (j in arr[i] .. k) {
                // 구해야 하는 입력값에 입력값 - 동전의 값을 뺀 dp 구하기
                dp[j] += dp[j - arr[i]]
            }
        }

        println(dp[k])
    }
}
// i = 1
// j: arr[1] = 1
// dp[1] += dp[0] -> 동전 1원에 대한 dp 개수는 dp[0] (1원에서 1원을 뺀 금액의 dp)
// dp[2] += dp[2 - 1] -> 동전 2원은 2 - 1 (2원에서 1원을 뺀 것의 개수)

