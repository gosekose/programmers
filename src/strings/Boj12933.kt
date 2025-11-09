package strings
import java.io.*

fun main() {
    val solution12933 = Solution12933()
    solution12933.solution()
}

class Solution12933 {
    private val quack = arrayOf('q', 'u', 'a', 'c', 'k')

    fun solution() {
        val arr = BufferedReader(InputStreamReader(System.`in`)).readLine().toCharArray()

        if ((arr.size % 5) != 0) {
            println(-1)
            return
        }

        var remain = arr.size
        var count = 0

        while (remain > 0) {
            var isQuackRemoved = false
            var isFirst = true
            var quackPt = 0

            for (i in 0..arr.lastIndex) {

                if (arr[i] == quack[quackPt]) {
                    arr[i] = FINISH
                    quackPt++ // 찾아야할 다음 단어
                }

                if (quackPt != 0 && quackPt % quack.size == 0) {
                    if (isFirst) {
                        count++
                        isFirst = false
                        isQuackRemoved = true
                    }
                    quackPt = 0
                    remain -= quack.size
                }
            }

            if (!isQuackRemoved) {
                println(-1) // 만약 quack을 빼지 못했는데 문자가 남아있다면 -1
                return
            }
        }

        println(count)
    }

    companion object {
        const val FINISH = 'X'
    }
}