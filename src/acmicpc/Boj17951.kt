package acmicpc

import java.util.*
import java.io.*

fun main(args: Array<String>) {
    val br = BufferedReader(InputStreamReader(System.`in`))

    var str = StringTokenizer(br.readLine())

    val N = str.nextToken().toInt()
    val K = str.nextToken().toInt()

    str = StringTokenizer(br.readLine())

    val tests = mutableListOf<Int>()
    for (i in 0 until N) {
        val token = str.nextToken().toInt()
        tests.add(token)
    }
    val total = tests.sum()

    fun lowerBound(): Int {
        var left = 0
        var right = total // 시험지의 모든 문제를 다 맞춘 최대 점수

        var maxScore = 0

        while(left <= right) {
            val mid = (left + right) / 2
            var k = 0
            var tmp = 0

            for (score in tests) {
                tmp += score

                if (tmp >= mid) {
                    k++
                    tmp = 0
                }
            }

            /* K 집합보다 더 만들어지는 경우 mid 점수가 더 높아질 수 있음 */
            /* mid = 집합의 최솟값의 값을 더 크게 만들 수 있는 가능성 존재 */
            if (k >= K) {
                left = mid + 1
                maxScore = mid
            } else {
                right = mid - 1
            }
        }

        return maxScore
    }

    val ans = lowerBound()

    val bw = BufferedWriter(OutputStreamWriter(System.out))
    bw.write("$ans")
    bw.flush()
}