package acmicpc

import java.io.BufferedReader
import java.io.*
import java.util.*
import kotlin.math.*

class Boj2805 {
    fun solution(args: Array<String>) {
        val br = BufferedReader(InputStreamReader(System.`in`))

        var (nS, mS) = br.readLine().split(" ")
        val n = nS.toInt()
        val m = mS.toLong()

        val trees = br.readLine().split(" ").map { it.toLong() }.sorted()

        var right = trees[trees.lastIndex]
        var left = 0L
        var mid = 0L // 높이 h가 될 것
        var answer = 0L

        while (right >= left) {
            mid = (right + left) / 2

            val idx = trees.binarySearch(mid)
            var sum = 0L

            if (idx >= 0) { // 인덱스를 찾음
                for (i in idx until n) {
                    sum += (trees[i] - mid)
                }
            } else {
                val appIdx = (idx + 1) * -1
                for (i in appIdx until n) {
                    sum += (trees[i] - mid)
                }
            }

            if (sum >= m) { // 넘치면 잘리는 부분이 줄어들어야 함 -> 인덱스 증가
                left = mid + 1
                answer = max(answer, mid)
            } else { // 줄여야 한다면 잘리는 부분 줄어야 함 right = mid - 1
                right = mid - 1
            }
        }

        println(answer)
    }
}