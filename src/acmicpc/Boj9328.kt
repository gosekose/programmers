package acmicpc

import java.util.*
import java.io.*
data class Node9328(
    val row: Int,
    val col: Int,
)

fun main(args: Array<String>) {
    val br = BufferedReader(InputStreamReader(System.`in`))

    val T = br.readLine().toInt()
    val dr = arrayOf(-1, 0, 1, 0)
    val dc = arrayOf(0, -1, 0, 1)
    val sb = StringBuilder()

    repeat(T) {
        val st = StringTokenizer(br.readLine())

        val h = st.nextToken().toInt()
        val w = st.nextToken().toInt()

        val board = Array(h + 2) { CharArray(w + 2) }

        for (r in 1 .. h) {
            val str = br.readLine()
            for (c in 0 until w) {
                val idx = c+1
                board[r][idx] = str[c]
            }
        }

        for (r in 0 until h + 2) {
            board[r][0] = '.'
            board[r][w + 1] = '.'
        }

        for (c in 0 until w + 2) {
            board[0][c] = '.'
            board[h + 1][c] = '.'
        }

        val keysStr = br.readLine()
        val keys = mutableSetOf<Char>()

        for (key in keysStr) {
            keys.add(key)
        }

        val doors = Array(26) { mutableListOf<Node9328>() }
        val visited = Array(h + 2) { BooleanArray(w + 2) }
        visited[0][0] = true

        val queue = ArrayDeque<Node9328>()
        queue.add(Node9328(0, 0))
        var ans = 0

        while(queue.isNotEmpty()) {
            val node = queue.poll()
            val (row, col) = node

            for (k in 0 until 4) {
                val nr = row + dr[k]
                val nc = col + dc[k]

                if (nr < 0 || nr > h + 1 || nc < 0 || nc > w + 1 || board[nr][nc] == '*' || visited[nr][nc]) continue

                /* .이면 이동 가능 */
                if (board[nr][nc] == '.') {
                    visited[nr][nc] = true
                    queue.add(Node9328(nr, nc))
                    continue
                }

                if (board[nr][nc] == '$') {
                    ans++
                    visited[nr][nc] = true
                    queue.add(Node9328(nr, nc))
                    continue
                }

                val ch = board[nr][nc]

                if (ch in 'a' .. 'z') {
                    visited[nr][nc] = true
                    keys.add(ch)
                    queue.add(Node9328(nr, nc))

                    /* 방문 가능하도록 개방 */
                    val idx = ch - 'a'
                    if (doors[idx].isNotEmpty()) {
                        val list = doors[idx]

                        for (node in list) {
                            queue.add(Node9328(node.row, node.col))
                        }
                    }
                } else {
                    val lch = ch.lowercaseChar()

                    /* 이미 키가 있는 경우 이동 가능 */
                    if (keys.contains(lch)) {
                        visited[nr][nc] = true
                        queue.add(Node9328(nr, nc))
                    }
                    /* 키가 없는 경우 다음 이동 가능 후보로 세팅 */
                    else {
                        val idx = lch - 'a'
                        doors[idx].add(Node9328(nr, nc))
                    }
                }
            }
        }

        sb.append("$ans").append("\n")
    }

    val bw = BufferedWriter(OutputStreamWriter(System.out))
    bw.write(sb.toString())
    bw.flush()
}