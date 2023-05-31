package GameEngine;

import java.util.Scanner;

public class ReverseEngine extends Engine {
    private String winner = "Draw";

    protected boolean getWinner(String turnPrompt, int[][] board, int currentPlayer, Scanner scanner) {
        return true;

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

            if (count == 3) {
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

            if (count == 3) {
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

            if (count == 3) {
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

            if (count == 3) {
                return true;
            }
        }

        return false;
    }
    
    public String getWinner() {
        return winner;
    }
    

}