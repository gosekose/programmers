class Programmers160585 {

    private var oCnt = 0
    private var xCnt = 0
    private var arr = Array(5) { Array(5) {'.'} }

    fun solution(board: Array<String>): Int {
        for (i in 0..2) {
            for (j in 0..2) {
                if (board[i][j] == 'O') oCnt++
                else if (board[i][j] == 'X') xCnt++
                arr[i + 1][j + 1] = board[i][j]
            }
        }

        if (!(oCnt == xCnt || oCnt - 1 == xCnt)) return 0 // 개수가 같거나 하나 적어야

        for (i in 1..3) {
            for (j in 1..3) {
                if (
                    arr[i - 1][j] == arr[i][j] && arr[i + 1][j] == arr[i][j] && arr[i][j] != '.' ||
                    arr[i][j - 1] == arr[i][j] && arr[i][j + 1] == arr[i][j] && arr[i][j] != '.' ||
                    arr[i - 1][j - 1] == arr[i][j] && arr[i + 1][j + 1] == arr[i][j] && arr[i][j] != '.' ||
                    arr[i - 1][j + 1] == arr[i][j] && arr[i + 1][j - 1] == arr[i][j] && arr[i][j] != '.'
                ) if (!validate(i, j)) return 0
            }
        }

        return 1
    }

    private fun validate(row: Int, col: Int): Boolean {
        if (arr[row][col] == 'O') {
            if (oCnt - 1 != xCnt) return false // o인데 개수가 하나 적지 않으면
        } else if (arr[row][col] == 'X') {
            if (oCnt != xCnt) return false // x인데 같지 않으면
        }

        return true
    }
}