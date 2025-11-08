package acmicpc

import kotlin.math.*

fun main(args: Array<String>) {
    val N = readln().toLong()
    val K = readln().toLong()

    var lo = 1L
    var hi = K
    var ans = hi

    fun countLE(x: Long): Long {
        var cnt = 0L
        var i = 1L

        while(i <= N) {
            cnt += min(N, x / i)
            i++
        }

        return cnt
    }

    while(lo <= hi) {
        val mid = (lo + hi) / 2
        if (countLE(mid) >= K) {
            ans = mid
            hi = mid - 1
        } else {
            lo = mid + 1
        }
    }

    println(ans)
}



