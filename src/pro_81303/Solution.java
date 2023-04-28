package pro_81303;

import java.util.*;

class Solution {
    public String solution(int n, int k, String[] cmd) {
        Graph graph = new Graph(n, k);
        graph.run(cmd);
        return graph.getStatus();
    }
}

class Graph {

    Point point; // 현재 위치한 포인트를 기록
    Node start; // 시작 노드 (삭제하거나 다시 복구하는 과정에서 살아있는 맨 앞의 노드)
    Node last; // 마지막 노드 (삭제하거나 다시 복구하는 과정에서 살아있는 맨 뒤의 노드)
    Node[] nodes; // 노드를 설정할 입력 배열
    Stack<Node> dels = new Stack<>(); // 후입선출 복구를 위한 스택

    class Node {
        boolean isDel; // 출력을 위해 삭제 여부 기록
        Node pre; // 이전 노드
        Node next; // 마지막 노드
        Node () {}
    }

    class Point {
        Node p; // 현재 포인트의 노드
        Point(Node p) {
            this.p = p;
        }
    }

    Graph(int n, int k) {
        nodes = new Node[n];

        for (int i = 0; i < n; i++) nodes[i] = new Node();
        nodes[0].next = nodes[1];
        for (int i = 1; i < n - 1; i++) {
            nodes[i].pre = nodes[i - 1]; // nodes 배열에 i 인덱스에 있는 노드에 노드 연결
            nodes[i].next = nodes[i + 1];
        }

        nodes[n - 1].pre = nodes[n - 2];
        point = new Point(nodes[k]);
        start = nodes[0]; // start 초기화
        last = nodes[n - 1]; // last 초기화
    }

    void up(int dx) {
        Node node = point.p; // 현재 위치한 노드
        Node pre = node;
        int p = 0;
        while(p < dx) {
            pre = pre.pre; // up 은 행을 올려야 하므로 이전 노드로 탐색
            p++;
        }

        point.p = pre;
    }

    void down(int dx) {
        Node node = point.p;
        Node next = node;
        int p = 0;
        while(p < dx) {
            next = next.next; // down은 행을 내려야 하므로 다음 노드로 탐색
            p++;
        }

        point.p = next;
    }

    // 위치한 노드에서 제거
    void del() {
        Node node = point.p;

        if (node == start) { // 만약 맨 앞의 노드가 삭제될 경우 해당 노드의 다음를 start로 설정
            start = node.next;
            start.pre = null;
            point.p = start;
        } else if (node == last) {
            last = node.pre;
            last.next = null;
            point.p = last; // 마지막 인덱스의 경우 앞으로 이동
        } else {
            node.next.pre = node.pre; // 삭제되는 노드의 이전 노드와 다음 노드 연결
            node.pre.next = node.next;
            point.p = node.next; // 삭제되는 노드의 다음 노드로 포인트 변경
        }

        node.isDel = true; // 삭제 여부 표시
        dels.push(node); // 스택에 넣기
    }

    void restore() {
        Node node = dels.pop(); // 삭제된 노드 후입선출
        node.isDel = false; // 복구 설정

        if (node.pre != null && node.next != null) { // 중간에 위치한 노드인경우
            Node pre = node.pre;
            Node next = node.next;
            pre.next = node; // 이전 노드의 다음 노드를 node로 복구
            next.pre = node; // 다음 노드의 이전 노드를 node로 복구
        } else if (node.pre != null) { // next == null
            Node pre = node.pre;
            pre.next = node;
            last = node; // 마지막 노드 업데이트
        } else if (node.next != null) {
            Node next = node.next;
            next.pre = node;
            start = node; // 첫번째 노드 업데이트
        }
    }

    void run(String[] cmds) {
        for (String cmd : cmds) {
            char command = cmd.charAt(0);
            if (cmd.length() > 1) {
                int num = Integer.parseInt(cmd.split(" ")[1]);
                if (command == 'D') down(num);
                else up(num);
            }

            else {
                if (command == 'C') del();
                else restore();
            }
        }

    }

    String getStatus() {
        StringBuilder sb = new StringBuilder();
        for (Node node : nodes) {
            if (node.isDel) sb.append("X");
            else sb.append("O");
        }

        return sb.toString();
    }
}

class Test {
    public static void main(String[] args) {
        String[] cmd = {"D 2", "C", "U 3", "C", "D 4", "C", "U 2", "Z", "Z", "U 1", "C"};
        int n = 8;
        int k = 2;

        Graph graph = new Graph(n, k);
        graph.run(cmd);
        System.out.println(graph.getStatus());
    }
}