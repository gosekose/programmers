package pro_150366.success;

import java.util.*;
import static java.lang.Integer.parseInt;

class Solution {
    public String[] solution(String[] commands) {
        Matrix matrix = new Matrix();
        return matrix.apply(commands);
    }
}

class Matrix {
    static final int RANGE = 2501;
    
    int[] parent = new int[RANGE];
    String[] values = new String[RANGE];
    
    Matrix() {
        for (int i = 1; i < RANGE; i++) {
            parent[i] = i;
            values[i] = "";
        }
    };
    
    int find(int a) {
        if (parent[a] == a) return a;
        else return parent[a] = find(parent[a]);
    }
    
    void union(int a, int b) {
        a = find(a);
        b = find(b);
        if (a != b) parent[b] = a;
    }
    
    int convertIndex(int row, int col) {
        return 50 * (row - 1) + col; // row = 1, y = 1 -> 1 // row = 2, y = 1 -> 51
    }
    
    void update(int r, int c, String value) {
        int point = convertIndex(r, c);
        values[find(point)] = value;
    }
    
    void update(String before, String after) {
        for (int i = 1; i < RANGE; i++) {
            if (before.equals(values[i])) values[i] = after;
        }
    }
    
    void merge(int r1, int c1, int r2, int c2) {
        int p1 = convertIndex(r1, c1);
        int p2 = convertIndex(r2, c2);
        
        int rootP1 = find(p1);
        int rootP2 = find(p2);
        
        if (rootP1 == rootP2) return;
        
        String root = values[rootP1].isBlank() ? values[rootP2] : values[rootP1];
        values[rootP1] = "";
        values[rootP2] = "";
        
        union(rootP1, rootP2);
        values[rootP1] = root;     
    }
    
    void unmerge(int r, int c) {
        int p = convertIndex(r, c);
        int root = find(p);
        
        String rootValue = values[root];
        values[root] = "";
        values[p] = rootValue;
        List<Integer> dels = new ArrayList<>();
        for (int i = 1; i < RANGE; i++) {
            if (find(i) == root) {
                dels.add(i);
            }
        }
        for (Integer i : dels) {
            parent[i] = i;
        }
    }
    
    String print(int r, int c) {
        int p = convertIndex(r, c);
        int root = find(p);
        
        if (values[root].isBlank()) return "EMPTY";
        return values[root];
    }
    
    String[] apply(String[] commands) {
        List<String> result = new ArrayList<>();
        
        for (String command : commands) {
            String[] cmds = command.split(" ");
            
            String cmd = cmds[0];
            String v1 = cmds[1];
            String v2 = cmds[2];
            
            if (cmds.length >= 4) {
                String v3 = cmds[3];
                if (cmds.length == 5) merge(parseInt(v1), parseInt(v2), parseInt(v3), parseInt(cmds[4]));
                else update(parseInt(v1), parseInt(v2), v3);
            } else {
                if (cmd.equals("UPDATE")) update(v1, v2);
                else if (cmd.equals("UNMERGE")) unmerge(parseInt(v1), parseInt(v2));
                else result.add(print(parseInt(v1), parseInt(v2)));
            }
        }
        
        return result.toArray(new String[0]);
    }
}

// 틀린 이유: merge시 값이 없어도 merge 가능해야함 -> union find로 문제 해결