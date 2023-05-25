package GameEngine;

import java.util.Scanner;
import java.util.Random;

class RegularEngine extends Engine {
    private String winner = "Draw";

    private static Random random = new Random();

    public RegularEngine(int boardSize) {
        this.boardSize = boardSize;
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

     public void printRowSeparator(int size) {
        System.out.print("-");
        for (int i = 0; i < size; i++) {
            System.out.print("-----");
        }
        System.out.println();
    }

    public void printRow(int[] row) {
        int size = row.length;
        for (int i = 0; i < size; i++) {
            String mark = (row[i] == 1) ? "X" : ((row[i] == 2) ? "O" : " ");
            System.out.print("| " + mark + " ");
        }
        System.out.println("|");
    }

    @Override
    protected int getBoardSize() {
        return boardSize;
    }

    protected boolean checkHit(int[][] board, int row, int col) {
        int player = board[row][col];

        // Check row
        int count = 0;
        for (int c = 0; c < boardSize; c++) {
            if (board[row][c] == player) {
                count++;
            } else {
                count = 0;
            }

            if (count == 5) {
                return true;
            }
        }

        // Check column
        count = 0;
        for (int r = 0; r < boardSize; r++) {
            if (board[r][col] == player) {
                count++;
            } else {
                count = 0;
            }

            if (count == 5) {
                return true;
            }
        }

        // Check diagonal (top-left to bottom-right)
        count = 0;
        int startRow = Math.max(row - col, 0);
        int startCol = Math.max(col - row, 0);
        for (int i = 0; i < Math.min(boardSize - startRow, boardSize - startCol); i++) {
            if (board[startRow + i][startCol + i] == player) {
                count++;
            } else {
                count = 0;
            }

            if (count == 5) {
                return true;
            }
        }

        // Check diagonal (top-right to bottom-left)
        count = 0;
        startRow = Math.max(row - (boardSize - 1 - col), 0);
        startCol = Math.min(col + row, boardSize - 1);
        for (int i = 0; i < Math.min(boardSize - startRow, startCol + 1); i++) {
            if (board[startRow + i][startCol - i] == player) {
                count++;
            } else {
                count = 0;
            }

            if (count == 5) {
                return true;
            }
        }

        return false;
    }
    public String getWinner() {
        return winner;
    }
    

}