package acmicpc

import java.io.BufferedReader
import java.io.InputStreamReader

class Boj2512 {
    fun solution(args: Array<String>) {
        val br = BufferedReader(InputStreamReader(System.`in`))

        val n = br.readLine().toInt()
        val list = br.readLine().split(" ").map { it.toInt() }.sorted()
        val max = br.readLine().toInt()

        var right = list[n - 1]
        var left = 0
        var mid = 0

        while (right >= left) {
            mid = (right + left) / 2

            var totalBudget = max
            for (budge in list) {
                totalBudget -= if (budge > mid) mid else budge
            }

            if (totalBudget >= 0) left = mid + 1
            else right = mid - 1
        }

        println(right)
    }
}