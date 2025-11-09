package acmicpc

import java.io.*

class Boj1786 {
    fun solution(args: Array<String>) {
        val br = BufferedReader(InputStreamReader(System.`in`))

        val t = br.readLine()
        val p = br.readLine()

        val result = kmp(text = t, pattern = p)

        println(result.size)
        for (re in result) {
            print("$re ")
        }
    }

    private fun makeTable(pattern: String): IntArray {
        val n = pattern.length
        val table = IntArray(n)

        var idx = 0
        for (i in 1 until n) {
            while (idx > 0 && pattern[i] != pattern[idx]) {
                idx = table[idx - 1]
            }

            if (pattern[idx] == pattern[i]) {
                idx += 1
                table[i] = idx
            }
        }

        return table
    }

    private fun kmp(text: String, pattern: String): List<Int> {
        val n = text.length
        val m = pattern.length

        val table = makeTable(pattern)
        val result = mutableListOf<Int>()

        var idx = 0

        for (i in 0 until n) {
            while (idx > 0 && text[i] != pattern[idx]) {
                idx = table[idx - 1]
            }

            if (text[i] == pattern[idx]) {
                if (idx == m - 1) {
                    result.add(i - m + 2)
                    idx = table[idx]
                } else idx++
            }
        }

        return result
    }
}