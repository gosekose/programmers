package pro_150368;

class Solution {
    
    static final int[] RATE = {60, 70, 80, 90};
    static int plus = 0;
    static int profit = 0;
    int elen;
    
    public int[] solution(int[][] users, int[] emoticons) {
        elen = emoticons.length;  
        int[] rates = new int[elen]; // 이모티콘의 개수만큼 할인률을 적용할 배열 생성 
        
        dfs(users, emoticons, rates, 0); // dfs 적용
        return new int[]{plus, profit};
    }
    
    void dfs(int[][] users, int[] emoticons, int[] rates, int len) {
        if (len == elen) { // len == elen 의미는 이모티콘의 개수만큼 상수 RATE에서 순열의 rates 배열이 생성된 것을 의미
            satisfy(users, emoticons, rates); // 생성된 rates 배열을 순회하며 유저들의 plus와 총 profit 구하기
            return ;
        }
        
        for (int rate : RATE) {
            rates[len] = rate; // RATE에서 배열을 순회하며 rates[len] 배열에 값을 끼워넣음
            dfs(users, emoticons, rates, len + 1); // dfs로 len + 1 재귀
        }
    }
    
    void satisfy(int[][] users, int[] emoticons, int[] rates) {
        int tmpPlus = 0; // 임시 plus 개수 (dfs로 완전탐색하면 배열마다 새로운 tmpPlus가 생성되며 plus와 비교하기 위해)
        int tmpProfit = 0; // 임시 profit
        
        for (int[] user : users) {
            int r = user[0]; // 유저 거래 가능 할인률
            int limit = user[1]; // plus에 가입할 수 있는 하한선
            int cCost = 0; // 유저 구매 비용
            
            int cRate = 100 - r; // 만약 30 -> 70
            for (int i = 0; i < elen; i++) {
                if (cRate >= rates[i]) // 70 >= 60
                cCost += (int) emoticons[i] * (rates[i] * 0.01);
                
                if (cCost >= limit) {
                    tmpPlus++;  // 구매 비용이 플러스 가입 하한선을 넘거나 같은 경우
                    cCost = 0;
                    break;                    
                }
            }
            tmpProfit += cCost; // 하한선을 넘지 않는 경우         
        }
            
        if (tmpPlus > plus) { // dfs로 구한 rates 배열에 대한 tmpPlus 값 비교
            plus = tmpPlus;
            profit = tmpProfit;
        }
        
        else if (tmpPlus == plus) {
            profit = Math.max(tmpProfit, profit); // 만약 개수는 같다면 금액만 최고 금액으로 업데이트
        }
    }
}