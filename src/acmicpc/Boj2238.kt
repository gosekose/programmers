package acmicpc

import java.io.*
import java.util.*

fun main(args: Array<String>) {
    val br = BufferedReader(InputStreamReader(System.`in`))
    val pq = PriorityQueue<Node2238>(){a, b -> if(a.cnt == b.cnt) a.num - b.num else a.cnt - b.cnt}
    val (U, N) = br.readLine().split(" ").map { it.toInt() }

    val map = mutableMapOf<Int, Int>()
    val names = mutableMapOf<Int, MutableList<String>>()

    repeat(N) {
        val strs = br.readLine().split(" ")
        val name = strs[0]
        val num = strs[1].toInt()

        if (num < 1 || num > 10000) return@repeat

        if (names.containsKey(num)) {
            names[num]!!.add(name)
        } else {
            names[num] = mutableListOf<String>()
            names[num]!!.add(name)
        }

        if (map.containsKey(num)) {
            map[num] = map[num]!! + 1
        } else map[num] = 1

    }

    for ((key, value) in map) {
        pq.add(Node2238(key, value))
    }

    val node = pq.poll()

    val sb = StringBuilder()
    sb.append(names[node.num]!![0]).append(" ").append(node.num)

    println(sb.toString())

}

data class Node2238(
    val num: Int,
    val cnt: Int,
)