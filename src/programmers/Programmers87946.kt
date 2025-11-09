import kotlin.math.*

class Programmers87946 {

    private var answer: Int = 0
    private lateinit var d: Array<IntArray>

    fun solution(k: Int, dungeons: Array<IntArray>): Int {
        d = dungeons

        for (i in 0..d.lastIndex) { // 먼저 고를 인덱스 선택
            var varK = k // 최초 피로도 리셋
            val visited = BooleanArray(d.size) // 방문여부 설정

            if (d[i][0] <= k && d[i][1] <= k)  { // 필수 피로도, 최소 피로도 만족
                val diff = varK - d[i][1]
                visited[i] = true
                dfs(diff, visited, 1) // dfs : 남은 피로도, 방문 여부, 깊이
            }
        }

        return answer
    }

    private fun dfs(varK: Int, visited: BooleanArray, depth: Int) {
        for (i in 0..d.lastIndex) {
            // 방문하지 않은 경우 && 피로도 만족
            if (!visited[i] && d[i][0] <= varK && d[i][1] <= varK) {

                val diff = varK - d[i][1]
                visited[i] = true
                dfs(diff, visited, depth + 1)

                visited[i] = false // 백트래킹
            }
        }
        answer = max(answer, depth)
    }


}
// 피로도 최소 필요, 소모 피로도
// 하루에 한 번 탐험 max
// dfs 백트래킹

// 1. for문을 돌면서 어떤 것을 선택할지 고르기
// 2. 내부 포문을 돌면서 선택 안한 것 선택 피로도가 남아있다면 dfs
// 3. 내부 포문에서 백트래킹
