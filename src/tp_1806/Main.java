package tp_1806;

import java.io.*;
import java.util.StringTokenizer;

import static java.lang.Integer.parseInt;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = parseInt(st.nextToken());
        int s = parseInt(st.nextToken());
        PartialSum ps = new PartialSum(n, s);

        st = new StringTokenizer(br.readLine());
        for (int i = 0 ; i < n; i++) {
            ps.setMap(i, parseInt(st.nextToken()));
        }

        ps.run();
        System.out.println(ps.getResult());
    }
}

class PartialSum {
    int n;
    int target;
    int result = Integer.MAX_VALUE;
    int[] map;

    public PartialSum(int n, int target) {
        this.n = n;
        this.target = target;
        map = new int[n + 1];
    }

    void setMap(int i, int value) {
        map[i] = value;
    }

    void run() {
        int length = 0;
        int sum = 0;
        int left = 0;
        int right = 0; // n 10 이상이므로 1 인덱스 이상무

        while (left <= n && right <= n) {
            if (sum < target) { // 타겟이 더 큰 경우
                sum += map[right++];
            } else {
                sum -= map[left++];
                length = right - left + 1;
                result = Math.min(length, result);
            }
        }
    }

    int getResult() {
        return result == Integer.MAX_VALUE ? 0 : result;
    }
}

// 순서
// 포인터에 위치한 값을 처리하고 포인터 이동
// sum이 target보다 크거나 같다면,
// sum에서 left포인트 값 빼고, left 이동 시킴
// 그 후 right - left한 후 + 1 (개수 이므로)

// sum이 target보다 작다면
// sum에서 right값을 더하고 포인터 이동

// 0 1 2 3 4
// 0     0
//

// target = 3
// p: 1, 3,  sum : 1 + 2
// p: 2, 3   sum : 2    --> length = 3 - 2 + 1
// p: 2, 3 sum(2)
