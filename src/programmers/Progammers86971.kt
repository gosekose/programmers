import kotlin.math.*

class Progammers86971 {
    
    private var answer: Int = Int.MAX_VALUE
    private val graph: MutableList<MutableList<Int>> = mutableListOf()
    
    fun solution(n: Int, wires: Array<IntArray>): Int {
        for (i in 0..n) { // 그래프에 그래프 리스트 추가
            graph.add(mutableListOf())
        }
        
        for (wire in wires) { // 간선 설정
            val v1 = wire[0]
            val v2 = wire[1]
            
            graph[v1].add(v2) // 양방향 간선
            graph[v2].add(v1)
        }
        
        for (wire in wires) { // 그래프에서 간선을 하나씩 끊기
            val v1 = wire[0]
            val v2 = wire[1]
            
            graph[v1].remove(v2) // 양방향 간선 제거
            graph[v2].remove(v1)
            
            val visited = BooleanArray(n + 1) // 방문 배열
            val cnt = dfs(1, visited) // 그룹 1개를 구하면 나머지 그룹은 n - cnt
            
            val abs = abs(cnt - (n - cnt))
            answer = min(abs, answer) // 최소값 갱신
            
            graph[v1].add(v2) // 양방향 간선 추가
            graph[v2].add(v1)
        }
        
        return answer
    }
    
    private fun dfs(node: Int, visited: BooleanArray): Int {
        visited[node] = true // 방문 처리
        
        val adjacents = graph[node] // 인접한 노드 목록
        
        var cnt = 1 // 현재 자신 방문함
        for (adjacent in adjacents) {
            if (!visited[adjacent]) { // 인접한 노드를 방문 안한 경우
                cnt += dfs(adjacent, visited)
            }
        }
        
        return cnt
    }
}
