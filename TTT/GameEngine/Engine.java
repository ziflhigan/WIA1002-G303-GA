package GameEngine;

import java.util.Scanner;
//interface
public abstract class Engine {
    public abstract boolean getWinner(String turnPrompt, int[][] A, int playerNumber, Scanner in);
    // Other methods and properties common to all engine types
  }
