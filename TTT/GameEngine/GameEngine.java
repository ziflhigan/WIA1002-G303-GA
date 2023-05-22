package GameEngine;
import java.util.Scanner;

import SaveGame.SaveGame;

public class GameEngine {

  public static int getValidInt(String prompt, int min, int max) {
    Scanner in = new Scanner(System.in);

    while (true) {
      System.out.print(prompt);
      String input = in.nextLine();
      int num = 0;
      try {
        num = Integer.parseInt(input);
      } catch (NumberFormatException e) {
        System.out.println("Invalid input! Please enter a valid integer.");
        continue;
      }

      if (num < min || num > max) {
        System.out.printf("Integer must be between %d and %d.%n", min, max);
        continue;
      }

      return num;
    }
  }

  public static boolean checkRows(int[][] A) {
    for (int i = 0; i < A.length; i++) {
      if ((A[i][0] == A[i][1]) && (A[i][1] == A[i][2]) && A[i][0] != 0)
        return true;
    }
    return false;
  }

  public static boolean checkCols(int[][] A) {
    for (int i = 0; i < A[0].length; i++) {
      if ((A[0][i] == A[1][i]) && (A[1][i] == A[2][i]) && A[0][i] != 0)
        return true;
    }
    return false;
  }

  public static boolean checkDiags(int[][] A) {
    if ((A[0][0] == A[1][1]) && (A[1][1] == A[2][2]) && A[0][0] != 0)
      return true;
    else if ((A[0][2] == A[1][1]) && (A[1][1] == A[2][0]) && A[1][1] != 0)
      return true;
    else
      return false;
  }

  public static boolean checkHit(int[][] A) {

    if (checkRows(A) || checkCols(A) || checkDiags(A))
      return true;
    else
      return false;
  }

  public static boolean isFree(int[][] A, int row, int col) {
    if (A[row][col] == 0)
      return true;
    else
      return false;
  }

  public static void printBoard(int[][] A) {
    System.out.println("-------------");

    for (int i = 0; i < 3; i++) {
      System.out.print("| ");

      for (int j = 0; j < 3; j++) {
        System.out.print(A[i][j] + " | ");
      }

      System.out.println();
      System.out.println("-------------");
    }
  }
  
  public static boolean isValidMove(int[][] grid, int row, int col) {
    if (row < 0 || row > 2 || col < 0 || col > 2) {
      return false;
    }

    return grid[row][col] == 0;
  }
  public static void main(String[] args) {
    int[][] grid = new int[3][3];
    int foundWinner = 0;
    Scanner scanner = new Scanner(System.in);

      System.out.println("Welcome to Tic Tac Toe \n");


        // Prompt for difficulty level
        System.out.println("Choose difficulty level:");
        System.out.println("1. Easy");
        System.out.println("2. Medium");
        System.out.println("3. Difficult");
        int difficultyLevel = getValidInt("Enter the difficulty level (1-3): ", 1, 3);
    
        // Based on the chosen difficulty level, set the appropriate engine
        Engine engine;
        switch (difficultyLevel) {
          case 1:
            engine = new EasyEngine();
            break;
          case 2:
            engine = new MediumEngine();
            break;
          case 3:
            engine = new DifficultEngine();
            break;
          default:
            engine = new EasyEngine(); // Default to easy difficulty
            break;
        }

        printBoard(grid);

        int i = 0;
        while (i < 9) {
          if (i % 2 == 0) // Player 1
          {
            if (engine.getWinner("Player 1 turn", grid, 1, scanner)) {
              foundWinner = 1;
              System.out.println("Player 1 WINS!");
              break;
            }
            printBoard(grid);
            System.out.println();
          } else // Computer (Player 2)
          {
            if (engine.getWinner("Computer turn", grid, 2, scanner)) {
              foundWinner = 1;
              System.out.println("Computer WINS!");
              break;
            }
            printBoard(grid);
            System.out.println();

        // Prompt for command (save/move/exit)
        System.out.print("Input command (move/exit): ");
        String command = scanner.nextLine();

         if (command.equals("move")) {
        } else if (command.equals("exit")) {
          break;
        } else {
          System.out.println("Invalid command! Please try again.");
        }
      }
      i++;

    }

    // Prompt for command (save/exit)
    System.out.print("Input command (save/exit): ");
    String command = scanner.nextLine();

    if (foundWinner == 0)
      System.out.println("It's a draw!");

    if (command.equals("save")) {
      SaveGame.save(grid);
    } else if (command.equals("exit")) {
    } else {
      System.out.println("Invalid command! Please try again.");
    }

    scanner.close();
  }


}
