import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class MapDecipher {
    final private int width = 10, height = 20;
    private int[][] mapPiece1, mapPiece2, mapPiece3, mapPiece4, completeMap;

    /**
     * {0, 1}, {1, 0}, {0, -1}, {-1, 0}
     * move down, move right, move up, move left
     */
    private final int[][] DIRECTIONS = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

    /**
     * @param imagePath the path of the image where we stored in local
     * @return rangeConverted
     * @throws IOException
     * returns the converted pixel value of the grayscale image to 0-3
     */
    public int[][] imageConverter(String imagePath) throws IOException{
        File imageFile = new File(imagePath);
        BufferedImage image = ImageIO.read(imageFile);

        int[][] rangeConverted = new int[height][width];

        for (int y = 0; y < height; y++ ){
            for (int x = 0; x < width; x++){
                int pixelValue = image.getRGB(x,y) & 0xFF;
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
    public void imageInputAndConvert(){
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
     * @param array
     * print out the elements of 2D arrays
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
     * @param mP map pieces
     * @param currentX current coordinate in x-direction
     * @param currentY current coordinate in y-direction
     * @param visitedStations the number of visited stations so far
     * @return 0 or 1
     *  It checks for the base conditions (out of bounds, obstacles, or previously visited cells)
     *  and returns 0 if any of them is true. If it reaches a station, it increments the "visitedCounter" counter.
     *  If it reaches exactly 3 stations, it returns 1, indicating a valid path.
     */
    public int recursiveDFS(int[][] mP, int currentX, int currentY, int visitedStations, boolean[][] visited) {
        if (currentX < 0 || currentX >= mP[0].length || currentY < 0 || currentY >= mP.length
                || mP[currentY][currentX] == 1)
            return 0;

        if (visited[currentY][currentX])
            return 0;

        if (mP[currentY][currentX] == 2)
            visitedStations++;

        if (visitedStations == 3 && mP[currentY][currentX] == 3)
            return 1;

        visited[currentY][currentX] = true;
        int pathCount = 0;

        for (int[] direction : DIRECTIONS){
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
     *             mapPiece3 mapPiece4]
     * @return the complete map in 2D array
     * It also changes the 'final destinations' to 'obstacles' as per required
     */
    public int[][] completeMap(){
        int[][] copyMapPiece1 = mapPiece1, copyMapPiece2 = mapPiece2,
                copyMapPiece3 = mapPiece3, copyMapPiece4 = mapPiece4;

        copyMapPiece1[19][9] = 1;
        copyMapPiece2[19][9] = 1;
        copyMapPiece3[19][9] = 1;

        completeMap = new int[40][20];

        for (int i = 0; i < 20; i++){
            for (int j = 0; j < 10; j++){
                completeMap[i][j] = copyMapPiece1[i][j];
            }
        }

        for (int i = 0, y = 0; i < 20; i++, y++){
            for (int j = 10, x = 0; j < 20; j++, x++){
                completeMap[i][j] = copyMapPiece2[y][x];
            }
        }

        for (int i = 20, y = 0; i < 40; i++, y++){
            for (int j = 0, x = 0; j < 10; j++, x++){
                completeMap[i][j] = copyMapPiece3[y][x];
            }
        }

        for (int i = 20, y = 0; i < 40; i++, y++){
            for (int j = 10, x = 0; j < 20; j++, x++){
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

    public int[][] getCompleteMap() {
        return completeMap;
    }
}

class ImageInput {
    public static void main(String[] args) {
        MapDecipher map = new MapDecipher();

        map.imageInputAndConvert();

        System.out.println("Map Piece 1:");
        map.display2DArray(map.getMapPiece1());
        System.out.println("\nMap Piece 2:");
        map.display2DArray(map.getMapPiece2());
        System.out.println("\nMap Piece 3:");
        map.display2DArray(map.getMapPiece3());
        System.out.println("\nMap Piece 4:");
        map.display2DArray(map.getMapPiece4());

        int pathsMapPiece1 = map.countPaths(map.getMapPiece1());
        int pathsMapPiece2 = map.countPaths(map.getMapPiece2());
        int pathsMapPiece3 = map.countPaths(map.getMapPiece3());
        int pathsMapPiece4 = map.countPaths(map.getMapPiece4());

        System.out.println("Number of paths for Map Piece 1: " + pathsMapPiece1);
        System.out.println("Number of paths for Map Piece 2: " + pathsMapPiece2);
        System.out.println("Number of paths for Map Piece 3: " + pathsMapPiece3);
        System.out.println("Number of paths for Map Piece 4: " + pathsMapPiece4);

        map.display2DArray(map.completeMap());

    }
}