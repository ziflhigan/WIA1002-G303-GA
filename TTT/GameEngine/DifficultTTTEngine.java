package GameEngine;

import java.util.Scanner;

public class DifficultTTTEngine extends RegularEngine {
    
    String winner="draw";    

    public DifficultTTTEngine(int boardSize) {
        super(boardSize);
    }

    @Override
    protected boolean getWinner(String turnPrompt, int[][] board, int currentPlayer, Scanner scanner) {
        System.out.println(turnPrompt);
        int row, col;
        while (true) {

        if (currentPlayer == 1) {
            row = getValidInt(scanner, "Enter row (0-4): ", 0, 4);
            col = getValidInt(scanner, "Enter col (0-4): ", 0, 4);
      } else {
            // Perform difficult-level logic for computer's move
            int[] bestMove = minimax(board, currentPlayer);
            row = bestMove[0];
            col = bestMove[1];

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
                    winner = "Player 1";
                } else {
                    System.out.println("\n\nComputer wins!");
                    winner = "Computer";
                }
                return true;
            }
            return false;
        }
    } 
}

    private int[] minimax(int[][] board, int currentPlayer) {
        int[] bestMove = new int[2];
        int bestScore = Integer.MIN_VALUE;

        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                if (board[row][col] == 0) {
                    board[row][col] = currentPlayer;
                    int score = minimaxHelper(board, currentPlayer, 0, false);
                    board[row][col] = 0;

                    if (score > bestScore) {
                        bestScore = score;
                        bestMove[0] = row;
                        bestMove[1] = col;
                    }
                }
            }
        }

        return bestMove;
    }

    private int minimaxHelper(int[][] board, int currentPlayer, int depth, boolean isMaximizingPlayer) {
        int opponent = (currentPlayer == 1) ? 2 : 1;

        if (checkWin(board, currentPlayer)) {
            return 10 - depth;
        } else if (checkWin(board, opponent)) {
            return depth - 10;
        } 

        if (isMaximizingPlayer) {
            int bestScore = Integer.MIN_VALUE;

            for (int row = 0; row < boardSize; row++) {
                for (int col = 0; col < boardSize; col++) {
                    if (board[row][col] == 0) {
                        board[row][col] = currentPlayer;
                        int score = minimaxHelper(board, currentPlayer, depth + 1, false);
                        board[row][col] = 0;

                        bestScore = Math.max(score, bestScore);
                    }
                }
            }

            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;

            for (int row = 0; row < boardSize; row++) {
                for (int col = 0; col < boardSize; col++) {
                    if (board[row][col] == 0) {
                        board[row][col] = opponent;
                        int score = minimaxHelper(board, currentPlayer, depth + 1, true);
                        board[row][col] = 0;

                        bestScore = Math.min(score, bestScore);
                    }
                }
            }

            return bestScore;
        }
    }

    private boolean checkWin(int[][] board, int player) {
        // Check horizontal lines
        for (int row = 0; row < boardSize; row++) {
            boolean win = true;
            for (int col = 0; col < boardSize; col++) {
                if (board[row][col] != player) {
                    win = false;
                    break;
                }
            }
            if (win) {
                return true;
            }
        }

        // Check vertical lines
        for (int col = 0; col < boardSize; col++) {
            boolean win = true;
            for (int row = 0; row < boardSize; row++) {
                if (board[row][col] != player) {
                    win = false;
                    break;
                }
            }
            if (win) {
                return true;
            }
        }

        // Check diagonal lines
        boolean win = true;
        for (int i = 0; i < boardSize; i++) {
            if (board[i][i] != player) {
                win = false;
                break;
            }
        }
        if (win) {
            return true;
        }

        win = true;
        for (int i = 0; i < boardSize; i++) {
            if (board[i][boardSize - 1 - i] != player) {
                win = false;
                break;
            }
        }
        if (win) {
            return true;
        }

        return false;
    }

}
