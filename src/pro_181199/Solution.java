package pro_181199;// 16:50

import java.util.*;

class Solution {
    public int solution(int[][] targets) {
        int answer = 1;
        
        Arrays.sort(targets, Comparator.comparingInt((int[] arr) -> arr[1]));
        
        int end = targets[0][1]; // end 위치가 작은 것의 첫번 째 인덱스로 초기화
        
        for (int[] arr : targets) {
            if (arr[0] >= end) { // 시작 위치가 끝점보다 같거나 크다면 
                answer++; // 개수 증가
                end = arr[1]; // end점 업데이트
            }
        }

        return answer;
    }
}


// 끝점의 위치가 다음 인덱스의 시작 위치보다 큰 경우 그대로
// 만약 다음 인덱스 시작 위치가 끝점의 인덱스 위치보다 같거나 크다면 끝점을 업데이트하고 cnt ++
