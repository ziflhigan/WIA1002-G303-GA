package GameEngine;

import java.util.Scanner;
import java.util.Random;

class ReverseEngineEasy extends Engine {
    Random random = new Random();
    private String winner = "draw";

    @Override
    protected boolean getWinner(String turnPrompt, int[][] board, int currentPlayer, Scanner scanner) {
        System.out.println(turnPrompt);

        int row = 0, col = 0;
        while (true) {
            if (currentPlayer == 1) {
                row = getValidInt(scanner, "\nEnter row (0-2): ", 0, 2);
                col = getValidInt(scanner, "Enter col (0-2): ", 0, 2);
            } else {
                while (true) {
                    row = random.nextInt(3);
                    col = random.nextInt(3);

                    if (board[row][col] == 0) {
                        break;
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
                        System.out.println("Computer wins!");
                        winner = "PLayer";
                    } else if (currentPlayer ==2) {
                        System.out.println("Player 1 wins!");
                        winner = "Computer";

                }
                    return true;
                }
                return false;
            }
               
            }

           
    }

    private boolean checkHit(int[][] board, int row, int col) {
        int player = board[row][col];
    
        // Check rows
        int rowCount = 0;
        for (int i = 0; i < 3; i++) {
            if (board[row][i] == player) {
                rowCount++;
            }
        }
        if (rowCount == 3) {
            return true;
        }
    
        // Check columns
        int colCount = 0;
        for (int i = 0; i < 3; i++) {
            if (board[i][col] == player) {
                colCount++;
            }
        }
        if (colCount == 3) {
            return true;
        }
    
        // Check diagonals
        if (row == col) {
            int diagonalCount = 0;
            for (int i = 0; i < 3; i++) {
                if (board[i][i] == player) {
                    diagonalCount++;
                }
            }
            if (diagonalCount == 3) {
                return true;
            }
        }
    
        if (row + col == 2) {
            int diagonalCount = 0;
            for (int i = 0; i < 3; i++) {
                if (board[i][2 - i] == player) {
                    diagonalCount++;
                }
            }
            if (diagonalCount == 3) {
                return true;
            }
        }
    
        return false;
    }

    public String getWinner() {
        return winner;
    }
    
}
