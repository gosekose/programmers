package pro_118668;

import java.util.*;

class Solution {
    public int solution(int alp, int cop, int[][] problems) {
        
        int goalA = 0; // 목표 점수를 기록
        int goalC = 0;  
        
        for (int i = 0; i < problems.length; i++) {
            goalA = Math.max(problems[i][0], goalA); // 알고력의 최대값을 목표 점수로 설정하면, 목표 점수 달성 시 모든 문제의 알고력은 해결
            goalC = Math.max(problems[i][1], goalC); // 마찬가지로 코딩력의 최대값을 목표 점수로 설정하면, 목표 점수 달성 시 모든 문제의 코딩력은 해결
        }
        
        if (goalA <= alp && goalC <= cop) return 0; // 만약 이미 만족한 상태라면 바로 리턴
        
        if (goalA <= alp) alp = goalA; // 인덱스 에러를 방지하기 위해 목표점수보다 alp가 크면 goalA로 설정
        else if (goalC <= cop) cop = goalC;
        
        int[][] dp = new int[goalA + 2][goalC + 2]; // i + 1 혹은 j + 1로 다음 획득할 점수에 대한 cost를 구하기 위해 기존 dp를 만들기 위해 설정한 n + 1에서 +1
        
        for (int i = alp; i <= goalA; i++) {
            for (int j = cop; j <= goalC; j++) 
                dp[i][j] = Integer.MAX_VALUE;
        }
        
        dp[alp][cop] = 0; // 초기값 0 설정 이유: 주어진 alp, cop과 동일한 문제가 있다면 시간 없이 바로 풀 수 있으므로
        
        for (int i = alp; i <= goalA; i++) {
            for (int j = cop; j <= goalC; j++) {
                
                dp[i + 1][j] = Math.min(dp[i + 1][j], dp[i][j] + 1); // 알고력 공부로 알고력 1 증가
                dp[i][j + 1] = Math.min(dp[i][j + 1], dp[i][j] + 1); // 코딩력 공부로 코딩력 1 증가
                
                // 문제로 알고력 혹은 코딩력 증가시키기
                for (int[] problem : problems) { 
                    if (i >= problem[0] && j >= problem[1]) { // 현재 풀 수 있는 문제인 경우
                        int alpUp = problem[2]; // 알고력 증가 가능한량
                        int copUp = problem[3]; // 코딩력 증가 가능한량
                        int cost = dp[i][j] + problem[4]; // 만약 문제를 풀어서 능력을 증가시키기 위한 시간
                        
                        if (i + alpUp > goalA && j + copUp > goalC) { // 둘다 목표 점수 만족
                            dp[goalA][goalC] = Math.min(dp[goalA][goalC], cost); // 이미 달성한 시간과 앞 서 언급한 시간 비교
                        } else if (i + alpUp > goalA){ // 알고력만 목표 알고력 달성                            
                            // j + copUp 하는 이유: 만약 해당 문제를 풀 경우 alpUp, copUp량 만큼 모두 증가하므로
                            dp[goalA][j + copUp] = Math.min(dp[goalA][j + copUp], cost);
                        } else if (j + copUp > goalC) {
                            dp[i + alpUp][goalC] = Math.min(dp[i + alpUp][goalC], cost);
                        } else if (i + alpUp <= goalA && j + copUp <= goalC) { // 둘다 목표 점수보다 작거나 같은 경우
                            dp[i + alpUp][j + copUp] = Math.min(dp[i + alpUp][j + copUp], cost);
                        }
                    }
                }
                
            }
        }
        
        return dp[goalA][goalC];
    }
}