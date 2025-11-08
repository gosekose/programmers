package acmicpc

import java.util.*
import java.io.*
import kotlin.math.*

fun main(args: Array<String>) {
    val br = BufferedReader(InputStreamReader(System.`in`))
    val st = StringTokenizer(br.readLine())
    val n = st.nextToken().toInt()
    val m = st.nextToken().toInt()

    val board = Array(n) {
        val tok = StringTokenizer(br.readLine())
        IntArray(m) {
            tok.nextToken().toInt()
        }
    }

    fun clean() {
        var last = 0
        for (i in 0 until n) {
            var end = last
            for (j in last until m) {
                if (board[i][j] == 1) {
                    end = j
                }
            }

            if (end >= last) {
                for (j in last ..end) {
                    board[i][j] = 0
                }
            }

            last = end
        }
    }

    var result = 0

    while(board.any { row -> row.any { it == 1}}) {
        clean()
        result++
    }

    println(result)
}