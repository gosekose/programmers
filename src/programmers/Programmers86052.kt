class Programmers86052 {
    
    private var m: Int = 0
    private var n: Int = 0
    
    private val DR = arrayOf(0, -1, 0, 1)
    private val DC = arrayOf(1, 0, -1, 0)
    
    private lateinit var visited: Array<Array<BooleanArray>>    
    
    fun solution(grid: Array<String>): IntArray {
        val answer = mutableListOf<Int>()
        
        m = grid.size
        n = grid[0].length

        visited = Array(m) { Array(n) { BooleanArray(4) } }
        
        for (i in 0 until m) {
            for (j in 0 until n) {
                for (d in 0..3) {
                    if (!visited[i][j][d]) {
                        answer.add(move(grid, i, j, d))
                    }
                }
            }
        }
        
        return answer.sorted().toIntArray()
    }
    
    private fun move(grid: Array<String>, row: Int, col: Int, d: Int): Int {    
        var cnt = 0
        
        var nD = d
        var nR = row
        var nC = col
        
        while(true) {
            if (visited[nR][nC][nD]) break
            
            visited[nR][nC][nD] = true
            cnt++
            
            if (grid[nR][nC] == 'L') {
                nD = (nD + 3) % 4
            } else if (grid[nR][nC] == 'R') {
                nD = (nD + 1) % 4
            }
            
            nR = (nR + DR[nD] + m) % m
            nC = (nC + DC[nD] + n) % n
        }
        
        return cnt
    }
}
