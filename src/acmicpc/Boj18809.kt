package acmicpc

import java.util.*
import java.io.*
import java.lang.*
import kotlin.math.*


fun main(args: Array<String>) {
    val br = BufferedReader(InputStreamReader(System.`in`))
    val (N, M, G, R) = br.readLine().split(" ").map { it.toInt() }
    val board = Array(N) { CharArray(M) }

    for (r in 0 until N) {
        val str = StringTokenizer(br.readLine())
        var c = 0
        while(str.hasMoreTokens()) {
            board[r][c] = str.nextToken()[0]
            c++
        }
    }

    // 0인 경우 탐색해서 가능한 목록에 넣기
    val area = mutableListOf<Pair<Int, Int>>()

    for (r in 0 until N) {
        for (c in 0 until M) {
            if (board[r][c] == '2') area += r to c
        }
    }

    val gLists = mutableListOf<MutableList<Pair<Int, Int>>>()
    combi(0, G, area, mutableListOf(), board, gLists)

    val dr = arrayOf(-1, 0, 1, 0)
    val dc = arrayOf(0, -1, 0, 1)

    var ans = 0

    for (gList in gLists) {
        for (g in gList) {
            board[g.first][g.second] = '1'
        }

        val rLists = mutableListOf<MutableList<Pair<Int, Int>>>()
        combi(0, R, area, mutableListOf<Pair<Int, Int>>(), board, rLists)

        for (rList in rLists) {
            for (r in rList) {
                board[r.first][r.second] = '1'
            }
            // gList, rList를 구하고, board 세팅 완료

            // g, r 하나씩 큐에 넣으며 같은 스탭에 도달했을 때, g, r이 있으면 꽃 가능

            val queue = PriorityQueue<Node18809>(){a, b -> a.step - b.step }
            val grid = Array(N) { Array(M) { IntArray(2) } }

            for (g in gList) {
                grid[g.first][g.second][0] = 1
                queue.add(Node18809(g.first, g.second, 1, 0))
            }

            for (r in rList) {
                grid[r.first][r.second][1] = 1
                queue.add(Node18809(r.first, r.second, 1, 1))
            }

            var tmpAns = 0

            while(queue.isNotEmpty()) {
                val node = queue.poll()
                val color = node.color
                val step = node.step

                if (grid[node.r][node.c][color] == -1) continue; // 꽃으로 변한 경우

                for (k in 0 .. 3) {
                    val nr = node.r + dr[k]
                    val nc = node.c + dc[k]
                    val nstep = step + 1

                    if (nr < 0 || nr >= N || nc < 0 || nc >= M || board[nr][nc] == '0') continue

                    // 아직 방문하지 않은 경우
                    if (grid[nr][nc][color] == 0 && grid[nr][nc][abs(color-1)] == 0) {
                        grid[nr][nc][color] = nstep // 방문 처리
                        queue.add(Node18809(nr, nc, nstep, color))
                    }
                    // 이미 방문했을 때, 다음 스탭과 동일한 경우
                    else if (grid[nr][nc][abs(color-1)] != 0 && grid[nr][nc][abs(color-1)] == nstep) {
                        grid[nr][nc][color] = -1 // 방문 처리
                        grid[nr][nc][abs(color-1)] = -1

                        // 꽃 처리
                        tmpAns++
                    }
                }
            }

            ans = max(tmpAns, ans)

            reset(rList, board) // 원복
        }

        reset(gList, board) // 원복
    }

    println(ans)
}

data class Node18809(
    val r: Int,
    val c: Int,
    val step: Int,
    val color: Int // G:0, r:1
)

fun combi(idx: Int, mx: Int, area: MutableList<Pair<Int, Int>>, list: MutableList<Pair<Int, Int>>, board: Array<CharArray>, colors: MutableList<MutableList<Pair<Int, Int>>>) {
    if (list.size == mx) {
        val tmp = mutableListOf<Pair<Int, Int>>()

        for (a in list) {
            tmp += a.first to a.second
        }
        colors += tmp
//        println("tmp: $tmp, mx = $mx")
        return;
    }

    for(i in idx until area.size) {
        val r = area[i].first
        val c = area[i].second

        if (board[r][c] == '2') {
            list.add(r to c)
            board[r][c] = '1'

            combi(i + 1, mx, area, list, board, colors)

            board[r][c] = '2'
            list.removeAt(list.size - 1)
        }
    }
}

fun reset(list: MutableList<Pair<Int, Int>>, board: Array<CharArray>) {
    for (li in list) {
        board[li.first][li.second] = '2'
    }
}