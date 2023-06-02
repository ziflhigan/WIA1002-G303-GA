package GameEngine;

import java.util.ArrayList;
import java.util.Random;

public class RegularEngineMedium implements EngineInterface {

    private char[][] copyBoard(char[][] original) {
        char[][] copy = new char[5][5];

        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 3; col++) {
                copy[row][col] = original[row][col];
            }
        }

        return copy;
    }

    private int evaluateBoard(char[][] board) {
        int score = 0;

        // Check rows and columns
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 3; j++) {
                // Rows
                if (board[i][j] == board[i][j + 1] && board[i][j] == board[i][j + 2]) {
                    if (board[i][j] == 'O') {
                        score = 1;
                        break;
                    } else if (board[i][j] == 'X') {
                        score = -1;
                        break;
                    }
                }

                // Columns
                if (board[j][i] == board[j + 1][i] && board[j][i] == board[j + 2][i]) {
                    if (board[j][i] == 'O') {
                        score = 1;
                        break;
                    } else if (board[j][i] == 'X') {
                        score = -1;
                        break;
                    }
                }
            }
        }

        // Check diagonals
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == board[i + 1][j + 1] && board[i][j] == board[i + 2][j + 2]) {
                    if (board[i][j] == 'O') {
                        score = 1;
                        break;
                    } else if (board[i][j] == 'X') {
                        score = -1;
                        break;
                    }
                }

                if (board[i][4 - j] == board[i + 1][3 - j] && board[i][4 - j] == board[i + 2][2 - j]) {
                    if (board[i][4 - j] == 'O') {
                        score = 1;
                        break;
                    } else if (board[i][4 - j] == 'X') {
                        score = -1;
                        break;
                    }
                }
            }
        }

        return score;
    }

    private int minimax(char[][] board, int depth, boolean isMaximizingPlayer) {
        int score = evaluateBoard(board);

        if (score != 0 || depth == 0) {
            return score;
        }

        if (isMaximizingPlayer) {
            int bestScore = Integer.MIN_VALUE;

            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    if (board[row][col] == '-') {
                        board[row][col] = 'O';
                        int currentScore = minimax(board, depth - 1, false);
                        board[row][col] = '-';
                        bestScore = Math.max(bestScore, currentScore);
                    }
                }
            }

            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;

            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    if (board[row][col] == '-') {
                        board[row][col] = 'X';
                        int currentScore = minimax(board, depth - 1, true);
                        board[row][col] = '-';
                        bestScore = Math.min(bestScore, currentScore);
                    }
                }
            }

            return bestScore;
        }
    }

    public int[] getMove(char[][] board) {
        int bestScore = Integer.MIN_VALUE;
        ArrayList<int[]> bestMoves = new ArrayList<>();

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (board[row][col] == '-') {
                    char[][] copiedBoard = copyBoard(board);
                    copiedBoard[row][col] = 'O';
                    int currentScore = minimax(copiedBoard, 4, false);

                    if (currentScore > bestScore) {
                        bestScore = currentScore;
                        bestMoves.clear();
                        bestMoves.add(new int[]{row, col});
                    } else if (currentScore == bestScore) {
                        bestMoves.add(new int[]{row, col});
                    }
                }
            }
        }

        Random random = new Random();
        return !bestMoves.isEmpty() ? bestMoves.get(random.nextInt(bestMoves.size())) : new int[]{0, 0};
    }
}
