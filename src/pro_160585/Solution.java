package pro_160585;
class Solution {

    public int solution(String[] board) {
        int oCnt = 0;
        int xCnt = 0;

        for (int i = 0; i < 3; i++) {
            oCnt += countChar(board[i], 'O');
            xCnt += countChar(board[i], 'X');
        }

        if (xCnt > oCnt) return 0;
        if (oCnt > xCnt + 1) return 0;
        if (hasWin(board, 'O')) {
            if (oCnt == xCnt) return 0;
        }
        if (hasWin(board, 'X')) {
            if (oCnt == xCnt + 1) return 0;
        }

        return 1;
    }

    private static int countChar(String str, char ch) {
        return str.length() - str.replace(String.valueOf(ch), "").length();
    }

    private static boolean hasWin(String[] board, char ch) {

        //
        for (int i = 0; i < 3; i++) {
            if (board[i].charAt(0) == ch && board[i].charAt(1) == ch && board[i].charAt(2) == ch)
                return true;

        }

        for (int i = 0; i < 3; i++) {
            if (board[0].charAt(i) == ch && board[1].charAt(i) == ch && board[2].charAt(i) == ch)
                return true;
        }

        if (board[0].charAt(0) == ch && board[1].charAt(1) == ch && board[2].charAt(2) == ch)
            return true;

        if (board[0].charAt(2) == ch && board[1].charAt(1) == ch && board[2].charAt(0) == ch)
            return true;

        return false;
    }
}
