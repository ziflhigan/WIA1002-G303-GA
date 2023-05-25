package GameEngine;

import java.util.Random;
import java.util.Scanner;

public class EasyTTTEngine extends RegularEngine {
    private static Random random = new Random();
    String winner="draw";

    public EasyTTTEngine(int boardSize) {
        super(boardSize);
    }

    protected boolean getWinner(String turnPrompt, int[][] board, int currentPlayer, Scanner scanner) {
       
        System.out.println(turnPrompt);
        int row = 0, col = 0;
        while (true) {

        if (currentPlayer == 1) {
             row = getValidInt(scanner, "Enter row (0-4): ", 0, 4);
             col = getValidInt(scanner, "Enter col (0-4): ", 0, 4);

        } else {
                 row = random.nextInt(5);
                 col = random.nextInt(5);

                System.out.printf("\nComputer chooses [%d,%d]\n", row, col);
            }

            if (board[row][col] != 0) {
                System.out.printf("[%d,%d] is already filled! Please choose an empty cell.\n", row, col);
            } else {
                board[row][col] = currentPlayer;
                boolean isWinner = checkHit(board, row, col);
                
                if (isWinner) {
                    if (currentPlayer == 1) {
                        System.out.println("\n\nPlayer 1 wins!");
                        winner ="Player 1";
                        
                    } else {
                        System.out.println("\n\nComputer wins!");
                        winner="Computer";
                    }
                    return true;
                }
                return false;
            }
        }

    }
}
