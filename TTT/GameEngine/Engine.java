package GameEngine;

import java.util.Scanner;
import SaveGame.SaveGame;

public abstract class Engine {
  protected int[][] board;
  protected int currentPlayer;
  protected String winner;
  protected int boardSize=3;


  public void playGame(Scanner scanner) {
    initializeBoard();
    currentPlayer = 1;

    int i = 1;
    winner= "draw";

    while (true) {
        printBoard();
        String turnPrompt = (currentPlayer == 1) ? "Player 1 turn" : "Computer's turn";
        boolean gameFinished = getWinner(turnPrompt, board, currentPlayer, scanner);

        if (gameFinished) {
            printBoard();
            break;
        }

        if (isBoardFull()) {
            printBoard();
            System.out.println("It's a draw!");
            break;
        }

        if (currentPlayer == 1) {
            currentPlayer = 2;
        } else {
            currentPlayer = 1;
        }

      
        i++;
    }

    // Prompt for command (save/exit)
    while (true) {
        System.out.print("Input command (0= save/ 1= exit): ");
       int command = scanner.nextInt();

        if (command == 0) {
            // Save game
            SaveGame.save(board, boardSize);
            break;
        } else if (command==1) {
            break;
        } else {
            System.out.println("Invalid command! Please try again.");
        }
    }



    scanner.close();
}



  protected abstract boolean getWinner(String turnPrompt, int[][] board, int currentPlayer, Scanner scanner);



protected void initializeBoard() {
      // Initialize an empty board
      int size = getBoardSize();
      board = new int[size][size];
  }

  protected void printBoard() {
      int size = board.length;
      System.out.println();
      for (int i = 0; i < size; i++) {
          printRowSeparator(size);
          printRow(board[i]);
      }
      printRowSeparator(size);
  }

  protected void printRowSeparator(int size) {
      System.out.print("-");
      for (int i = 0; i < size; i++) {
          System.out.print("----");
      }
      System.out.println();
  }

  protected void printRow(int[] row) {
      int size = row.length;
      for (int i = 0; i < size; i++) {
          String mark = (row[i] == 1) ? "X" : ((row[i] == 2) ? "O" : " ");
          System.out.print("| " + mark + " ");
      }
      System.out.println("|");
  }

  protected boolean isBoardFull() {
      int size = board.length;
      for (int i = 0; i < size; i++) {
          for (int j = 0; j < size; j++) {
              if (board[i][j] == 0) {
                  return false;
              }
          }
      }
      return true;
  }

  protected int getValidInt(Scanner scanner, String prompt, int min, int max) {
      int value;
      while (true) {
          System.out.print(prompt);
          if (scanner.hasNextInt()) {
              value = scanner.nextInt();
              if (value >= min && value <= max) {
                  break;
              }
          } else {
              scanner.next(); // Clear invalid input
          }
          System.out.println("Invalid input. Please enter an integer between " + min + " and " + max + ".");
      }
      return value;
  }

  protected int getBoardSize() {
      return 3; // Default size for reverse Tic-Tac-Toe
  }
  
  public void setBoardSize(int bSize){
    boardSize=bSize;
  }

  public String getWinner() {
    return winner;
}
}