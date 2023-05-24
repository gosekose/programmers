package mst_4386;

import java.util.*;
import java.io.*;
import static java.lang.Double.parseDouble;
public class Main {
    
    static int n;
    static int[] parent;
    static int[] count;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());
        parent = new int[n];
        count = new int[n];
        double[][] star = new double[n][2];
        
        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            double x = parseDouble(st.nextToken());
            double y = parseDouble(st.nextToken());
            star[i][0] = x;
            star[i][1] = y;
            parent[i] = i;
            count[i] = 1;
        }
        if (n == 1) {
            System.out.println(0.00);
            return;
        }
        
        System.out.printf("%.2f", getDistance(star));
    }
    
    static double getDistance(double[][] star) {
        Queue<Star> queue = new PriorityQueue<>(Comparator.comparing((Star s) -> s.distance));
        
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                double distance = Math.sqrt(Math.pow((star[i][0] - star[j][0]), 2) + Math.pow((star[i][1] - star[j][1]), 2));
                queue.add(new Star(i, j, distance));
            }
        }
        
        double distance = 0.0;
        while(!queue.isEmpty()) {
            Star s = queue.poll();
            int p1 = find(s.idx1);
            int p2 = find(s.idx2);

            if (p1 != p2) {
                union(p1, p2);
                distance += s.distance;
            }

            if (count[p1] == n) break;
        }
        
        return distance;
    } 
    
    
    static int find(int child) {
        if (parent[child] == child) return child;
        return parent[child] = find(parent[child]);
    }
    
    static void union(int p1, int p2) {
        parent[p2] = p1;
        count[p1] += count[p2];
        count[p2] = 0;
    }
    
    static class Star {
        int idx1;
        int idx2;
        double distance;
        
        Star (int idx1, int idx2, double distance) {
            this.idx1 = idx1;
            this.idx2 = idx2;
            this.distance = distance;
        }
    }
}