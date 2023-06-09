import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.*;

public class MapDecipher {
    private final int width = 10, height = 20;
    private int[][] mapPiece1;
    private int[][] mapPiece2;
    private int[][] mapPiece3;
    private int[][] mapPiece4;

    /*
     * {0, 1}, {1, 0}, {0, -1}, {-1, 0}
     * move down, move right, move up, move left
     */
    private final int[][] Directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

    /**
     * @param imagePath the path of the image where we stored in local
     * @return rangeConverted
     * @throws IOException returns the converted pixel value of the grayscale image to 0-3
     */
    public int[][] imageConverter(String imagePath) throws IOException {
        File imageFile = new File(imagePath);
        BufferedImage image = ImageIO.read(imageFile);

        int[][] rangeConverted = new int[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixelValue = image.getRGB(x, y) & 0xFF;
                int convertedValue = pixelValue / 64;
                rangeConverted[y][x] = convertedValue;
            }
        }
        return rangeConverted;
    }

    /**
     * reads the image pieces and stores the converted pixel value by calling imageConverter method
     * prints out the results in the form of 2D array
     */
    public void imageInputAndConvert() {
        try {
            mapPiece1 = imageConverter("Pieces of Map\\image 1.png");
            mapPiece2 = imageConverter("Pieces of Map\\image 2.png");
            mapPiece3 = imageConverter("Pieces of Map\\image 3.png");
            mapPiece4 = imageConverter("Pieces of Map\\image 4.png");

        } catch (IOException e) {
            System.err.println("Error reading image file");
            e.printStackTrace();
        }
    }

    /**
     * @param array print out the elements of 2D arrays
     */
    public void display2DArray(int[][] array) {
        for (int[] row : array) {
            for (int element : row) {
                System.out.print(element + " ");
            }
            System.out.println();
        }
    }

    /**
     * @param mP              map pieces
     * @param currentX        current coordinate in x-direction
     * @param currentY        current coordinate in y-direction
     * @param visitedStations the number of visited stations so far
     * @return 0 or 1
     * It checks for the base conditions (out of bounds, obstacles, or previously visited cells)
     * and returns 0 if any of them is true. If it reaches a station, it increments the "visitedCounter" counter.
     * If it reaches exactly 4 stations, it returns 1, indicating a valid path.
     */
    public int recursiveDFS(int[][] mP, int currentX, int currentY, int visitedStations, boolean[][] visited) {
        if (currentX < 0 || currentX >= mP[0].length || currentY < 0 || currentY >= mP.length
                || mP[currentY][currentX] == 1 || visitedStations > 4)
            return 0;

        if (visited[currentY][currentX])
            return 0;

        if (mP[currentY][currentX] == 2)
            visitedStations++;

        if (visitedStations == 4 && mP[currentY][currentX] == 3)
            return 1;

        visited[currentY][currentX] = true;
        int pathCount = 0;

        for (int[] direction : Directions) {
            int newX = currentX + direction[0];
            int newY = currentY + direction[1];
            pathCount += recursiveDFS(mP, newX, newY, visitedStations, visited);
        }

        visited[currentY][currentX] = false;

        if (mP[currentY][currentX] == 2)
            visitedStations--;

        return pathCount;
    }

    public int countPaths(int[][] mapPiece) {
        boolean[][] visited = new boolean[mapPiece.length][mapPiece[0].length];
        return recursiveDFS(mapPiece, 0, 0, 0, visited);
    }

    /**
     * Merge the map pieces into a complete map according to the map template
     * which is : [mapPiece1 mapPiece2
     * mapPiece3 mapPiece4]
     *
     * @return the complete map in 2D array
     * It also changes the 'final destinations' to 'obstacles' as per required
     */
    public int[][] completeMap() {
        int[][] copyMapPiece1 = new int[20][10], copyMapPiece2 = new int[20][10],
                copyMapPiece3 = new int[20][10], copyMapPiece4 = new int[20][10];

        for (int i = 0; i < 20; i++){
            for (int j = 0; j < 10 ; j++){
                copyMapPiece1[i][j] = mapPiece1[i][j];
            }
        }

        for (int i = 0; i < 20; i++){
            for (int j = 0; j < 10 ; j++){
                copyMapPiece2[i][j] = mapPiece2[i][j];
            }
        }

        for (int i = 0; i < 20; i++){
            for (int j = 0; j < 10 ; j++){
                copyMapPiece3[i][j] = mapPiece3[i][j];
            }
        }

        for (int i = 0; i < 20; i++){
            for (int j = 0; j < 10 ; j++){
                copyMapPiece4[i][j] = mapPiece4[i][j];
            }
        }

        copyMapPiece1[19][9] = 1;
        copyMapPiece2[19][9] = 1;
        copyMapPiece3[19][9] = 1;

        int[][] completeMap = new int[40][20];

        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 10; j++) {
                completeMap[i][j] = copyMapPiece1[i][j];
            }
        }

        for (int i = 0, y = 0; i < 20; i++, y++) {
            for (int j = 10, x = 0; j < 20; j++, x++) {
                completeMap[i][j] = copyMapPiece2[y][x];
            }
        }

        for (int i = 20, y = 0; i < 40; i++, y++) {
            for (int j = 0, x = 0; j < 10; j++, x++) {
                completeMap[i][j] = copyMapPiece3[y][x];
            }
        }

        for (int i = 20, y = 0; i < 40; i++, y++) {
            for (int j = 10, x = 0; j < 20; j++, x++) {
                completeMap[i][j] = copyMapPiece4[y][x];
            }
        }

        return completeMap;
    }

    public int[][] getMapPiece1() {
        return mapPiece1;
    }

    public int[][] getMapPiece2() {
        return mapPiece2;
    }

    public int[][] getMapPiece3() {
        return mapPiece3;
    }

    public int[][] getMapPiece4() {
        return mapPiece4;
    }

    /**
     * This method uses breadth first search, which searches the path level by level,
     * it has a queue that stores a sequence of integers containing the coordinates, number of visited stations,
     * and the index of visited nodes in the visitedList
     * it has a 2D array startVisited that stores boolean value whether the particular node has been visited or not.
     * it also has an array list named visitedList that stores the visited 2D arrays, because for each possible direction it has a unique 'startVisited' array.
     *
     * @param mapPiece the map piece
     * @return the number of possible paths found
     */
    public int countPathsBFS(int[][] mapPiece) {
        Queue<int[]> queue = new LinkedList<>();
        boolean[][] startVisited = new boolean[mapPiece.length][mapPiece[0].length];
        startVisited[0][0] = true;
        queue.offer(new int[]{0, 0, 0, 0}); // starting node: x, y, visitedStations, visited[][] index

        List<boolean[][]> visitedList = new ArrayList<>();
        visitedList.add(startVisited);

        int pathCount = 0;
        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int currentX = current[0];
            int currentY = current[1];
            int visitedStations = current[2];
            int visitedIndex = current[3];
            boolean[][] currentVisited = visitedList.get(visitedIndex);

            if (mapPiece[currentY][currentX] == 3 && visitedStations == 3) {
                pathCount++;
                continue;
            }

            for (int[] direction : Directions) {
                int newX = currentX + direction[0];
                int newY = currentY + direction[1];

                if (newX < 0 || newX >= mapPiece[0].length || newY < 0 || newY >= mapPiece.length
                        || mapPiece[newY][newX] == 1 || currentVisited[newY][newX])
                    continue;

                int newVisitedStations = visitedStations;
                if (mapPiece[newY][newX] == 2 && visitedStations < 3) {
                    newVisitedStations++;
                } else if (mapPiece[newY][newX] == 2) {
                    continue; // Skip visiting the station cell if the number of visited stations is already 3
                }

                // Skip visiting the destination cell if the number of visited stations is less than 3
                if (mapPiece[newY][newX] == 3 && newVisitedStations < 3)
                    continue;

                // Create a new visited array for the next level
                boolean[][] newVisited = new boolean[mapPiece.length][mapPiece[0].length];
                for (int i = 0; i < mapPiece.length; i++) {
                    System.arraycopy(currentVisited[i], 0, newVisited[i], 0, mapPiece[0].length);
                }
                newVisited[newY][newX] = true;

                // Add the new visited array to the list and enqueue the new position with the new visited array's index
                visitedList.add(newVisited);
                int newVisitedIndex = visitedList.size() - 1;
                queue.offer(new int[]{newX, newY, newVisitedStations, newVisitedIndex});
            }
        }
        return pathCount;
    }

}