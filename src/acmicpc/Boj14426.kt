package acmicpc

import java.util.*
import java.io.*

class Test14426 {
    fun lowerBound(arr: Array<String>, key: String): Int {
        var left = 0
        var right = arr.size

        while(left < right) {
            val mid = (left + right) / 2

            val target = arr[mid]

            if (target < key) {
                left = mid + 1
            } else {
                right = mid
            }
        }

        return left
    }

    fun solve(){
        val br = BufferedReader(InputStreamReader(System.`in`))

        val str = StringTokenizer(br.readLine())
        val N = str.nextToken().toInt()
        val M = str.nextToken().toInt()

        val arr = Array(N) { "" }

        for (i in 0 until N) {
            arr[i] = br.readLine()
        }

        arr.sort()

        var ans = 0

        repeat(M) {
            val q = br.readLine()
            val idx = lowerBound(arr, q)
            if (idx < N && arr[idx].startsWith(q)) {
                ans++
            }
        }

        val bw = BufferedWriter(OutputStreamWriter(System.out))
        bw.write("$ans")
        bw.flush()
    }
}


fun main(args: Array<String>) {
    val test = Test14426()
    test.solve()
}