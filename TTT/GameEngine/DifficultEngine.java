package GameEngine;

import java.util.Scanner;

public class DifficultEngine extends Engine {

  public boolean getWinner(String turnPrompt, int[][] A, int playerNumber, Scanner in) {
    System.out.println(turnPrompt);
    int row = 0, col = 0;
    while (true) {
      if (playerNumber == 1) {
        row = GameEngine.getValidInt("Enter row (0-2): ", 0, 2);
        col = GameEngine.getValidInt("Enter col (0-2): ", 0, 2);
      } else {
        // Difficult mode logic
        int bestScore = Integer.MIN_VALUE;
        int bestRow = -1;
        int bestCol = -1;
        boolean validMoveAvailable = false; // Track if valid move is available

        for (int i = 0; i < A.length; i++) {
          for (int j = 0; j < A[i].length; j++) {
            if (A[i][j] == 0) {
              A[i][j] = playerNumber;
              int score = minimax(A, 0, false);
              A[i][j] = 0;

              if (score > bestScore) {
                bestScore = score;
                bestRow = i;
                bestCol = j;
              }
              validMoveAvailable = true; // Mark that a valid move is available
            }
          }
        }

        if (!validMoveAvailable) {
          System.out.println("No more valid moves for the computer.");
          break;
        }

        row = bestRow;
        col = bestCol;
        System.out.printf("Computer chooses [%d,%d]\n", row, col);
        A[row][col] = playerNumber;
      }

      if (GameEngine.isFree(A, row, col)) {
        break;
      }

      if (playerNumber == 1) {
        System.out.printf("[%d,%d] is already filled!\n", row, col);
      }
    }
    return GameEngine.checkHit(A);
  }

  public static int minimax(int[][] board, int depth, boolean isMaximizingPlayer) {
    if (GameEngine.checkHit(board)) {
      if (isMaximizingPlayer)
        return -1;
      else
        return 1;
    } else if (depth == 9) {
      return 0;
    }

    if (isMaximizingPlayer) {
      int bestScore = Integer.MIN_VALUE;
      for (int i = 0; i < board.length; i++) {
        for (int j = 0; j < board[i].length; j++) {
          if (board[i][j] == 0) {
            board[i][j] = 2;
            int score = minimax(board, depth + 1, false);
            board[i][j] = 0;
            bestScore = Math.max(score, bestScore);
          }
        }
      }
      return bestScore;
    } else {
      int bestScore = Integer.MAX_VALUE;
      for (int i = 0; i < board.length; i++) {
        for (int j = 0; j < board[i].length; j++) {
          if (board[i][j] == 0) {
            board[i][j] = 1;
            int score = minimax(board, depth + 1, true);
            board[i][j] = 0;
            bestScore = Math.min(score, bestScore);
          }
        }
      }
      return bestScore;
    }
  }
}
