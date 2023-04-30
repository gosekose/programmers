package pro_150366.failcode;// 01:05 // 1:42
import java.util.*;
import static java.lang.Integer.parseInt;

class Solution {
    public String[] solution(String[] commands) {
        Matrix matrix = new Matrix();
        return matrix.apply(commands);
    }
}

class Matrix {
    
    Node[][] nodes = new Node[51][51];
    Point[][] points = new Point[51][51];
    Map<String, Set<Node>> map = new HashMap<>(); 
    
    
    class Node {
        int x;
        int y;
        String value;
        Set<Point> merges = new HashSet<>();
                
        Node(int x, int y, String value) {
            this.x = x;
            this.y = y;
            this.value = value;
        }
    }
    
    class Point {
        int x;
        int y;
        
        Point (int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    
    
    Matrix() {
        for (int i = 0; i < 51; i++) {
            for (int j = 0; j < 51; j++) {
                points[i][j] = new Point(i, j);
            }
        } 
    }
    
    String[] apply(String[] commands) {
        List<String> result = new ArrayList<>();
        
        for (String command : commands) {
            String[] cmd = command.split(" ");

            String c = cmd[0];
            String v1 = cmd[1];
            String v2 = cmd[2];
            
            if (cmd.length >= 4) {
                String v3 = cmd[3];
                if (cmd.length == 5) {
                    String v4 = cmd[4];
                    merge(parseInt(v1), parseInt(v2), parseInt(v3), parseInt(v4));
                } 
                
                else update(parseInt(v1), parseInt(v2), v3);
                
            } else {
                if (c.equals("UPDATE")) update(v1, v2);
                else if (c.equals("UNMERGE")) unMerge(parseInt(v1), parseInt(v2));
                else {
                    result.add(print(parseInt(v1), parseInt(v2)));
                }
            }
        }
        
        return result.toArray(new String[0]);
    }
    
    void update(int r, int c, String value) {
        Node node = new Node(r, c, value);
        nodes[r][c] = node;
        
        if(!map.containsKey(value)) {
            Set<Node> set = new HashSet<>();
            set.add(node);
            map.put(value, set);
        } else {
            Set<Node> set = map.get(value);
            set.add(node);
        }
    }
    
    void update(String v1, String v2) {
        Set<Node> set = map.get(v1);
        Set<Node> swap = null;
        
        if (map.containsKey(v2)) {
            swap = map.get(v2);
        } else {
            swap = new HashSet<>();
            map.put(v2, swap);
        }
        
        for (Node nd : set) {
            nd.value = v2;
            swap.add(nd);
        }
        set.clear();
    }
    
    void merge(int r1, int c1, int r2, int c2) {
        Node node = null;
        
        if (r1 == r2 && c1 == c2) return;
        if (nodes[r1][c1] == null && nodes[r2][c2] == null) return;
        else if (nodes[r1][c1] == null) {
            node = nodes[r2][c2];
            nodes[r1][c1] = node;
        } 
        else if (nodes[r2][c2] == null) {
            node = nodes[r1][c1];
            nodes[r2][c2] = node;
        } 
        else {
            node = nodes[r1][c1];
            Node node2 = nodes[r2][c2];
            
            nodes[r2][c2] = node;
            Set<Node> set = map.get(node2.value);
            set.remove(node2);
        }
        
        if (!node.merges.contains(points[r1][c1])) node.merges.add(points[r1][c1]);
        if (!node.merges.contains(points[r2][c2])) node.merges.add(points[r2][c2]);
    }
    
    void unMerge(int r, int c) {
        Node node = nodes[r][c];
        for (Point p : node.merges) {
            if (r == p.x && c == p.y) continue;
            nodes[p.x][p.y] = null; // 초기화
        }
        node.merges.clear();
    }
    
    String print(int r, int c) {
        if (nodes[r][c] == null) return "EMPTY";
        return nodes[r][c].value;
    }
    
}

// 업데이트할 때, 해당 값의 위치를 저장해야함 (map)
