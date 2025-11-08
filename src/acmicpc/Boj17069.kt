package acmicpc

var board: Array<IntArray> = arrayOf()
var dp: Array<Array<LongArray>> = arrayOf()
var N = 0

fun main(args: Array<String>) {
    N = readLine()!!.toInt()

    board = Array(N) { IntArray(N) }
    dp = Array(N) { Array(N) { LongArray(3) {-1L} } } // dp

    for (i in 0 until N) {
        val nums = readln().split(" ").map { it.toInt() }
        for (j in nums.indices) {
            board[i][j] = nums[j]
        }
    }

    val answer = dfs(0, 1, 0)

    println(answer)
}

// 현재 위치, 어떤 방향에서 왔는지
fun dfs(row: Int, col: Int, dir: Int): Long {
    // 현재 있는 dir 방향에 대해서 움직임을 처리한다
    if (dp[row][col][dir] != -1L) return dp[row][col][dir]

    if (row == N - 1 && col == N - 1) {
        dp[row][col][dir] = 1L
        return 1L
    }

    var ways = 0L

    // 가로 이동은 두 가지
    if (dir == 0) {
        val dr1 = row
        val dc1 = col + 1

        if (isPossible(dr1, dc1)) {
            ways += dfs(dr1, dc1, 0)
        }

        val dr2 = row + 1
        val dc2 = col + 1

        if (isPossible(row, dc2) && isPossible(dr2, col) && isPossible(dr2, dc2)) {
            ways += dfs(dr2, dc2, 2)
        }

    } else if (dir == 1) {
        val dr1 = row + 1
        val dc1 = col

        if (isPossible(dr1, dc1)) {
            ways += dfs(dr1, dc1, 1)
        }

        val dr2 = row + 1
        val dc2 = col + 1

        if (isPossible(row, dc2) && isPossible(dr2, col) && isPossible(dr2, dc2)) {
            ways += dfs(dr2, dc2, 2)
        }

    } else if (dir == 2) {
        val dr1 = row + 1
        val dc1 = col

        if (isPossible(dr1, dc1)) {
            ways += dfs(dr1, dc1, 1)
        }

        val dr2 = row
        val dc2 = col + 1

        if (isPossible(dr2, dc2)) {
            ways += dfs(dr2, dc2, 0)
        }

        val dr3 = row + 1
        val dc3 = col + 1

        if (isPossible(row, dc3) && isPossible(dr3, col) && isPossible(dr3, dc3)) {
            ways += dfs(dr3, dc3, 2)
        }
    }

    dp[row][col][dir] = ways
    return ways
}

fun isPossible(row: Int, col: Int): Boolean {
    if (row < 0 || row >= N || col < 0 || col >= N || board[row][col] == 1) return false
    return true
}