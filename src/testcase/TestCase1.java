package testcase;
import java.util.*;

public class TestCase1 {

    public static void main(String[] args) {

        List<Node> nodes = new ArrayList<>();

        Node n1 = new Node(1, 2);
        Node n2 = new Node(2, 3);
        Node n3 = new Node(1, 7);

        nodes.add(n1);
        nodes.add(n2);
        nodes.add(n3);
        nodes.add(new Node(11, 2));
        nodes.add(new Node(2, 9));

        nodes.sort(Comparator.comparing((Node n) -> n.y)
                .reversed()
                .thenComparing(n -> n.x));

        for (Node n : nodes) {
            System.out.println("n.x = " + n.x + ", n.y = " + n.y);
        }
    }

    static class Node {
        int x;
        int y;
        Node (int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

}
