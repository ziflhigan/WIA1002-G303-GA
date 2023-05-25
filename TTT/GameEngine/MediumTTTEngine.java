package GameEngine;

import java.util.Random;
import java.util.Scanner;

public class MediumTTTEngine extends RegularEngine {
private static Random random = new Random();
    String winner="draw";    



    public MediumTTTEngine(int boardSize) {
        super(boardSize);
    }

    @Override
    protected boolean getWinner(String turnPrompt, int[][] board, int currentPlayer, Scanner scanner) {
        System.out.println(turnPrompt);
        int row, col;
        while (true){
        if (currentPlayer == 1) {
              row = getValidInt(scanner, "Enter row (0-4): ", 0, 4);
             col = getValidInt(scanner, "Enter col (0-4): ", 0, 4);
         } else {
            // Perform medium-level logic for computer's move
            boolean moveMade = false;
            row = 0;
            col = 0;

            // Check if a winning move is available
            for (int r = 0; r < boardSize; r++) {
                for (int c = 0; c < boardSize; c++) {
                    if (board[r][c] == 0) {
                        board[r][c] = currentPlayer;
                        if (checkHit(board, r, c)) {
                            row = r;
                            col = c;
                            moveMade = true;
                            break;
                        }
                        board[r][c] = 0;
                    }
                }
                if (moveMade) {
                    break;
                }
            }

            // If no winning move is available, make a random move
            if (!moveMade) {
                while (true) {
                    row = random.nextInt(boardSize);
                    col = random.nextInt(boardSize);
                    if (board[row][col] == 0) {
                        break;
                    }
                }
            }

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
