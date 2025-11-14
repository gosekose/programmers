package acmicpc

import java.io.*
import java.util.*

fun main(args: Array<String>) {
    val test = Test2026()
    test.solve()
}

class Test2026 {
    var arr: Array<IntArray> = arrayOf()
    var visited: BooleanArray = booleanArrayOf()
    var indegree: IntArray = intArrayOf()
    var K = 0
    var N = 0
    var F = 0
    var eof = false

    fun solve() {
        val br = BufferedReader(InputStreamReader(System.`in`))
        val st = StringTokenizer(br.readLine())
        K = st.nextToken().toInt()
        N = st.nextToken().toInt()
        F = st.nextToken().toInt()

        arr = Array(N + 1) { IntArray(N + 1) }
        visited = BooleanArray(N + 1)
        indegree = IntArray(N + 1)

        for (i in 0 until F) {
            val str = StringTokenizer(br.readLine())
            val n1 = str.nextToken().toInt()
            val n2 = str.nextToken().toInt()

            arr[n1][n2] = 1
            arr[n2][n1] = 1
            indegree[n1]++
            indegree[n2]++
        }

        /* 자신을 제외한 친구 개수가 적으면 스킵 */
        for (i in 1 .. N) {
            if (indegree[i] < K - 1) continue
            if (eof) break

            /* 자신은 참여 처리하고 친구 관계 체크 */
            visited[i] = true
            dfs(i, 1)
            visited[i] = false
        }

        if (!eof) {
            println(-1)
        }
    }

    fun dfs(cur: Int, depth: Int) {
        if (eof) return

        if (depth == K) {
            write()
            eof = true
            return
        }

        for (i in cur+1 .. N) {
            /* 현재 친구로 선택한 친구관계여야 함 && 이미 방문 = 친구 세팅된 관계에서 1이여야 함 */
            if (!(arr[cur][i] == 1 && isFriend(i))) continue

            visited[i] = true
            dfs(i, depth + 1)
            visited[i] = false
        }
    }

    fun isFriend(x: Int): Boolean {
        for (i in 1 .. N) {
            /* 친구로 세팅된 관계에서 서로 친구가 아니면 불가*/
            if (visited[i] && arr[x][i] != 1) {
                return false
            }
        }
        /* 친구가 아니여도 방문하지 않았다면 가능할수 있음 */
        return true
    }

    fun write() {
        val sb = StringBuilder()

        for (i in 1 .. N) {
            if (visited[i]) sb.append(i).append("\n")
        }
        val bw = BufferedWriter(OutputStreamWriter(System.out))
        bw.write(sb.toString())
        bw.flush()
    }
}