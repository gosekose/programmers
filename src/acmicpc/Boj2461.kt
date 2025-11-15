package acmicpc

import java.io.*
import java.util.*
import kotlin.math.*

fun main(args: Array<String>) {
    val br = BufferedReader(InputStreamReader(System.`in`))
    val (N, M) = br.readLine().split(" ").map { it.toInt() }

    val lists = mutableListOf<Node2461>()
    for (c in 0 until N) {
        val str = StringTokenizer(br.readLine())
        for (i in 0 until M) {
            val s = str.nextToken().toInt()
            lists.add(Node2461(s, c))
        }
    }

    /* 정렬 */
    lists.sort()

    if (N == 1) {
        println(0)
        return
    }

    val map = IntArray(N)
    var totalClasses = 0
    var minDiff = Int.MAX_VALUE
    var left = 0

    /* 리스트에 투포인트 처리 */
    for (right in lists.indices) {
        val (s, c) = lists[right]
        map[c]++
        if (map[c] == 1) totalClasses++

        /* N개의 클래스라면 */
        while(totalClasses == N) {
            val diff = lists[right].s - lists[left].s
            minDiff = min(minDiff, diff)

            val (s, c) = lists[left]
            map[c]--
            if(map[c] == 0) totalClasses--
            left++
        }
    }

    println(minDiff)
}

data class Node2461(
    val s: Int, // 능력
    val c: Int, // 학급
): Comparable<Node2461> {
    override fun compareTo(other: Node2461): Int {
        return if (this.s == other.s) {
            return this.c - other.c
        } else this.s - other.s
    }
}