import kotlin.math.*

class Programmers131130 {

    private var answer: Int = 0
    private var m: Int = 0

    private lateinit var cards: IntArray

    fun solution(inputs: IntArray): Int {

        cards = inputs
        m = cards.size

        for (i in 0 until m) {
            val visited = BooleanArray(m)
            dfs(i, visited, 1, 0, Group.ONE)
        }

        return answer
    }

    private fun dfs(start: Int, visited: BooleanArray, one: Int, two: Int, group: Group) {
        when(group) {
            Group.ONE -> {
                visited[start] = true // 카드 방문 처리
                val next = cards[start] - 1 // 다음에 가야할 카드의 인덱스

                if (visited[next]) { // 다음 카드가 방문했다면,
                    for (i in 0 until m) {
                        if (!visited[i]) { // 방문하지 않은 카드
                            dfs(i, visited, one, two + 1, Group.TWO) // 두번째 그룹 탐색
                        }
                    }
                }

                else {
                    visited[next] = true
                    dfs(next, visited, one+1, two, Group.ONE)
                }

                visited[next] = false // 백트래킹
            }
            Group.TWO -> {
                visited[start] = true
                val next = cards[start] - 1 // 다음에 가야할 카드의 인덱스

                if (visited[next]) { // 다음 카드 방문했을 때,
                    answer = max(one * two, answer) // answer 재할당
                }

                else {
                    dfs(next, visited, one, two+1, Group.TWO)
                }

                visited[next] = false // 백트래킹
            }
        }
    }

    enum class Group{
        ONE, TWO;
    }
}

// 완전탐색 / 백트래킹
// dfs --> 임의의 상자를 어떤 것을 고를 것인가?

// 1. for (0 ~ 끝)
// 2. visited 초기화
// 3. dfs 함수 호출 백트래킹
// 4. 방문되지 않은 false 리스트에서 다시 dfs를 수행 , 백트래킹
// 5. max값 업데이트