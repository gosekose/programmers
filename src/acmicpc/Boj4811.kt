package acmicpc

import java.io.*
import kotlin.math.*

class Boj4811 {
    fun solution(args: Array<String>) {
        val br = BufferedReader(InputStreamReader(System.`in`))
        val (t, w) = br.readLine().split(" ").map { it.toInt() }

        val dp = Array(w + 1) { Array(3) { IntArray(2) } }
        val arr = Array(t + 1) { IntArray(3) }

        for (i in 1..t) {
            val k = br.readLine().toInt()
            if (k == 1) arr[i][1] = 1
            else arr[i][2] = 1
        } // 이차원 배열로 만들고

        // 시작할 때 움직이지 않은 dp
        val min = min(t, w)
        for (i in 1..t) {
            for (j in 0..min) { // j는 걸음수
                if (j == 0) { //걷지 않는 경우는 그 자리 위치에서 배열만 더해주는 개념
                    dp[j][1][0] += arr[i][1] // 그자리에 계속 더함
                    // 걷지 않는다면 dp[0][2]는 절대 갈 수 없음
                } else {
                    dp[j][1][0] = max(dp[j][1][0], dp[j - 1][2][0]) + arr[i][1]
                    dp[j][2][0] = max(dp[j][2][0], dp[j - 1][1][0]) + arr[i][2]
                }
            }
        }

        // dp 시작할 때 움직임 -> 0자리
        for (i in 1..t) {
            for (j in 1..min) { // j는 걸음수
                if (j == 1) { // 딱 한번만 걸은 경우
                    dp[j][2][1] += arr[i][2] // 그자리에 계속 더함
                    // 한 번만 걸었다면 dp[1][1]는 절대 갈 수 없음
                } else {
                    dp[j][1][1] = max(dp[j][1][1], dp[j - 1][2][1]) + arr[i][1]
                    dp[j][2][1] = max(dp[j][2][1], dp[j - 1][1][1]) + arr[i][2]
                }
            }
        }

        var max = 0
        for (i in 0..w) {
            val maxList = arrayOf(dp[i][1][0], dp[i][2][0], dp[i][1][1], dp[i][2][1])
            max = max(maxList.max(), max)
        }
        println(max)
    }
}

//  1  2  3 4  5  6  7
//     0. 0       0. 0
//  0       0. 0

// i = 3
// j = 1
// dp[1][1]

// arr[1]  arr[2] arr[3]  arr[4]  arr[5]  arr[6]   arr[7]
// w = 0   w = 0  w = 0   w = 0   w = 0   w = 0    w = 0
// w = 1   w = 1  w = 1   w = 1   w = 1
//         w = 2  w = 2                   w = 2    w = 2
//                w = 3
