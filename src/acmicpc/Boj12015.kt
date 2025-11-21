package acmicpc

import java.io.*
import java.util.*

fun main(args: Array<String>) {
    val br = BufferedReader(InputStreamReader(System.`in`))

    val N = br.readLine().toInt()
    val arr = IntArray(N)

    val str = StringTokenizer(br.readLine())
    for (i in 0 until N) {
        arr[i] = str.nextToken().toInt()
    }

    val lis = IntArray(N)
    var len = 0

    fun lowerBound(lis: IntArray, l: Int, r: Int, target: Int): Int {
        var left = l
        var right = r

        while(left < right) {
            val mid = (left + right) / 2
            if (lis[mid] >= target) {
                right = mid
            } else {
                left = mid + 1
            }
        }

        return left
    }

    /* LIS */
    for (i in 0 until N) {
        val x = arr[i]

        val pos = lowerBound(lis, 0, len, x)

        lis[pos] = x
        if (pos == len) {
            len++
        }
    }

    println(len)
}