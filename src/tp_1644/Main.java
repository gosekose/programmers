package tp_1644;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        Prime prime = new Prime(n);
        prime.run();
        System.out.println(prime.getResult());
    }
}

class Prime {

    List<Integer> primes = new ArrayList<>();

    int result;
    int n;
    Prime(int n) {
        this.n = n;
    }

    void getPrime() {
        int[] arr = new int[n + 1];
        int root = (int) Math.sqrt(n);
        for (int i = 2; i <= n; i++) {
            arr[i] = i;
        }

        for (int i = 2; i <= root; i++) {
            if (arr[i] != 0) {
                for (int j = i * 2; j <= n; j += i) {
                    arr[j] = 0;
                }
            }
        }

        for (int i = 2; i <= n; i++) {
            if (arr[i] != 0) {
                primes.add(arr[i]);
            }
        }
    }

    void run() {
        getPrime();

        int sum = 2;
        int left = 0;
        int right = 0;
        int size = primes.size();

        while (right < size && left < size) {
            if (sum < n) {
                if (++right >= size) break;
                sum += primes.get(right);
            } else if (sum == n) {
                result++; // 개수 증가 시키기
                sum -= primes.get(left++);
            } else {
                sum -= primes.get(left++);
            }
        }
    }

    int getResult() {
        return result;
    }
}
