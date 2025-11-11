package acmicpc

import java.util.*
import java.io.*
import kotlin.math.*

fun main(args: Array<String>) {
    val br = BufferedReader(InputStreamReader(System.`in`))
    val (N, K) = br.readLine().split(" ").map { it.toInt() }

    val INF = 100_000_000
    val dist = Array(N) { IntArray(N) }

    for (r in 0 until N) {
        val str = StringTokenizer(br.readLine())
        var c = 0
        while(c < N) {
            val num = str.nextToken().toInt()
            dist[r][c] = num
            c++
        }
    }

    /* 플로이드 워셜로 경유로 방문하는 경우에 대한 최단 거리 구하기 */
    for (k in 0 until N) {
        for (r in 0 until N) {
            for (c in 0 until N) {

                /* 경유지 방문 */
                val d = dist[r][k] + dist[k][c]
                if (d < dist[r][c]) dist[r][c] = d
            }
        }
    }

    /* mask가 된 상태(비트부호가 1인 경우는 방문 / 0은 미방문)에서 현재 있는 위치 */
    val dp = Array(1 shl N) { IntArray(N) { INF }}
    val n = (1 shl N) - 1 // 0 ~ 모든 방문 가능 비트

    dp[1 shl K][K] = 0 // K에서 K를 방문하는 경우는 0

    /* 방문 영역 비트마스크 */
    for (mask in 0 .. n) {
        /* j는 방문 영역 위치 */
        for (j in 0 until N) {
            val cur = dp[mask][j]

            /* 마스킹된 영역이 아닌 경우 */
            if (cur == INF) continue

            /* 방문하지 않은 곳 이동 시작 */
            for (v in 0 until N) {
                /* 이미 v 노드를 방문함 */
                if ((mask and (1 shl v)) != 0) continue

                /* 다음 방문 마스킹 */
                val vmask = mask or (1 shl v)
                val cand = cur + dist[j][v] /* j위치에서 v로 이동할 때의 값 */

                /* 다음 위치는 v */
                if (cand < dp[vmask][v]) dp[vmask][v] = cand
            }
        }
    }

    var ans = INF
    /* 모두 방문을 마쳤을 때, 가장 마지막에 위치하게 되는 곳이 가장 작은 값 */
    for (u in 0 until N) ans = min(ans, dp[n][u])

    val bw = BufferedWriter(OutputStreamWriter(System.out))
    bw.write("$ans")
    bw.flush()
}