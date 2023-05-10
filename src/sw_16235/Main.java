package sw_16235;

import java.util.*;
import java.io.*;
import static java.lang.Integer.parseInt;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        int n = parseInt(st.nextToken());
        int m = parseInt(st.nextToken());
        int k = parseInt(st.nextToken());
        
        TreeInvestment treeInvestment = new TreeInvestment(n, k);
        
        for (int row = 1; row <= n; row++) {
            st = new StringTokenizer(br.readLine());
            for (int col = 1; col <= n; col++)
                treeInvestment.setNutrientMap(row, col, parseInt(st.nextToken())); // 영양분 초기화
        }
        
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            treeInvestment.setTrees(parseInt(st.nextToken()), parseInt(st.nextToken()), parseInt(st.nextToken())); // 트리 추가
        }
        
        treeInvestment.start();
        System.out.println(treeInvestment.getAlive());
    }    
}

class TreeInvestment {
    
    static final int[] DR = {-1, -1, -1, 0, 0, 1, 1, 1}; // 8개 방향 설정
    static final int[] DC = {-1, 0, 1, -1, 1, -1, 0, 1};
    
    int n; // n + 1 by n + 1 행렬
    int k; // k년 후
    int[][][] nutrientMap; // 영양분 맵
    PriorityQueue<Tree> springQueue = new PriorityQueue<>(Comparator.comparing((Tree tr) -> tr.age)); // 봄에 살아 있는 나무  우선순위 큐
    Queue<Tree> autumnQueue = new ArrayDeque<>(); // 가을에 살아 있는 나무  우선순위 큐
    Queue<Tree> summerQueue = new ArrayDeque<>(); // 여름 큐 (죽은 나무 영양분을 위해)
    
    TreeInvestment(int n, int k) {
        this.n = n;
        this.k = k;
        
        nutrientMap = new int[2][n + 1][n + 1]; // 3차원 배열의 1차원 --> 0: 현재, 1: 매년 추가되는 양분의 양
       
        for (int row = 1; row <= n; row++) {
            for (int col = 1; col <= n; col++) {
                nutrientMap[0][row][col] = 5;  // 5 초기화
            }
        }
    }
    
    void setNutrientMap(int row, int col, int value) {
        nutrientMap[1][row][col] = value; // 매년 추가되는 양분의 양 초기화
    }
    
    void setTrees(int row, int col, int age) {
        springQueue.add(new Tree(row, col, age)); // 트리 스프링큐에 추가하기 
    }
    
    void start() {
        int nowK = 0; // 0년 째
        
        while (nowK < k) {
            while(!springQueue.isEmpty()) { // 봄          
                Tree tree = springQueue.poll();
                int row = tree.row;
                int col = tree.col;

                if (nutrientMap[0][row][col] >= tree.age) {
                    nutrientMap[0][row][col] -= tree.age; // 나이만큼 양분 제거
                    tree.age++; // 나이 ++
                    autumnQueue.add(tree); // 다음 큐에 추가 (내년에도 살아 있게 됨)
                } 
                
                else summerQueue.add(tree); // 여름에 양분 추가를 위해 여름큐 등록
            }
            
            while (!summerQueue.isEmpty()) { // 여름 양분 등록하기 
                Tree tree = summerQueue.poll();
                nutrientMap[0][tree.row][tree.col] += tree.age / 2; // 죽은 나무는 나이 / 2의 양만큼 영양분
            }
            
            while(!autumnQueue.isEmpty()) { // 가을
                Tree tree = autumnQueue.poll();
                
                if (tree.age % 5 == 0) { // 5의 배수는 번식
                    
                    for (int k = 0; k < 8; k++) {
                        int nextR = tree.row + DR[k];
                        int nextC = tree.col + DC[k];
                        
                        if (!isValid(nextR, nextC)) continue;
                        springQueue.add(new Tree(nextR, nextC, 1)); // 나무가 생긴 것은 springQueue에 등록
                    }
                }
                springQueue.add(tree); // 번식한 트리도 queue에 추가
            }
            
            for (int row = 1; row <= n; row++) { // 겨울은 양분을 추가함
                for (int col = 1; col <= n; col++) 
                    nutrientMap[0][row][col] += nutrientMap[1][row][col];
            }
            
            nowK++; // 1년이 지남
        }
        
    }
    
    int getAlive() { // k년뒤에도 살아있다는 것은 springQueue에 있다는 것을 의미
        return springQueue.size();
    }
    
    boolean isValid (int row, int col) {
        return row > 0 && row <= n && col > 0 && col <= n; // 1, 1 시작이므로
    }
    
    static class Tree {
        int row;
        int col;
        int age;
        
        Tree (int row, int col, int age) {
            this.row = row;
            this.col = col;
            this.age = age;
        }
    }
}
