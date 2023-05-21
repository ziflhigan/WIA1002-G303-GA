package GameEngine;

import java.util.Random;
import java.util.Scanner;

public class EasyEngine extends Engine {

  private static Random random = new Random();

  public boolean getWinner(String turnPrompt, int[][] A, int playerNumber, Scanner in) {
    System.out.println(turnPrompt);
    int row = 0, col = 0;
    while (true) {
      if (playerNumber == 1) {
        row = GameEngine.getValidInt("Enter row (0-2): ",0,2);
        col = GameEngine.getValidInt("Enter col (0-2): ",0,2);
      } else {
        row = random.nextInt(3);
        col = random.nextInt(3);
        System.out.printf("Computer chooses [%d,%d]\n", row, col);
      }

      if (GameEngine.isFree(A, row, col)) {
        break;
      }

      if (playerNumber == 1) {
        System.out.printf("[%d,%d] is already filled!\n", row, col);
      }
    }
    A[row][col] = playerNumber;
    return GameEngine.checkHit(A);
  }
}
