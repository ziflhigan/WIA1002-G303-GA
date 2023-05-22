package SaveGame;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SaveGame {
    public static void save(int[][] grid) {
        String fileName = generateFileName();
        try (PrintWriter writer = new PrintWriter(new FileWriter("savedGames/" + fileName))) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    writer.write(grid[i][j] + " ");
                }
                writer.write("\n");
            }
            System.out.println("Game saved successfully!");
        } catch (IOException e) {
            System.out.println("Error saving the game.");
        }
    }

    private static String generateFileName() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String timestamp = dateFormat.format(new Date());
        return "game_" + timestamp + ".txt";
    }
}
