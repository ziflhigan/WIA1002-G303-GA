package GameEngine;

import java.util.ArrayList;
import java.util.Random;

public class RegularEngineEasy implements EngineInterface{


    public int[] getMove(char[][] board) {
        ArrayList<int[]> availableMoves = new ArrayList<>();
    
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                if (board[row][col] == '-') {
                    availableMoves.add(new int[]{row, col});
                }
            }
        }
    
        Random random = new Random();
        return !availableMoves.isEmpty() ? availableMoves.get(random.nextInt(availableMoves.size())) : new int[]{0, 0};
    }
    
}
