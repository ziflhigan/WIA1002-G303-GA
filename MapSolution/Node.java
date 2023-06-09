import java.util.HashSet;

public class Node {
    int row;
    int col;
    int distance;
    HashSet<String> visited;
    Node parent;
    int stationCount;

    Node(int row, int col, int distance, HashSet<String> visited, Node parent, int stationCount) {
        this.row = row;
        this.col = col;
        this.distance = distance;
        this.visited = visited;
        this.parent = parent;
        this.stationCount = stationCount;
    }
}
