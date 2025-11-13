package acmicpc

import java.io.*
import java.util.*

fun main(args: Array<String>) {
    val br = BufferedReader(InputStreamReader(System.`in`))

    val N = br.readLine().toInt()

    val arr = mutableListOf<Node19700>()
    for (i in 0 until N) {
        val str = StringTokenizer(br.readLine())

        val h = str.nextToken().toInt()
        val k = str.nextToken().toInt()

        arr.add(Node19700(h, k))
    }

    /* 키 순서로 내림 차순 */
    arr.sortByDescending { it.h }

    val sizes = TreeMap<Int, Int>()
    var teams = 0

    fun addSize(x: Int) {
        sizes[x] = (sizes[x] ?: 0) + 1
    }

    fun removeSize(x: Int) {
        val s = sizes[x] ?: return
        if (s == 1) sizes.remove(x) else sizes[x] = s - 1
    }

    /* 내림차순 키 조회 */
    for ((_, k) in arr) {
        val limit = k - 1
        val t = sizes.floorKey(limit)

        if (t == null) {
            teams++
            addSize(1)
        } else {
            removeSize(t)
            addSize(t + 1)
        }
    }

    println(teams)
}

data class Node19700(
    val h: Int,
    val k: Int
)