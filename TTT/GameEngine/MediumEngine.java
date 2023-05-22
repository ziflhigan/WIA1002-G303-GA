package GameEngine;

import java.util.Scanner;


public class MediumEngine extends Engine {

  public  boolean getWinner(String turnPrompt, int[][] A, int playerNumber, Scanner in) {
    System.out.println(turnPrompt);
    int row = 0, col = 0;
    while (true) {
      if (playerNumber == 1) {
        row = GameEngine.getValidInt("Enter row (0-2): ",0,2);
        col = GameEngine.getValidInt("Enter col (0-2): ",0,2);
      } else {
        // Medium mode logic
        boolean moved = false;
        for (int i = 0; i < A.length; i++) {
          for (int j = 0; j < A[i].length; j++) {
            if (A[i][j] == 0) {
              A[i][j] = playerNumber;
              moved = true;
              break;
            }
          }
          if (moved)
            break;
        }
        if (!moved) {
          System.out.println("No more valid moves for the computer.");
          break;
        }
        row = -1; // Dummy values since it's not a user input
        col = -1;
        System.out.printf("Computer chooses [%d,%d]\n", row, col);
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

}
