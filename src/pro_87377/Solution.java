package pro_87377;

import java.util.*;

class Solution {
    public String[] solution(int[][] line) {
        String[] answer = {};
        List<Node> nodes = new ArrayList<>();
        long minX = Long.MAX_VALUE;
        long minY = Long.MAX_VALUE;
        long maxX = Long.MIN_VALUE;
        long maxY = Long.MIN_VALUE;
        
        for (int i = 0; i < line.length; i++) {
            long a = line[i][0];
            long b = line[i][1];
            long e = line[i][2];
            
            for (int j = i + 1; j < line.length; j++) {
                long c = line[j][0];
                long d = line[j][1];
                long f = line[j][2];
                
                long denominator = a * d - b * c;
                long dx = b * f - e * d;
                long dy = e * c - a * f;
                
                if (denominator != 0) {
                    double x = dx / (double) denominator;
                    double y = dy / (double) denominator;
                    
                    if (x == Math.ceil(x) && y == Math.ceil(y)) {
                        long lX = (long) x;
                        long lY = (long) y;
                        nodes.add(new Node(lX, lY));
                        minX = Math.min(minX, lX);
                        maxX = Math.max(maxX, lX);
                        minY = Math.min(minY, lY);
                        maxY = Math.max(maxY, lY);
                    }
                }
            }
        }
        
        boolean[][] flag = new boolean[(int) (maxY - minY + 1)][(int) (maxX - minX + 1)];
        
        for (Node node : nodes) {
            int nx = (int) (node.x - minX);
            int ny = (int) (node.y - maxY);
            flag[Math.abs(ny)][Math.abs(nx)] = true;
        }
        
        answer = new String[flag.length];
        
        for (int i = 0; i < flag.length; i++) {
            StringBuilder sb = new StringBuilder();
            boolean[] flagIdxArr = flag[i];
            for (int j = 0; j < flagIdxArr.length; j++) {
                if (flagIdxArr[j]) sb.append("*");
                else sb.append(".");
            }
            
            answer[i] = sb.toString();
        } 
        
        
        return answer;
    }
    
    class Node {
        long x;
        long y;
        
        Node (long x, long y) {
            this.x = x;
            this.y = y;
        }
    }
}