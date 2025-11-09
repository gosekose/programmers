import java.util.*

class Programmers81302 {
    
    private val DR = arrayOf(0, 1, 1,  1,  0, -1, -1, -1) // DR * DC == 0 상하좌우
    private val DC = arrayOf(1, 1, 0, -1, -1, -1,  0,  1)
    
    private val DR2 = arrayOf(0, 2, 0, -2)
    private val DC2 = arrayOf(2, 0, -2, 0)
    
    private val visited = Array(5) { Array(5) { BooleanArray(5) } }
    private val map = Array(5) { Array(5) { CharArray(5) } }
    private val queue: Queue<Loc> = LinkedList() 
    private val ps = mutableListOf<MutableList<Loc>>()
    private val answer = IntArray(5)
    
    fun solution(places: Array<Array<String>>): IntArray {       
        
        places.forEachIndexed { idx, place ->
            val pList = mutableListOf<Loc>()
            place.forEachIndexed { i, p ->
                p.forEachIndexed { j, c -> 
                    map[idx][i][j] = c
                    if (c == 'P') pList.add(Loc(i, j, idx)) // row, col, depth
                }
            }
            ps.add(pList)
        }
        
        for (i in 0..ps.lastIndex) {
            for (j in ps[i]) {
                queue.add(j)
            }
            bfs(i)
        }
        
        return answer
    }
    
    private fun bfs(bfsDepth: Int) {
        var temp = 1
        
        while(queue.isNotEmpty()) {
            val now = queue.poll() // 현재 있는거 빼기
            val d = now.depth
            
            for (k in 0 .. DR.lastIndex) { 
                val nR = now.row + DR[k]
                val nC = now.col + DC[k]
                
                if (isValid(nR, nC)) {
            
                    if (DR[k] * DC[k] == 0 && map[d][nR][nC] == 'P') { // 상하좌우에 존재
                        temp = 0 // 조기리턴
                        break
                    } else if (map[d][nR][nC] == 'P') { // 대각선에 존재한다면 양쪽 확인
                        val dP = (k + 1) % 8
                        val dM = (k - 1 + 8) % 8
                        
                        
                        
                        if (!(map[d][now.row + DR[dP]][now.col + DC[dP]] == 'X' 
                              && map[d][now.row + DR[dM]][now.col + DC[dM]] == 'X')) {
                            // 양쪽다 막혀있지 않으면 불가
                            temp = 0
                            break
                        }
                    }
                    
                    if (isValidQueueRange(nR, nC, d)) {
                        val next = Loc(nR, nC, d)
                        queue.add(next)
                        visited[d][nR][nC] = true
                    }
                }
            }
            
            for (k in 0..3) {
                val nR2 = now.row + DR2[k]
                val nC2 = now.col + DC2[k]
                
                if (isValid(nR2, nC2)) { // 직선 맨해튼 거리 == 2
                    if (map[d][nR2][nC2] == 'P') { // y축 이동
                        if (k == 0) {
                            if (map[d][nR2][nC2 - 1] != 'X') {
                                temp = 0
                                break
                            }
                        } else if (k == 1) {
                            if (map[d][nR2 -1][nC2] != 'X') {
                                temp = 0
                                break
                            }
                        } else if (k == 2) {
                            if (map[d][nR2][nC2 + 1] != 'X') {
                                temp = 0
                                break
                            }
                        } else if (k == 3) {
                            if (map[d][nR2 + 1][nC2] != 'X') {
                                temp = 0
                                break
                            }
                        }
                    }
                    
                    
                    if (isValidQueueRange(nR2, nC2, d)) {
                        val next = Loc(nR2, nC2, d)
                        queue.add(next)
                        visited[d][nR2][nC2] = true
                    }
                }
            }
        }
        
        queue.clear()
        answer[bfsDepth] = temp
    }
    
    private fun isValid(nR: Int, nC: Int): Boolean {
        return nR >= 0 && nR < 5 && nC >= 0 && nC < 5 
    }
    
    private fun isValidQueueRange(nR: Int, nC: Int, depth: Int): Boolean {
        return !visited[depth][nR][nC] && map[depth][nR][nC] == 'P'
    }
    
    data class Loc(
        val row: Int,
        val col: Int,
        val depth: Int,
    )
}
