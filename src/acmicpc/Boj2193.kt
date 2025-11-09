package acmicpc

import java.io.*

class Boj2193 {

    fun solution(args: Array<String>) {
        val br = BufferedReader(InputStreamReader(System.`in`))
        val n = br.readLine().toInt()

        val arr = LongArray(n + 1)

        arr[1] = 1 // 1만 존재

        if (n == 1) {
            println(1)
            return
        }

        if (n == 2) {
            println(1)
            return
        }

        arr[2] = 1

        for (i in 3..n) {
            arr[i] = arr[i - 1] + arr[i - 2]
        }

        println(arr[n])
    }

}

// 1

// 10

// 100
// 101

// 1000
// 1001
// 1010

// 10000
// 10001.
// 10010.
// 10100
// 10101

// 100000
// 100001
// 100010
// 100100
// 100101
// 101000
// 101010
// 101001

// 1000000
// 1000001
// 1000010
// 1000100
// 1000101
// 1001000
// 1001001
// 1001010
// 1010000
// 1010001
// 1010010
// 1010100
// 1010101

// 누적합 3부터 (이전항 + 전전항)
// 1 1 2 3 5 8 13