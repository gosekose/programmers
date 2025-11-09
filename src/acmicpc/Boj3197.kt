package acmicpc

import java.util.*
import java.io.*

fun main(args: Array<String>) {
    val test = Test3197()
    test.solve()
}

class Test3197 {
    val dr = arrayOf(-1, 0, 1, 0)
    val dc = arrayOf(0, -1, 0, 1)
    var swan = mutableListOf<Node3197>()
    var water = mutableListOf<Node3197>()
    var map: Array<CharArray> = arrayOf()

    fun solve() {
        val br = BufferedReader(InputStreamReader(System.`in`))
        val (R, C) = br.readLine().split(" ").map { it.toInt() }

        map = Array(R) { CharArray(C) }

        for (r in 0 until R) {
            val str = br.readLine()
            for (c in 0 until C) {
                map[r][c] = str[c]

                if (str[c] == 'L') {
                    map[r][c] = '.' // 움직일 수 있는 공간으로 처리
                    swan.add(Node3197(r, c))
                    water.add(Node3197(r, c))
                } else if (str[c] == '.') {
                    water.add(Node3197(r, c))
                }
            }
        }

        val depth = simulation(R, C)
        println(depth)
    }

    /* 백조 이동 후, 물 이동 */
    fun simulation(R: Int, C: Int): Int {
        var depth = 0

        val (r, c) = swan[0] // 백조 시작 위치
        val (tr, tc) = swan[1] // 타겟

        val swanVisited = Array(R) { BooleanArray(C) }
        val waterVisited = Array(R) { BooleanArray(C) }

        val swanQ = ArrayDeque<Node3197>()   /* 현재 뎁스에 이동할 수 있는 백조 위치 */
        val swanNext = ArrayDeque<Node3197>() /* 물이 녹으면 이동 가능한 백조 위치 */

        val waterQ = ArrayDeque<Node3197>()
        val waterNext = ArrayDeque<Node3197>() /* 다음 물이 녹을 수 있는 위치 */

        swanQ.add(Node3197(r, c))
        swanVisited[r][c] = true

        for (w in water) {
            waterQ.add(w)
            waterVisited[w.row][w.col] = true
        }

        while(true) {
            /* 백조 위치 이동 시키기 */
            while(swanQ.isNotEmpty()) {
                val node = swanQ.poll()

                /* 백조 위치가 도착점이면 */
                if (node.row == tr && node.col == tc) {
                    return depth
                }

                for (k in 0 .. 3) {
                    val nr = node.row + dr[k]
                    val nc = node.col + dc[k]

                    if (nr < 0 || nr >= R || nc < 0 || nc >= C || swanVisited[nr][nc]) continue

                    swanVisited[nr][nc] = true

                    /* 현재 방문은 못하지만 다음번에 가능함 */
                    if (map[nr][nc] == 'X') {
                        swanNext.add(Node3197(nr, nc))
                    } else if (map[nr][nc] == '.') {
                        swanQ.add(Node3197(nr, nc))
                    }
                }
            }

            /* swan이 다음에 움직여야 하는 장소 업데이트 */
            while(swanNext.isNotEmpty()) {
                val node = swanNext.poll()
                swanQ.add(node)
            }

            /* 물 이동 시키기 */
            while (waterQ.isNotEmpty()) {
                val node = waterQ.poll()

                for (k in 0 .. 3) {
                    val nr = node.row + dr[k]
                    val nc = node.col + dc[k]

                    if (nr < 0 || nr >= R || nc < 0 || nc >= C || waterVisited[nr][nc]) continue

                    waterVisited[nr][nc] = true

                    if (map[nr][nc] == 'X') {
                        waterNext.add(Node3197(nr, nc))
                    } else if (map[nr][nc] == '.') {
                        waterQ.add(Node3197(nr, nc))
                    }
                }
            }

            /* 녹일 수 있는 얼음 녹이기 */
            while(waterNext.isNotEmpty()) {
                val node = waterNext.poll()
                map[node.row][node.col] = '.'
                waterQ.add(node)
            }

            depth++
        }
    }


    data class Node3197(
        val row: Int,
        val col: Int,
    )
}