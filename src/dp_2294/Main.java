package dp_2294;

import java.util.*;
import java.io.*;
import static java.lang.Integer.parseInt;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = parseInt(st.nextToken());
        int k = parseInt(st.nextToken());
        Coin coin = new Coin(n, k);
        
        for (int i = 0; i < n; i++) {
            coin.setCoin(parseInt(br.readLine()));
        }
        
        coin.run();
        System.out.println(coin.getResult());
    }
}

class Coin {
    int n;
    int k;
    int[] dp; // 각 인덱스에 들어갈 값은 코인의 최소 개수
    int result; // 결과 저장
    List<Integer> coins = new ArrayList<>(); // 코인 값 저장
    
    Coin (int n, int k) {
        this.n = n;
        this.k = k;
        dp = new int[k + 1];
        Arrays.fill(dp, Integer.MAX_VALUE); // 모두 최고값으로 초기화
    }
    
    void setCoin(int won) {
        coins.add(won);
    }
    
    void run() {
        
        dp[0] = 0; // 0을 만드는 경우는 0개임
        for (int i = 1; i <= k; i++) {
            for (Integer coin : coins) {
                if (i - coin < 0 || dp[i - coin] == Integer.MAX_VALUE) continue; // 만약 인덱스가 0 미만이거나, 만들 수 없는 값인 경우
                dp[i] = Math.min(dp[i - coin] + 1, dp[i]); // 코인의 값을 인덱스로 뺀다면 그 값이 설정   
            }
        }
        
        if (dp[k] == Integer.MAX_VALUE) result = -1;
        else result = dp[k];
    }
    
    int getResult() {
        return result;
    }
    
}