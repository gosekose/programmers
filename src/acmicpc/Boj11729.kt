package acmicpc

import kotlin.math.*
import java.io.*

fun main(args: Array<String>) {
    val br = BufferedReader(InputStreamReader(System.`in`))
    val n = br.readLine()!!.toInt()

    val sb = StringBuilder()
    var moves = 0L

    fun hanoi(k: Int, from: Int, via: Int, to: Int) {
        if (k == 0) return
        hanoi(k - 1, from, to, via)
        sb.append(from).append(' ').append(to).append('\n')
        moves++
        hanoi(k - 1, via, from, to)
    }

    hanoi(n, 1, 2, 3)

    println(moves)
    println(sb.toString())
}