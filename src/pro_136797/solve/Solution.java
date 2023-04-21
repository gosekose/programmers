package pro_136797.solve;

import java.util.*;

class Solution {
    public int[][] cost = {
        { 1, 7, 6, 7, 5, 4, 5, 3, 2, 3 },
        { 7, 1, 2, 4, 2, 3, 5, 4, 5, 6 },
        { 6, 2, 1, 2, 3, 2, 3, 5, 4, 5 },
        { 7, 4, 2, 1, 5, 3, 2, 6, 5, 4 },
        { 5, 2, 3, 5, 1, 2, 4, 2, 3, 5 },
        { 4, 3, 2, 3, 2, 1, 2, 3, 2, 3 },
        { 5, 5, 3, 2, 4, 2, 1, 5, 3, 2 },
        { 3, 4, 5, 6, 2, 3, 5, 1, 2, 4 },
        { 2, 5, 4, 5, 3, 2, 3, 2, 1, 2 },
        { 3, 6, 5, 4, 5, 3, 2, 4, 2, 1 }
    };

    int[][][] dp;
    String arr;
    int len;
    
    public int solve(int i, int l, int r) {

        if (i == len) return 0;
        if (dp[i][l][r] != -1) return dp[i][l][r];
        
        int num = arr.charAt(i) - '0';
        int result = Integer.MAX_VALUE;
        
        if (num != r) result = Math.min(solve(i + 1, num, r) + cost[l][num], result);
        
        if (num != l) result = Math.min(solve(i + 1, l, num) + cost[r][num], result);
        return dp[i][l][r] = result;
    }
    
    public int solution(String numbers) {
        arr = numbers;
        len = numbers.length();
        dp = new int[len + 1][10][10];
        for (int i = 0; i < len + 1; i++) {
            for (int j = 0; j < 10; j++)
                Arrays.fill(dp[i][j], -1);
        }
        return solve(0, 4, 6);
    }
}