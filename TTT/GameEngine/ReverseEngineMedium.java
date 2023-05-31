package GameEngine;

import java.util.*;

public class ReverseEngineMedium extends ReverseEngine {
    private static Random random = new Random();
        String winner="draw";    
    
        @Override
        protected boolean getWinner(String turnPrompt, int[][] board, int currentPlayer, Scanner scanner) {
            System.out.println(turnPrompt);
            int row, col;
            while (true) {
                if (currentPlayer == 1) {
                    row = getValidInt(scanner, "Enter row (0-2): ", 0, 4);
                    col = getValidInt(scanner, "Enter col (0-2): ", 0, 4);
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
    
        @Override
        protected boolean checkHit(int[][] board, int row, int col) {
            int player = board[row][col];
        
            // Check horizontal line
            boolean horizontalWin = true;
            for (int c = 0; c < boardSize; c++) {
                if (board[row][c] != player) {
                    horizontalWin = false;
                    break;
                }
            }
            if (horizontalWin) {
                return true;
            }
        
            // Check vertical line
            boolean verticalWin = true;
            for (int r = 0; r < boardSize; r++) {
                if (board[r][col] != player) {
                    verticalWin = false;
                    break;
                }
            }
            if (verticalWin) {
                return true;
            }
        
            // Check diagonal lines
            if (row == col) {
                boolean diagonalWin1 = true;
                for (int i = 0; i < boardSize; i++) {
                    if (board[i][i] != player) {
                        diagonalWin1 = false;
                        break;
                    }
                }
                if (diagonalWin1) {
                    return true;
                }
            }
        
            if (row + col == boardSize - 1) {
                boolean diagonalWin2 = true;
                for (int i = 0; i < boardSize; i++) {
                    if (board[i][boardSize - 1 - i] != player) {
                        diagonalWin2 = false;
                        break;
                    }
                }
                if (diagonalWin2) {
                    return true;
                }
            }
        
            return false;
        }
    
    }
    
