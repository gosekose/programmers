package acmicpc

import java.io.*
import kotlin.math.*

fun main(args: Array<String>) {
    val br = BufferedReader(InputStreamReader(System.`in`))

    val (nStr, bStr) = br.readLine().split(" ")
    val n = nStr.toInt()
    val b = bStr.toLong()

    val map = Array(n) { LongArray(n) }
    for (i in 0 until n) {
        val strs = br.readLine().split(" ").map { it.toLong() }
        for (j in strs.indices) {
            map[i][j] = strs[j]
        }
    }

    fun mul(x: Array<LongArray>, y: Array<LongArray>): Array<LongArray> {
        val z = Array(n) { LongArray(n) }
        for (i in 0 until n) {
            for (k in 0 until n) {
                val xik = x[i][k]
                if (xik == 0L) continue
                for (j in 0 until n) {
                    z[i][j] = (z[i][j] + xik * y[k][j]) % 1000
                }
            }
        }

        return z
    }

    var res = Array(n) { LongArray(n) }
    for (i in 0 until n) {
        res[i][i] = 1
    }

    var base = map
    var e = b
    while (e > 0L) {
        if (e % 2 == 1L) res = mul(res, base)
        base = mul(base, base)
        e = e shr 1
    }

    var sb = StringBuilder()
    for (i in 0 until n) {
        for (j in 0 until n) {
            sb.append(res[i][j] % 1000)
            if (j != n - 1) {
                sb.append(' ')
            }
        }
        if (i != n - 1) sb.append("\n")
    }
    println(sb.toString())
}