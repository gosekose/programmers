package acmicpc

import java.io.*
import java.util.*
import kotlin.math.*

fun main(args: Array<String>) {
    val br = BufferedReader(InputStreamReader(System.`in`))
    val (N, M) = br.readLine().split(" ").map { it.toInt() }
    val nums = br.readLine().split(" ").map { it.toInt() }

    var lower  = 0
    var high = 0
    for (i in nums.indices) {
        val num = nums[i]
        lower = max(num, lower) // 원소 중 가장 큰 수가 최소
        high += num // 모든값 합산이 최대 경계
    }

    while(lower <= high) {
        var mid = (lower + high) / 2
        var cnt = isPossible(mid, N, nums)
        if (cnt > M) {
            lower = mid + 1
        } else {
            high = mid - 1
        }

    }

    val sb = StringBuilder()
    sb.append(lower).append("\n")

    var cnt = 0
    var sum = 0
    var m = M

    for (i in 0 until N) {
        sum += nums[i]
        if (sum > lower) {
            m--
            sum = nums[i]
            sb.append(cnt).append(" ")
            cnt = 1
        } else {
            cnt++
        }

        if (m == N - i) break
    }

    while (m > 0) {
        sb.append(cnt).append(" ");
        cnt = 1
        m--
    }

    val bw = BufferedWriter(OutputStreamWriter(System.out))
    bw.write(sb.toString())
    bw.flush()

}

fun isPossible(mid: Int, N: Int, nums: List<Int>): Int {
    var sum = 0
    var cnt = 1

    for (i in 0 until N) {
        sum += nums[i]
        if (sum > mid) {
            cnt++
            sum = nums[i]
        }
    }

    return cnt
}
