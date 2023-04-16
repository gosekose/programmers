package pro_152995;

import java.util.*;

class Solution {
    public int solution(int[][] scores) {
        int answer = 1;
        int[] wanho = scores[0];
        
        Arrays.sort(scores, Comparator.comparing((int[] arr) -> arr[0])
                    .reversed()
                    .thenComparing((int[] arr) -> arr[1]));
                
        int myPoint = wanho[0] + wanho[1];
        
        int peerPoint = 0;
        
        for (int[] score : scores) {
            if (score[1] < peerPoint) {
                if (wanho[0] == score[0] && wanho[1] == score[1])
                    return -1;
            }
            
            else {
                peerPoint = Math.max(score[1], peerPoint);
                if (myPoint < score[0] + score[1]) 
                    answer++;
            }
        }
            
        return answer;
    }
}
