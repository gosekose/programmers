package str_1725;

import java.io.*;
import java.util.*;
import static java.lang.Integer.parseInt;

public class Main {

    static int n;
    static int[] histogram;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        n = parseInt(br.readLine());
        histogram = new int[n];

        for (int i = 0; i < n; i++) histogram[i] = parseInt(br.readLine());
        sb.append(getArea());
        System.out.println(sb);
    }

    static int getArea() { // 최대 20억을 넘지 않음
        Stack<Integer> stack = new Stack<>();
        int area = 0; // 20억을 넘지 않음

        for (int i = 0; i < n; i++) {

            // 스택이 비지 않고 인덱스의 값이 peek의 히스토그램 값보다 작거나 같다면
            while (!stack.isEmpty() && histogram[stack.peek()] >= histogram[i]) {
                int h = histogram[stack.pop()];

                // 스택이 비어 있다면 길이는 i, 스택이 있다면 현재 위치에서 stack의 인덱스
                int w = stack.isEmpty() ? i : i - 1 - stack.peek();

                area = Math.max(area, h * w);
            }
            stack.push(i);
        }

        // stack에 남아있는거 전부 제거하며 다시 업데이트
        while(!stack.isEmpty()) {
            int h = histogram[stack.pop()];

            int w = stack.isEmpty() ? n : n - 1 - stack.peek();
            area = Math.max(area, h * w);
        }

        return area;
    }
}