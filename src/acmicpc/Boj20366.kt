package acmicpc

import java.io.*
import java.util.*
import kotlin.math.*

fun main(args: Array<String>) {
    val br = BufferedReader(InputStreamReader(System.`in`))
    val N = br.readLine()!!.toInt()
    val nums = br.readLine().split(" ").map { it.toLong() }

    val sums = mutableListOf<Pair20366>()
    for (i in nums.indices) {
        for (j in i + 1 until N) {
            sums.add(Pair20366(i, j, nums[i] + nums[j]))
        }
    }

    sums.sortBy { it.sum }

    var ans = Long.MAX_VALUE

    // 합계에 대해서 처리
    for (l in sums.indices) {
        var r = l + 1
        while(r < sums.size) {
            val tmp = sums[r].sum - sums[l].sum

            if (!(sums[r].first != sums[l].first && sums[r].second != sums[l].second && sums[r].first != sums[l].second && sums[r].second != sums[l].first)) {
                r++
                continue
            }

            if (tmp == 0L) {
                println(0)
                return
            }

            if (ans < tmp) break // 단조 증가이므로 더이상 불가

            ans = min(ans, tmp)
            r++
        }
    }

    println(ans)
}

data class Pair20366(
    val first: Int,
    val second: Int,
    val sum: Long,
)