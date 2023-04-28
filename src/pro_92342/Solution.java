package pro_92342;

class Solution {
    
    int[] lion; // 라이언 점수
    int[] answer; // 정답을 복사할 배열
    int max = -1; // 최대값
    
    public int[] solution(int n, int[] info) {
        lion = new int[11];
        answer = new int[11];
        
        dfs(0, n, info);
        
        if (max == -1) return new int[]{-1}; // 경우의 수가 없는 경우 리턴
        
        return answer;
    }
    
    private void dfs(int depth, int n, int[] apeach) {
        if (depth == n) {
            int score = calculate(apeach); 
            if (max <= score) {
                max = score; // 최대값 업데이트
                answer = lion.clone(); // lion의 배열의 값을 복사, 참조로 할 경우 lion이 바뀌면서 값이 바뀜
            }
            return;
        }
        
        // lion[i] <= apeach[i] 조건 (apeach의 10점을 3번 맞췄다면, lion은 10점을 얻으려면 4번을 맞춰야함)
        for (int i = 0; i < apeach.length && lion[i] <= apeach[i]; i++) {
            lion[i] += 1; // 해당 인덱스에 1 증가 -> 다음 dfs에서도 1 증가할 수 있음 따라서 위의 조건까지 반복
            dfs(depth + 1, n, apeach); 
            lion[i] -= 1; // 위에서 추가한 lion 배열에서 1을 감소하는 작업을 모든 dfs에서 수행하므로 원점 복구 가능
        }
    }
    
    int calculate(int[] apeach) {
        int apeachScore = 0;
        int lionScore = 0;
        
        for (int i = 0; i < lion.length; i++) {
            if (apeach[i] == 0 && lion[i] == 0) continue;
            if (apeach[i] >= lion[i]) apeachScore += (10 - i); 
            else lionScore += (10 - i);
        }
        
        int score = lionScore - apeachScore;
        if (score <= 0) return -1;
        return score;
    }
}