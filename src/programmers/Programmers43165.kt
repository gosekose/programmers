class Programmers43165 {
    
    private val DX = arrayOf(-1, 1)
    private var answer = 0
    
    fun solution(numbers: IntArray, target: Int): Int {
        dfs(0, 0, numbers, target)
        
        return answer
    }
    
    // 현재 인덱스, 합계
    private fun dfs(n: Int, sum: Int, numbers: IntArray, target: Int) {
        if (n == numbers.size) {
            if (sum == target) answer++
            return
        }
        
        var k = sum
        for (j in 0 .. 1) {
            val dx = numbers[n] * DX[j] // 합계 추가
            dfs(n+1, k+dx, numbers, target)
        }
    }
}

// 현재의 sum, dfs 선택해야할 다음 인덱스, 선택해야할 다음 부호
// 깊이가 numbers.lastIndex까지 도달하면 타겟과 비교 후 ++ or pass
