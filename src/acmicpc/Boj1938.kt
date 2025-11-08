import java.io.*
import kotlin.math.*
import java.util.*

fun main(args: Array<String>) {
    val br = BufferedReader(InputStreamReader(System.`in`))
    val N = br.readLine()!!.toInt()

    // 보드
    val map = Array(N) { CharArray(N) }
    val bs = mutableListOf<Pair<Int, Int>>()
    val es = mutableListOf<Pair<Int, Int>>()

    for (r in 0 until N) {
        val str = br.readLine()
        for (c in 0 until N) {
            val ch = str[c]
            if (ch == 'B') bs += r to c
            else if (ch == 'E') es += r to c

            map[r][c] = ch
        }
    }


    // 회전을 위해 가운데 좌표 구하기
    // 세로 (r-1, c) / (r, c) / (r+1, c)
    // 가로 (r, c-1) / (r, c) / (r, c+1)
    // dir 세로: 1, 가로 0

    val bRs = bs.sortedBy { it.first }
    val bCs = bs.sortedBy { it.second }
    val dir = if(bRs[0].first == bRs[1].first && bRs[1].first == bRs[2].first) 0 else 1 // 가로 체크

    // 시작 노드 -> 시작 위치, 방향, 현재 움직인 거리
    val firstNode = Node1938(bRs[1].first, bCs[1].second, dir, 0)

    // 시작 노드 기준으로 bfs
    val visited = Array(N) { Array(N) { BooleanArray(2) } } // r,c,dir
    val dr = arrayOf(-1, 0, 1, 0)
    val dc = arrayOf(0, -1, 0, 1)

    // 상하좌우 1칸씩 모두 이동, 회전시 3 by 3 장애물 없는 경우 가능
    // 회전시 중앙점을 기준으로 좌우 -> 상하 변경

    fun isMovePossible(r: Int, c: Int): Boolean {
        if (r < 0 || r >= N || c < 0 || c >= N) return false // 범위
        return map[r][c] != '1' // 1이 아니여야 함
    }

    fun isAllMovePossible(r: Int, c: Int, dir: Int): Boolean {
        // 세로라면 r 기준 체크
        if (dir == 1) {
            return isMovePossible(r-1, c) && isMovePossible(r, c) && isMovePossible(r+1, c)
        } else {
            // 가로라면 열 기준 체크
            return isMovePossible(r, c-1) && isMovePossible(r, c) && isMovePossible(r, c+1)
        }
    }

    fun isRotatePossible(r: Int, c: Int): Boolean {
        // 3 by 3이 모두 일단 이동 가능한 영역인지 확인
        for (rP in -1 .. 1) {
            for (cP in -1 .. 1) {
                if (!isMovePossible(r + rP, c + cP)) return false
            }
        }

        return true
    }

    fun isAns(node: Node1938): Boolean {
        val (r, c, dir, cnt) = node
        // 세로라면 r
        if (dir == 1) {
            return map[r-1][c] == 'E' && map[r][c] == 'E' && map[r+1][c] == 'E'
        } else {
            return map[r][c-1] == 'E' && map[r][c] == 'E' && map[r][c+1] == 'E'
        }
    }

    val queue = ArrayDeque<Node1938>()
    queue.addFirst(firstNode) // 첫번째 노드 입력
    visited[firstNode.r][firstNode.c][firstNode.dir] = true

    var ans = 0
    while(queue.isNotEmpty()) {
        val node = queue.poll()
        val (r, c, dir, cnt) = node

        if (isAns(node)) {
            ans = cnt
            break
        }

        // 먼저 상하좌우 이동
        for (k in 0 .. 3) {
            val nr = r + dr[k]
            val nc = c + dc[k]
            if (!isAllMovePossible(nr, nc, dir)) continue
            // 중앙점에 대해서만 방향으로 dir 방문 체크?
            if (visited[nr][nc][dir]) continue
            visited[nr][nc][dir] = true
            queue.add(Node1938(nr, nc, dir, cnt + 1))
        }

        // 방향 전환 처리
        if (isRotatePossible(r, c)) {
            val nDir = if (dir == 1) 0 else 1
            if (visited[r][c][nDir]) continue
            // 전환한 방향이 방문 가능하다면
            visited[r][c][nDir] = true
            queue.add(Node1938(r, c, nDir, cnt + 1))
        }
    }

    println(ans)
}

data class Node1938(
    val r: Int,
    val c: Int,
    val dir: Int,
    val cnt: Int
)