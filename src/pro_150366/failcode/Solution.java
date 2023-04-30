package pro_150366.failcode;// 01:05 // 1:42
// 01:05 // 1:42
import java.util.*;
import static java.lang.Integer.parseInt;

class Solution {
    public String[] solution(String[] commands) {
        Matrix matrix = new Matrix();
        return matrix.apply(commands);
    }
}

class Matrix {

    Node[][] nodes = new Node[51][51]; // 노드 참조 저장 배열
    Point[][] points = new Point[51][51]; // 병합 좌표를 설정하기 위한 포인트
    Map<String, Set<Node>> map = new HashMap<>();  // value를 검색하기 위한 map

    class Node {
        int x;
        int y;
        String value;
        Set<Point> merges = new HashSet<>(); // 병합 포인트 set

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
        for (int i = 1; i < 51; i++) {
            for (int j = 1; j < 51; j++) {
                points[i][j] = new Point(i, j); // 포인트 초기화
            }
        }
    }

    String[] apply(String[] commands) {
        List<String> result = new ArrayList<>(); // 정답 list

        for (String command : commands) {
            String[] cmd = command.split(" "); // commands 문자열 분리

            String c = cmd[0]; // 명령어
            String v1 = cmd[1];
            String v2 = cmd[2];

            if (cmd.length >= 4) {
                String v3 = cmd[3];
                if (cmd.length == 5) {
                    String v4 = cmd[4];
                    merge(parseInt(v1), parseInt(v2), parseInt(v3), parseInt(v4)); // 병합 명령어
                }

                else update(parseInt(v1), parseInt(v2), v3); // 좌표로 업데이트

            } else {
                if (c.equals("UPDATE")) update(v1, v2); // 문자열 value로 업데이트 -> map 활용
                else if (c.equals("UNMERGE")) unMerge(parseInt(v1), parseInt(v2)); // 병합 해제
                else {
                    result.add(print(parseInt(v1), parseInt(v2))); // 프린트 result 저장
                }
            }
        }

        return result.toArray(new String[0]); // list 배열로 바꾸기
    }

    void update(int r, int c, String value) {
        Node node = null; // 노드 초기화

        if (nodes[r][c] != null) { // 업데이트 하려는 노드에 이미 값이 있음
            node = nodes[r][c];
            node.value = value; // 노드의 값 변경

            for (Point p : node.merges) { // 병합된 노드의 포인트 찾기
                nodes[p.x][p.y] = node; // 포인트의 좌표로 노드를 찾아서 참조 수정
            }

        } else {
            node = new Node(r, c, value); // 업데이트 하려는 노드가 없으므로 생성
            nodes[r][c] = node; // 노드 참조
        }

        if(!map.containsKey(value)) { // set에 value가 없음
            Set<Node> set = new HashSet<>(); // set 초기화
            set.add(node); // set 노드 추가
            map.put(value, set); // map의 value에 set 추가
        } else {
            Set<Node> set = map.get(value);
            set.add(node); // set에 노드 추가
        }
    }

    void update(String v1, String v2) { // 값으로 업데이트

        if (!map.containsKey(v1)) return; // 해당 키가 없다면 return

        Set<Node> set = map.get(v1); // set 가져오기
        Set<Node> swap = null; // set에 대한 참조 변경을 위한 swap 설정

        if (map.containsKey(v2)) { // v2에 값이 있는지 확인
            swap = map.get(v2); // swap 참조 변경
        } else {
            swap = new HashSet<>(); // v2가 없다면 swap에 새로운 set
            map.put(v2, swap); // v2 값에 swap 참조 설정
        }

        for (Node nd : set) {
            nd.value = v2; // set에 값을 가져온후 v2로 변경
            swap.add(nd); // swap에 추가
        }
        set.clear(); // v1 값 초기화
    }

    void merge(int r1, int c1, int r2, int c2) {
        Node node = null; // 노드 초기화

        if (r1 == r2 && c1 == c2) return; // 병합 위치가 같다면 리턴
        if (nodes[r1][c1] == null && nodes[r2][c2] == null) return; // 둘다 null이라면 리턴
        else if (nodes[r1][c1] == null) { // 하나만 값이 있는 경우
            node = nodes[r2][c2]; // 노드 참조
            nodes[r1][c1] = node; // r1, c1 좌표에 노드 참조
        }
        else if (nodes[r2][c2] == null) { // 같은 방법으로 참조 연결
            node = nodes[r1][c1];
            nodes[r2][c2] = node;
        }
        else { // 둘 다 null이 아닌 경우, r1, c1값 설정
            node = nodes[r1][c1];
            Node node2 = nodes[r2][c2];

            nodes[r2][c2] = node; // 노드 참조
            for (Point p : node2.merges) {
                Node mergedNode = nodes[p.x][p.y];

                if (mergedNode.value != node.value) { // 만약 value가 다르다면
                    Set<Node> set = map.get(mergedNode.value); // node2.value를 저장한 set 가져옴
                    set.remove(mergedNode); // node2 참조 지우기
                }

                nodes[p.x][p.y] = node; // 병합 노드 참조 변경
                if (!node.merges.contains(p)) node.merges.add(p); // 병합된 포인터 추가
            }

            if (node2.value != node.value) { // 만약 value가 다르다면
                Set<Node> set = map.get(node2.value); // node2.value를 저장한 set 가져옴
                set.remove(node2); // node2 참조 지우기
            }
            // node2.value = node.value;
        }

        if (!node.merges.contains(points[r1][c1])) node.merges.add(points[r1][c1]); // 포인터로 위치 추가
        if (!node.merges.contains(points[r2][c2])) node.merges.add(points[r2][c2]);
    }

    void unMerge(int r, int c) {
        if (nodes[r][c] == null) return; // 병합하는 위치에 값이 null
        Node node = nodes[r][c];
        Set<Node> set = map.get(node.value);
        for (Point p : node.merges) {
            if (r == p.x && c == p.y) continue; // 포인터가 현재 위치라면 값 유지
            Node findNode = nodes[p.x][p.y];
            set.remove(findNode); // set에서 노드 제거
            nodes[p.x][p.y] = null;
        }
        node.merges.clear(); // 병합한 포인터 전부 제거
    }

    String print(int r, int c) {
        if (nodes[r][c] == null) return "EMPTY";
        return nodes[r][c].value;
    }

}

// 업데이트할 때, 해당 값의 위치를 저장해야함 (map)
// 반례가 이해가 안됌 ㅠㅠ

//입력값 〉	["UPDATE 1 1 abc", "MERGE 1 1 1 2", "MERGE 1 2 1 3", "MERGE 1 3 1 4", "MERGE 1 2 1 7", "MERGE 1 1 1 7", "MERGER 1 7 3 1", "UPDATE 3 1 4", "PRINT 1 1", "PRINT 1 2", "PRINT 1 3", "PRINT 1 4", "PRINT 1 7", "PRINT 3 1"]
//기댓값 〉	["4", "4", "4", "4", "4", "4"]
//실행 결과   실행한 결괏값 ["abc","abc","abc","abc","abc","4"]이 기댓값 ["4","4","4","4","4","4"]과 다릅니다.