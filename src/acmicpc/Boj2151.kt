package acmicpc

import java.util.*
import java.io.*
import kotlin.math.*

fun main(args: Array<String>) {
    val test = Test2151()
    test.solve()
}

class Test2151 {
    var N: Int = 0
    var map: Array<CharArray> = arrayOf()       // 집을 담을 배열
    var dp:  Array<Array<IntArray>> = arrayOf() // 각 방향에 따른 최소 개수 배열
    var start: Pair<Int, Int> = 0 to 0
    var end: Pair<Int, Int> = 0 to 0
    val doors = mutableListOf<Pair<Int, Int>>() // 문 위치 저장

    /* dir */
    /* 0: row+, 1: row-, 2: col+, 3: col- */

    fun solve() {
        val br = BufferedReader(InputStreamReader(System.`in`))

        N = br.readLine().toInt()
        map = Array(N) { CharArray(N) }
        dp = Array(N) { Array(N) { IntArray(4) } }

        for (r in 0 until N) {
            val str = br.readLine()
            for (c in 0 until N) {
                map[r][c] = str[c]

                if (map[r][c] == '#') {
                    doors.add(r to c) // 도어 추가
                }
            }
        }

        start = doors[0] // 시작 문 위치
        end   = doors[1] // 도착 문 위치

        /* 거울 사용 개수가 적은 것부터 하나씩 이동하며 갱신 */
        val pq = PriorityQueue<Node2151>() { a, b -> a.cnt - b.cnt }

        /* 상하좌우 모든 방향으로 첫 큐에 입력*/
        pq.add(Node2151(start.first, start.second, 0, 0))
        pq.add(Node2151(start.first, start.second, 1, 0))
        pq.add(Node2151(start.first, start.second, 2, 0))
        pq.add(Node2151(start.first, start.second, 3, 0))

        /* 각 위치에 대한 거리 dp 갱신 */
        for (r in 0 until N) {
            for (c in 0 until N) {
                for (k in 0 until 4) {
                    if (r == start.first && c == start.second) {
                        dp[r][c][k] = 0
                    }
                    else dp[r][c][k] = Integer.MAX_VALUE
                }
            }
        }

        var ans = Integer.MAX_VALUE

        while(pq.isNotEmpty()) {
            val node = pq.poll()

            val (row, col, dir, cnt) = node

            /* 만약 최단 개수의 거울로 문에 도달하는 경우 */
            if(row == end.first && col == end.second) {
                ans = cnt
                break
            }

            /* 다음 방향에 따라 움직이게 되는 다음 위치 */
            val nr = if (dir == 0) row + 1 else if (dir == 1) row - 1 else row
            val nc = if (dir == 2) col + 1 else if (dir == 3) col - 1 else col

            /* 만약 이동이 불가능하다면 해당 방향은 더 이상 탐색 X */
            if (nr < 0 || nr >= N || nc < 0 || nc >= N || map[nr][nc] == '*') continue // 이동 불가

            /* 방향 바꾸지 않고 직진이 가능하다면, 거울 사용 개수가 현재 방향까지 도달했을 떄 거울 개수보다 작을 때 */
            if (cnt < dp[nr][nc][dir]) {
                dp[nr][nc][dir] = cnt
                pq.add(Node2151(nr, nc, dir, cnt))
            }

            /* 방향 바꾸는 것이 가능하다면 회전 처리*/
            if (map[nr][nc] == '!') {
                rotate(Node2151(nr, nc, dir, cnt), pq)
            }
        }

        println(ans)
    }

    // 도착한 위치에 dir을 바꿔서 처리하는 경우
    fun rotate(node: Node2151, pq: PriorityQueue<Node2151>) {
        val (row, col, dir, cnt) = node

        if (dir == 0 || dir == 1) { // 위/아래 → 좌/우
            if (cnt + 1 < dp[row][col][2]) { // 오른쪽
                dp[row][col][2] = cnt + 1
                pq.add(Node2151(row, col, 2, cnt + 1))
            }
            if (cnt + 1 < dp[row][col][3]) { // 왼쪽
                dp[row][col][3] = cnt + 1
                pq.add(Node2151(row, col, 3, cnt + 1))
            }
        } else { // 좌/우 → 위/아래
            if (cnt + 1 < dp[row][col][0]) { // 아래(row+)
                dp[row][col][0] = cnt + 1
                pq.add(Node2151(row, col, 0, cnt + 1))
            }
            if (cnt + 1 < dp[row][col][1]) { // 위(row-)
                dp[row][col][1] = cnt + 1
                pq.add(Node2151(row, col, 1, cnt + 1))
            }
        }
    }

    data class Node2151(
        val row: Int,
        val col: Int,
        val dir: Int, // 방향
        val cnt: Int, // 현재 거울 사용 개수
    )
}