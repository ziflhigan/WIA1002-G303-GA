import java.util.*;

public class BFSAllShortestPaths {
    private static final int[][] DIRECTIONS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    private static final String[] DIR_NAMES = {"Up", "Down", "Left", "Right"};

    public static List<List<String>> findAllShortestPaths(int[][] map) {
        List<List<String>> shortestPaths = new ArrayList<>();
        Queue<Node> queue = new LinkedList<>();
        HashSet<String> initialVisited = new HashSet<>();
        initialVisited.add("0,0");
        queue.offer(new Node(0, 0, 0, initialVisited, null));

        int shortestDistance = Integer.MAX_VALUE;

        while (!queue.isEmpty()) {
            Node current = queue.poll();

            if (current.distance > shortestDistance) {
                continue;
            }

            if (map[current.row][current.col] == 3) {
                if (current.distance < shortestDistance) {
                    shortestPaths.clear();
                    shortestDistance = current.distance;
                }

                List<String> path = new ArrayList<>();
                Node node = current;
                while (node.parent != null) {
                    // Calculate the distance from parent to child to identify its direction name
                    int dirRow = node.row - node.parent.row;
                    int dirCol = node.col - node.parent.col;
                    for (int i = 0; i < DIRECTIONS.length; i++) {
                        if (DIRECTIONS[i][0] == dirRow && DIRECTIONS[i][1] == dirCol) {
                            path.add(DIR_NAMES[i]);
                            break;
                        }
                    }
                    node = node.parent;
                }
                Collections.reverse(path);
                shortestPaths.add(path);
                continue;
            }

            for (int i = 0; i < DIRECTIONS.length; i++) {
                int newRow = current.row + DIRECTIONS[i][0];
                int newCol = current.col + DIRECTIONS[i][1];
                String key = newRow + "," + newCol;

                if (isValid(map, newRow, newCol) && !current.visited.contains(key)) {
                    HashSet<String> newVisited = new HashSet<>(current.visited);
                    newVisited.add(key);
                    queue.offer(new Node(newRow, newCol, current.distance + 1, newVisited, current));
                }
            }
        }
        return shortestPaths;
    }

    private static boolean isValid(int[][] map, int height, int width) {
        if (height < 0 || height >= map.length || width < 0 || width >= map[0].length) {
            return false;
        }
        int value = map[height][width];
        return value != 1;
    }
}
