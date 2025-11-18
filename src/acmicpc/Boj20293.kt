package acmicpc

import java.util.*
import java.io.*
import kotlin.math.*

class Test20293 {
    var R: Int = 0
    var C: Int = 0
    val nodes = mutableListOf<Node20293>()

    fun solve() {
        val br = BufferedReader(InputStreamReader(System.`in`))
        var str = StringTokenizer(br.readLine())
        R = str.nextToken().toInt()
        C = str.nextToken().toInt()

        val N = br.readLine().toInt()

        nodes.add(Node20293(1, 1, 0))
        nodes.add(Node20293(R, C, 0))

        repeat(N) {
            str = StringTokenizer(br.readLine())
            val row = str.nextToken().toInt()
            val col = str.nextToken().toInt()
            val fue = str.nextToken().toInt()

            nodes.add(Node20293(row, col, fue))
        }

        /* DAG 구성 정렬 */
        nodes.sort()

        var low = 1 /* 최소 연료량은 1개 */
        var high = (R - 1) + (C - 1) /* 최대 연료량은 이동 거리 */
        var ans = high
        while (low <= high) {
            val mid = (low + high) / 2

            if (canDp(mid)) {
                ans = min(ans, mid)
                high = mid - 1
            } else {
                low = mid + 1
            }
        }

        val sb = StringBuilder()
        sb.append("$ans")

        val bw = BufferedWriter(OutputStreamWriter(System.out))
        bw.write(sb.toString())
        bw.flush()
    }

    fun canDp(mid: Int): Boolean {
        /* dp[i]는 i까지 이동하고 남은 최대 연료량 */
        val t = nodes.size
        val dp = IntArray(t) { -1 }
        dp[0] = mid

        /* dp 순환 */
        for (i in 0 until t) {
            val current = nodes[i]

            for (j in i+1 until t) {
                val next = nodes[j]

                /* 만약 dag가 아니라면 불가 */
                if(next.r < current.r || next.c < current.c) continue

                val dist = (next.r - current.r) + (next.c - current.c)

                /* 이동 가능한 연료보다 dist가 더 크면 불가 */
                if (dp[i] < dist) continue

                dp[j] = max((dp[i] - dist + next.f), dp[j])
            }
        }

        return dp[t-1] >= 0
    }
}

data class Node20293(
    val r: Int,
    val c: Int,
    val f: Int,
) : Comparable<Node20293> {
    override fun compareTo(other: Node20293): Int {
        return if (this.r == other.r) this.c - other.c else this.r - other.r
    }
}

fun main(args: Array<String>) {
    val test = Test20293()
    test.solve()
}