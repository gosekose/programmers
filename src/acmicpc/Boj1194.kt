package acmicpc

import java.util.*
import java.io.*

fun main(args: Array<String>) {
    val br = BufferedReader(InputStreamReader(System.`in`))

    val (N, M) = br.readLine().split(" ").map { it.toInt() }
    val map = Array(N) { CharArray(M) }

    val queue = ArrayDeque<Node1194>()
    /* 알파벳 a, b, c, d, e, f 비트마스크 */
    val visitied = Array((1 shl 7)) { Array(N) { BooleanArray(M) } }

    for (r in 0 until N) {
        val str = br.readLine()
        for (c in 0 until M) {
            map[r][c] = str[c]

            if (str[c] == '0') {
                queue.add(Node1194(r, c, 0, 0))
                visitied[0][r][c] = true
            }
        }
    }

    val dr = arrayOf(-1, 0, 1, 0)
    val dc = arrayOf(0, -1, 0, 1)

    var ans = -1
    while(queue.isNotEmpty()) {
        val node = queue.poll()

        if (map[node.row][node.col] == '1') {
            ans = node.depth
            break
        }

        for (k in 0 .. 3) {
            val nr = node.row + dr[k]
            val nc = node.col + dc[k]
            val ndepth = node.depth + 1
            var nmask = node.mask

            /* 방문 불가 구간 */
            if (nr < 0 || nr >= N || nc < 0 || nc >= M || map[nr][nc] == '#') continue
            val alpha = map[nr][nc]

            if (map[nr][nc] in 'A' .. 'Z') {
                /* 열쇠 & 현재 마스크가 0이 아니여야 이동 가능 */
                val alphaKey  = node.mask and (1 shl ((alpha.lowercaseChar() - 'a') + 1))
                if (alphaKey == 0) continue
            }

            if (map[nr][nc] in 'a' .. 'z') {
                /* 열쇠 추가 */
                nmask = node.mask or (1 shl (alpha - 'a') + 1)
            }

            if (visitied[nmask][nr][nc]) continue
            visitied[nmask][nr][nc] = true
            queue.add(Node1194(nr, nc, nmask, ndepth))
        }
    }

    val bw = BufferedWriter(OutputStreamWriter(System.out))
    bw.write("$ans")
    bw.flush()
}

data class Node1194(
    val row: Int,
    val col: Int,
    val mask: Int,
    val depth: Int,
)