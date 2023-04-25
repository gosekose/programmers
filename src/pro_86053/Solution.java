package pro_86053;

class Solution {
    public long solution(int a, int b, int[] g, int[] s, int[] w, int[] t) {
        long answer = (long) (10e9 * 2 * 10e5 * 2); // ((금 + 은) / 한번에 옮길 수 있는 무게) * (옮기는 시간)
        int len = g.length;
        long start = 0;
        long end = (long) (10e9 * 2 * 10e5 * 2);
        
        while (start <= end) {
            long mid = (start + end) / 2;
            int gold = 0;
            int silver = 0;
            int add = 0;
            
            for (int i = 0; i < len; i++) {
                int nowG = g[i];
                int nowS = s[i];
                int nowW = w[i];
                int nowT = t[i];
                
                long moveCnt = mid / (nowT * 2);  
                if (mid % (nowT * 2) >= t[i]) moveCnt++;
                
                gold += Math.min(nowG, moveCnt * nowW);
                silver += Math.min(nowS, moveCnt * nowW);
                add += Math.min(nowG + nowS, moveCnt * nowW);
            }
            
            if (a <= gold && b <= silver && a + b <= add) {
                end = mid - 1;
                answer = Math.min(mid, answer);
                continue;
            }
            
            start = mid + 1;
        }
        
        return answer;
    }
}