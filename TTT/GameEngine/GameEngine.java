package GameEngine;
import java.util.Scanner;

public class GameEngine {
    public static void main(String[] args) {
        System.out.println();
        System.out.println("Welcome to Tic-Tac-Toe\n");
        System.out.println("Choose game type:");
        System.out.println("1. Regular Tic-Tac-Toe (5x5)");
        System.out.println("2. Reverse Tic-Tac-Toe (3x3)");
        System.out.println("3. Treble Cross (3x3)");

        Scanner scanner = new Scanner(System.in);
        int gameType = getValidInt(scanner, "Enter the game type (1-3): ", 1, 3);

        switch (gameType) {
            case 1:
                playRegularTicTacToe(scanner);
                break;
            case 2:
                playReverseTicTacToe(scanner);
                break;
            case 3:
                playTrebleCross(scanner);
                break;
            default:
                System.out.println("Invalid game type. Exiting the game.");
        }
    }

    private static void playRegularTicTacToe(Scanner scanner) {
        int difficulty = getValidInt(scanner, "\nEnter the difficulty level (1-3): ", 1, 3);

        Engine engine;


        switch (difficulty) {
            case 1:
                engine = new EasyTTTEngine(5);
                break;
            case 2:
            engine = new MediumTTTEngine(5);
            break;
            case 3:
            engine = new EasyTTTEngine(5);
            break;
            default:
                System.out.println("Invalid difficulty level. Starting with easy level.");
                engine = new EasyTTTEngine(5);
        }

        engine.setBoardSize(5);

        engine.playGame(scanner);
    }

    private static void playReverseTicTacToe(Scanner scanner) {
        Engine engine = new ReverseEngine();
        engine.playGame(scanner);
    }

    private static void playTrebleCross(Scanner scanner) {
        Engine engine = new TrebleCrossEngine();
        engine.playGame(scanner);
    }

    static int getValidInt(Scanner scanner, String prompt, int min, int max) {
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
}
