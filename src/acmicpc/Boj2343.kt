package acmicpc

import java.io.*
import java.util.*
import kotlin.math.*

fun main(args: Array<String>) {
    val br = BufferedReader(InputStreamReader(System.`in`))
    val (N, M) = br.readLine().split(" ").map { it.toLong() }

    val lectures = br.readLine().split(" ").map { it.toLong() }

    var left = 1L
    var right = N * 10_001L
    var ans = 1L

    while(left <= right) {
        val mid = (left + right) / 2

        var cnt = 1L
        var total = 0L

        for (lec in lectures) {

            if (total + lec > mid) {
                total = lec
                cnt++
            } else {
                total += lec
            }
        }

        if (cnt <= M) {
            ans = mid
            right = mid - 1
        } else {
            left = mid + 1
        }
    }

    println(ans)
}

// n = 20 -> 1000000
// 시간 복잡도 최대 200만