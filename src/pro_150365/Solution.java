package pro_150365;

class Solution {
    
    Node node;
    int row; // 행 범위
    int col; // 열 범위
    int targetX; // 목표 x좌표
    int targetY; // 목표 y좌표
    int targetStep;
    
    public String solution(int n, int m, int x, int y, int r, int c, int k) {
        String answer = "";
        
        row = n + 1; 
        col = m + 1;
        targetX = r; 
        targetY = c;
        targetStep = k;
        node = new Node(x, y); // 출발 노드 설정
    
        while(node.step <= targetStep) {
            if (node.step == targetStep) {
                if (node.x == targetX && node.y == targetY) {
                    return node.answer;
                }
            }
            
            if (isPossibleD()) { // 아래로 갈 수 있는 조건을 만족한다면 x++ (행을 내려야 하므로 +)
                node.x++;
                node.answer += "d";
            } else if (isPossibleL()) { // 다음 우선 순위로 L 탐색
                node.y--;
                node.answer += "l";
            } else if (isPossibleR()) {
                node.y++;
                node.answer += "r";
            } else if (isPossibleU()) {
                node.x--;
                node.answer += "u";
            } else { // 위의 분기문을 먼저 고려한 결과 해당하지 않는다면 탈출
                answer = "impossible"; 
                break;
            }
            
            node.step++; // break되지 않았다면 어느 한 방향으로 움직였다는 의미이므로 step++
        }
        
        
        return answer;
    }
    
    
    class Node {
        int x;
        int y;
        int step;
        String answer = "";
        
        Node (int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    
    boolean isPossibleD() { // down은 행을 내려야하므로 +
        return node.x > 0 && node.x < row - 1 && isPossibleMove(node.x + 1, node.y);
    }
    
    boolean isPossibleU() { // up은 행을 올려야 하므로 -
        return node.x > 1 && (node.x < row) && isPossibleMove(node.x - 1, node.y);
    }
    
    boolean isPossibleL() { // left는 열을 왼쪽으로 가야하므로 -
        return node.y > 1 && node.y < col && isPossibleMove(node.x, node.y - 1);
    }
    
    boolean isPossibleR() { // right는 열을 오른쪽으로 가야하므로 +
        return node.y > 0 && (node.y < col - 1) && isPossibleMove(node.x, node.y + 1);
    }
    
    boolean isPossibleMove(int x, int y) {
        return Math.abs(targetX - x) + Math.abs(targetY - y) + node.step + 1 <= targetStep;
    }
    
}