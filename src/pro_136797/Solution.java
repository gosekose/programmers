package pro_136797;

// 20:27 ~ 21:22
// 20:27 ~ 21:22
class Solution {

    static final int[] D_X = {0, 0, 0, 0, 0, 0, 0,    1, 1, 1, 1, 1, 1, 1,     -1, -1, -1, -1, -1, -1, -1,     2, 2, 2, 2, 2,     -2, -2, -2, -2, -2};
    static final int[] D_Y = {0, 1, -1, 2, -2, 3, -3, 0, 1, 2, 3, -1, -2, -3,   0, 1, 2, 3, -1, -2, -3,        0, 1, 2, -1, -2,    0, 1, 2, -1, -2};
    static final int[] D_W = {1, 2, 2, 4, 4, 6, 6,    2, 3, 5, 7, 3, 5, 7,      2, 3, 5, 7, 3, 5, 7,           4, 5, 6, 5, 6,      4, 5, 6, 5, 6};
    static final int[][] keyboard = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}, {0, 0, 0}};

    int answer = Integer.MAX_VALUE;

    public int solution(String numbers) {
        dfs(0, numbers, 0, new Node(1, 0), new Node(1, 2));

        return answer;
    }

    private void dfs(int weight, String numbers, int index, Node lp, Node rp) {
        if (numbers.length() - 1 == index) {
            System.out.println("weight = " + weight);
            answer = Math.min(answer, weight);
            return;
        } else if (numbers.length() <= index) {
            System.out.println("인덱스 초과");
            return;
        }

        if (weight >=  answer) return;

        int n = Character.getNumericValue(numbers.charAt(index));
        System.out.println("n = " + n);
        for (int k = 0; k < D_X.length; k++) {

            int newLX = lp.x + D_X[k];
            int newLY = lp.y + D_Y[k];
            System.out.println("newLX = " + newLX + ", newLY = " + newLY);

            int newRX = rp.x + D_X[k];
            int newRY = rp.y + D_Y[k];
            System.out.println("newRX = " + newRX + ", newRY = " + newRY);

            int newW = D_W[k];

            if (validateRange(newLX, newLY)) {
                System.out.println(keyboard[newLX][newLY]);
                if (keyboard[newLX][newLY] == n) {
                    System.out.println("L dfs x = " + newLX + ", dfs y = " + newLY);
                    dfs(weight + newW, numbers, index + 1, new Node(newLX, newLY), rp);
                }
            }

            if (validateRange(newRX, newRY)) {
                if (keyboard[newRX][newRY] == n) {
                    System.out.println("R dfs x = " + newRX + ", dfs y = " + newRY);
                    dfs(weight + newW, numbers, index + 1, lp, new Node(newRX, newRY));
                }
            }
        }
    }

    private boolean validateRange(int x, int y) {
        return x >= 0 && y >= 0 && x < 4 && y < 3 && !(x == 3 && y == 0) && !(x == 3 && y == 2);
    }


    class Node {
        int x;
        int y;

        Node (int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}

// dfs