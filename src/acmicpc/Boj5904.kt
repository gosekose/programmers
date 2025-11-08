package acmicpc

import java.io.*
fun main(args: Array<String>) {
    val br = BufferedReader(InputStreamReader(System.`in`))
    val N = br.readLine()!!.toLong()

    var k = 0
    var L = ArrayList<Long>()
    L.add(3L)

    while(L[k]< N) {
        val kk = k + 1
        val next = 2L * L[k] + (kk + 3).toLong()
        L.add(next)
        k++
    }

    fun conquer(n: Long, t: Int): Char {
        if (t == 0) {
            return if (n == 1L) 'm' else 'o'
        }

        val left = L[t - 1]
        val mid = (t + 3).toLong()
        return when {
            n <= left -> conquer(n, t - 1)
            n == left + 1 -> 'm'
            n <= left + mid -> 'o'
            else -> conquer(n - left - mid, t - 1)
        }
    }

    val ans = conquer(N, k)
    println(ans)
}