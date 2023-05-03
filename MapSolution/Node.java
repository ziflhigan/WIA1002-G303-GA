import java.util.HashSet;

public class Node {
    int row;
    int col;
    int distance;
    HashSet<String> visited;
    Node parent;

    Node(int row, int col, int distance, HashSet<String> visited, Node parent) {
        this.row = row;
        this.col = col;
        this.distance = distance;
        this.visited = visited;
        this.parent = parent;
    }
}