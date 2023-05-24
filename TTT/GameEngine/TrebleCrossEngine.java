package GameEngine;

import java.util.Scanner;

class TrebleCrossEngine extends Engine {
    @Override
    protected boolean getWinner(String turnPrompt, int[][] board, int currentPlayer, Scanner scanner) {
        System.out.println(turnPrompt);
        int row = getValidInt(scanner, "Enter row (0-2): ", 0, 2);
        int col = getValidInt(scanner, "Enter col (0-2): ", 0, 2);

        if (board[row][col] != 0) {
            System.out.printf("[%d,%d] is already filled! Please choose an empty cell.\n", row, col);
            return false;
        }

        board[row][col] = currentPlayer;
        return checkHit(board, row, col);
    }

    private boolean checkHit(int[][] board, int row, int col) {
        int player = board[row][col];

        // Check row
        for (int c = 0; c < 3; c++) {
            if (board[row][c] != player) {
                break;
            }
            if (c == 2) {
                return true;
            }
        }

        // Check column
        for (int r = 0; r < 3; r++) {
            if (board[r][col] != player) {
                break;
            }
            if (r == 2) {
                return true;
            }
        }

        // Check diagonal (top-left to bottom-right)
        if (row == col) {
            for (int i = 0; i < 3; i++) {
                if (board[i][i] != player) {
                    break;
                }
                if (i == 2) {
                    return true;
                }
            }
        }

        // Check diagonal (top-right to bottom-left)
        if (row + col == 2) {
            for (int i = 0; i < 3; i++) {
                if (board[i][2 - i] != player) {
                    break;
                }
                if (i == 2) {
                    return true;
                }
            }
        }

        return false;
    }
}