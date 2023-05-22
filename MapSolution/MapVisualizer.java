import javax.swing.*;
import java.awt.*;
import java.util.List;

class MapPanel extends JPanel {
    private final int[][] grid;
    private final List<List<String>> paths;

    MapPanel(int[][] grid, List<List<String>> paths) {
        this.grid = grid;
        this.paths = paths;
    }

    private void drawArrow(Graphics g, int x1, int y1, int x2, int y2) {
        int dx = x2 - x1;
        int dy = y2 - y1;
        double angle = Math.atan2(dy, dx);
        int arrowSize = 10;

        // Draw the line
        g.drawLine(x1, y1, x2, y2);

        // Draw the arrowhead
        g.drawLine(x2, y2, x1 + (int) (arrowSize * Math.cos(angle - Math.PI / 6)),
                y1 + (int) (arrowSize * Math.sin(angle - Math.PI / 6)));
        g.drawLine(x2, y2, x1 + (int) (arrowSize * Math.cos(angle + Math.PI / 6)),
                y1 + (int) (arrowSize * Math.sin(angle + Math.PI / 6)));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int cellSize = Math.min(getWidth() / grid[0].length, getHeight() / grid.length);

        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                int x = col * cellSize;
                int y = row * cellSize;

                switch (grid[row][col]) {
                    case 0 -> g.setColor(Color.GREEN);
                    case 1 -> g.setColor(Color.PINK);
                    case 2 -> g.setColor(Color.YELLOW);
                    case 3 -> g.setColor(Color.BLUE);
                }
                g.fillRect(x, y, cellSize, cellSize);
                g.setColor(Color.GRAY);
                g.drawRect(x, y, cellSize, cellSize);

                g.setColor(Color.BLACK);
                g.drawString(Integer.toString(grid[row][col]), x + cellSize / 2, y + cellSize / 2);
            }
        }

        g.setColor(Color.BLACK);
        for (List<String> path : paths) {
            int row = 0;
            int col = 0;
            for (String direction : path) {
                int prevRow = row;
                int prevCol = col;
                switch (direction) {
                    case "Up" -> row--;
                    case "Down" -> row++;
                    case "Left" -> col--;
                    case "Right" -> col++;
                }
                int x1 = prevCol * cellSize + cellSize / 2;
                int y1 = prevRow * cellSize + cellSize / 2;
                int x2 = col * cellSize + cellSize / 2;
                int y2 = row * cellSize + cellSize / 2;
                drawArrow(g, x1, y1, x2, y2);
            }
        }
    }
}
public class MapVisualizer {
    public static void main(String[] args) {

        MapDecipher comMap = new MapDecipher();
        comMap.imageInputAndConvert();

        List<List<String>> allShortestPaths = BFSAllShortestPaths.findAllShortestPaths(comMap.completeMap());

        System.out.println("The minimum steps required for a shortest path is : " + allShortestPaths.get(0).size());
        System.out.println("There are total " + allShortestPaths.size() + " possible shortest paths");
        System.out.println("All possible shortest paths:");
        for (List<String> path : allShortestPaths) {
            System.out.println(path);
        }

        JFrame frame = new JFrame("Map Visualizer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(450, 850);
        frame.setLocationRelativeTo(null);
        frame.setContentPane(new MapPanel(comMap.completeMap(), allShortestPaths));
        frame.setVisible(true);

        System.out.println(allShortestPaths.size());
    }
}
