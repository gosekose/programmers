package acmicpc

import kotlin.math.*
import java.io.*

class Boj1463 {
    fun solution(args: Array<String>) {
        val br = BufferedReader(InputStreamReader(System.`in`))
        val n = br.readLine().toInt()

        val arr = IntArray(n + 1) { Int.MAX_VALUE }

        arr[1] = 0
        for (i in 2..n) {
            if (i % 3 == 0) {
                arr[i] = min(arr[i / 3] + 1, arr[i])
            }

            if (i % 2 == 0) {
                arr[i] = min(arr[i / 2] + 1, arr[i]) // 최소값 갱신
            }

            arr[i] = min(arr[i - 1] + 1, arr[i])
        }

        println(arr[n])
    }
}