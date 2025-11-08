package acmicpc

import java.io.*
import kotlin.math.*

fun main(args: Array<String>) {
    val br = BufferedReader(InputStreamReader(System.`in`))
    val (N, M, D) = br.readLine().split(" ").map { it.toInt() }
    // N row, M col

    val map = Array(N + 1) { IntArray(M) } // 궁수 위치
    for (r in N downTo 1) {
        val str = br.readLine().split(" ").map { it.toInt() }
        for (c in 0 until M) {
            map[r][c] = str[c] // 적 위치 처리
        }
    }

    // map[0][i]는 궁수 위치
    val coms = mutableListOf<MutableList<Int>>()

    // 궁수 위치를 특정할 함수
    fun getCombi(idx: Int, list: MutableList<Int>) {
        if (list.size == 3) {
            val copyList = mutableListOf<Int>()
            for (num in list) {
                copyList.add(num)
            }
            coms.add(copyList)
            return
        }

        for (c in idx until M) {
            list.add(c)
            getCombi(c + 1, list)
            list.removeAt(list.size - 1)
        }
    }

    getCombi(0, mutableListOf<Int>()) // 콤비

    var ans = 0

    // 콤비 목록
    for (com in coms) {
        var tmpAns = 0
        val board = Array(N + 1) { IntArray(M) } // 시뮬레이션 할 맵
//        println("Com: $com")
        for (r in 1 .. N) {
            for (c in 0 until M) {
                board[r][c] = map[r][c]
            }
        }

        // 라운드는 최대 N번
        for (round in 0 until N) {
            // 먼저 궁수를 순회하며 D 거리에 속한 점 중 가장 가까운 점을 찾는다
            var dist = 1000
            var cLeft = 1000
            var node = 0 to 0
            val removeAt = mutableListOf<Pair<Int, Int>>()

            for (bow in com) {
                for (r in 1 .. N) {
                    for (c in 0 until M) {
                        if (board[r][c] == 0) continue
                        // 궁수 위치 0, bow 적 위치 (r, c)
                        val di = abs(c - bow) + r
                        if (di <= D) {
                            if (di < dist || (di == dist && c < cLeft)) {
                                dist = di
                                cLeft = c
                                node = r to c
//                                println("$round round, bow at: $bow, dist: $di, node: $node")
                            }
                        }
                    }
                }

                // 초기화가 되었다면 제거 가능
                if (dist != 1000) {
                    removeAt += node.first to node.second
                }

                dist = 1000
                cLeft = 1000
                node = 0 to 0
            }

            for (re in removeAt) {
                if (board[re.first][re.second] == 1) {
//                    println("Remove at ${re.first}, ${re.second}")
                    board[re.first][re.second] = 0
                    tmpAns++
                }
            }

            // 적 제거했으므로 한칸씩 업데이트
            var oneCnt = 0
            for (r in 2 .. (N - round)) {
                for (c in 0 until M) {
                    board[r - 1][c] = board[r][c]
                    if (board[r - 1][c] == 1) oneCnt++
                }
            }

            for (c in 0 until M) {
                board[N - round][c] = 0
            }

            if (oneCnt == 0) break

        }

        ans = max(ans, tmpAns)
    }

    println(ans)
}

// M은 최대 15
// 15C 3 -> 151413/6 충분

// N씩 한칸씩 내림 -> o(151413/6 * 15 번 내리고 * (15 * 15) 스캔)

