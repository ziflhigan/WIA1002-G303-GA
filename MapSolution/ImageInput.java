public class ImageInput {
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

    }
}
