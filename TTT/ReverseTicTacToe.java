import GameEngine.EngineInterface;
import GameEngine.ReverseEngineEasy;
import GameEngine.ReverseEngineHard;
import GameEngine.ReverseEngineMedium;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.Stack;

public class ReverseTicTacToe {
    private static char[][] board = new char[3][3];
    private static char player = 'X';
    private static char computer = 'O';
    private static int numMoves = 0;
    private static int round = 1;
    private static int playermark = 0;
    private static int enginemark = 0;
    EngineInterface engine;

    private static Stack<int[]> moves = new Stack<>();
    private static PlayerAccount playerAccount;

    public ReverseTicTacToe(PlayerAccount playerAccount) {
        this.playerAccount = playerAccount;
    }

    public boolean playgame() {

        String gameMode = getGameMode();
        switch (gameMode) {
            case "PVP":
                return playPVP();
            default:
                return playPVE();
        }
    }

    public boolean playPVE() {

        printInstructions();
        String engNum = getDifficulty();
        System.out.println();

        if (engNum.equals("0")) {
            this.engine = new ReverseEngineEasy();
            System.out.println("You engine's difficulty is easy, you can do it!");
        } else if (engNum.equals("1")) {
            this.engine = new ReverseEngineMedium();
            System.out.println("The engine's difficulty level is medium, good luck!");
        } else {
            this.engine = new ReverseEngineHard();
            System.out.println("The engine's difficulty is hard, try your best! ");
        }

        char currentPlayer = player;

        while (true) {
            initializeBoard();
            numMoves = 0;
            System.out.println("\nRound " + round);

            while (true) {

                double[] probabilities = getWinProbability();
                System.out.println("Player's win probability: " + probabilities[0] * 100 + "%");
                System.out.println("Engine's win probability: " + probabilities[1] * 100 + "%");

                printBoard();
                int[] move = getValidMove(currentPlayer, false);
                board[move[0]][move[1]] = currentPlayer;
                numMoves++;

                if (checkWin(currentPlayer)) {
                    printBoard();
                    currentPlayer = switchPlayer(currentPlayer);

                    if (currentPlayer == 'X') {
                        playermark++;
                        System.out.println(playerAccount.getUsername() + " wins!");
                    } else {
                        enginemark++;
                        System.out.println("Engine wins!");
                    }
                    System.out.println(playerAccount.getUsername() + "\t:\tEngine");
                    System.out.println(playermark + "\t:\t" + enginemark);
                    round++;

                    playerAccount.updateLeaderboard(playerAccount.getUsername(), playermark);
                    playerAccount.updateLeaderboard("Engine", enginemark);

                    moves.clear();
                    return currentPlayer == 'X';
                } else if (numMoves == 9) {
                    System.out.println("It's a draw!");
                    System.out.println(playerAccount.getUsername() + "\t:\tEngine");
                    System.out.println(playermark + "\t:\t" + enginemark);
                    System.out.println("Play again!");
                    round++;

                    playerAccount.updateLeaderboard(playerAccount.getUsername(), playermark);
                    playerAccount.updateLeaderboard("Engine", enginemark);
                    // Clear the history stack for the next round
                    moves.clear();

                    break;
                } else {
                    currentPlayer = switchPlayer(currentPlayer);
                }
            }
        }
    }

    private static void initializeBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = '-';
            }
        }
    }

    public boolean playPVP() {
        printInstructions();

        char currentPlayer = player;
        while (true) {
            initializeBoard();
            numMoves = 0;
            System.out.println("\nRound " + round);

            while (true) {

                double[] probabilities = getWinProbability();
                System.out.println("Player's win probability: " + probabilities[0] * 100 + "%");
                System.out.println("Second player's win probability: " + probabilities[1] * 100 + "%");

                printBoard();
                int[] move = getValidMove(currentPlayer, true);
                board[move[0]][move[1]] = currentPlayer;
                numMoves++;

                if (checkWin(currentPlayer)) {
                    printBoard();
                    currentPlayer = switchPlayer(currentPlayer);

                    if (currentPlayer == 'X') {
                        playermark++;
                        System.out.println(playerAccount.getUsername() + " wins!");
                    } else {
                        enginemark++;
                        System.out.println("Second player wins!");
                    }
                    System.out.println(playerAccount.getUsername() + "\t:\tSecond Player");
                    System.out.println(playermark + "\t:\t" + enginemark);
                    round++;

                    playerAccount.updateLeaderboard(playerAccount.getUsername(), playermark);
                    playerAccount.updateLeaderboard("Second Player", enginemark);

                    moves.clear();
                    return currentPlayer == 'X';
                } else if (numMoves == 9) {
                    System.out.println("It's a draw!");
                    System.out.println(playerAccount.getUsername() + "\t:\tSecond Player");
                    System.out.println(playermark + "\t:\t" + enginemark);
                    System.out.println("\nPlay again!");
                    round++;

                    playerAccount.updateLeaderboard(playerAccount.getUsername(), playermark);
                    playerAccount.updateLeaderboard("Second Player", enginemark);
                    // Clear the history stack for the next round
                    moves.clear();
                    break;

                } else {
                    currentPlayer = switchPlayer(currentPlayer);
                }
            }

        }
    }

    private static void printInstructions() {
        System.out.println("Welcome to Reverse Tic-Tac-Toe!");
        System.out.println(
                "The rules are simple: the loser is the first player to place 3 of their shape in either a horizontal, vertical, or diagonal row.");
        System.out.println(playerAccount.getUsername() + " : X, Engine : O");
        System.out.println("Let's start the game!");
        System.out.println();
    }

    private static void printBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private int[] getValidMove(char currentPlayer, boolean isHuman) {
        Scanner scanner = new Scanner(System.in);
        int row = -1;
        int col = -1;
        boolean validMove = false;

        if (currentPlayer == 'X') {
            while (!validMove) {
                try {

                    if (!moves.isEmpty()) {
                        System.out.println("Do you want to take back a move? (1: Yes, Other Numbers: No)");
                        if (scanner.nextInt() == 1) {
                            takeBackMove();
                            takeBackMove();
                            System.out.println("Both of the player and Engine's move have been taken back");
                            continue;
                        }

                    } else {
                        System.out.println("No more moves left to be taken back, please make a move");
                    }

                    System.out.println(
                            playerAccount.getUsername() + "\s turn \n 1. Make a move\n 2. Save game\n 3. Load game\n");
                    int choices = scanner.nextInt();
                    scanner.nextLine();

                    switch (choices) {
                        case 1:
                            break;
                        case 2:
                            saveGame();
                            break;
                        case 3:
                            loadGame("Reverse");
                            printBoard();
                            break;
                    }

                    System.out.print("Enter your move (row[1-3] column[1-3]): ");
                    row = scanner.nextInt() - 1;
                    col = scanner.nextInt() - 1;

                    if (row >= 0 && row < 3 && col >= 0 && col < 3) {
                        if (board[row][col] != '-') {
                            System.out.println("Invalid move: cell is already occupied");
                        } else {
                            board[row][col] = currentPlayer;
                            validMove = true;
                        }
                    } else {
                        System.out.println("Invalid move: row and column must be between 1 and 3");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter integers.");
                    scanner.nextLine(); // consume the invalid input
                }
            }
        } else {

            if (isHuman) {
                int[] newMove = getMoveSecondPlayer();
                row = newMove[0];
                col = newMove[1];

            } else {
                System.out.println("Engine turns.");
                int[] enginemove = engine.getMove(board);
                row = enginemove[0];
                col = enginemove[1];
            }

        }
        int[] move = { row, col };
        moves.push(move);
        return move;
    }

    private int[] getMoveSecondPlayer() {
        Scanner sc = new Scanner(System.in);
        int row = -1;
        int col = -1;
        boolean validMove = false;

        while (!validMove) {
            try {

                System.out.print("Second player, enter your move (row[1-3] column[1-3]): ");
                row = sc.nextInt() - 1;
                col = sc.nextInt() - 1;

                if (row >= 0 && row < 3 && col >= 0 && col < 3) {
                    if (board[row][col] != '-') {
                        System.out.println("Invalid move: cell is already occupied");
                    } else {
                        board[row][col] = 'O';
                        validMove = true;
                    }
                } else {
                    System.out.println("Invalid move: row and column must be between 1 and 3");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter integers.");
                sc.nextLine(); // consume the invalid input
            }
        }
        int[] move = { row, col };
        return move;

    }

    public boolean checkWin(char currentSymbol) {
        return (checkRowsForWin(currentSymbol) || checkColumnsForWin(currentSymbol)
                || checkDiagonalsForWin(currentSymbol));
    }

    private static char switchPlayer(char currentPlayer) {
        if (currentPlayer == player) {
            currentPlayer = computer;
        } else {
            currentPlayer = player;
        }

        return currentPlayer;
    }

    private static boolean checkRowsForWin(char player) {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == player && board[i][1] == player && board[i][2] == player) {
                return true;
            }
        }
        return false;
    }

    private static boolean checkColumnsForWin(char player) {
        for (int i = 0; i < 3; i++) {
            if (board[0][i] == player && board[1][i] == player && board[2][i] == player) {
                return true;
            }
        }
        return false;
    }

    private static boolean checkDiagonalsForWin(char player) {
        if (board[0][0] == player && board[1][1] == player && board[2][2] == player) {
            return true;
        }
        if (board[0][2] == player && board[1][1] == player && board[2][0] == player) {
            return true;
        }
        return false;
    }

    private static int getLineScore(char[] line, char player) {
        int score = 0;

        for (char c : line) {
            if (c == player) {
                return 0; // if the player has a mark in this line, its score is 0
            } else if (c != '-') {
                score++;
            }
        }

        return score;
    }

    private static int getScore(char player) {
        int score = 0;

        // Check rows
        for (int i = 0; i < 3; i++) {
            score += getLineScore(new char[] { board[i][0], board[i][1], board[i][2] }, player);
        }

        // Check columns
        for (int i = 0; i < 3; i++) {
            score += getLineScore(new char[] { board[0][i], board[1][i], board[2][i] }, player);
        }

        // Check diagonals
        score += getLineScore(new char[] { board[0][0], board[1][1], board[2][2] }, player);
        score += getLineScore(new char[] { board[0][2], board[1][1], board[2][0] }, player);

        return score;
    }

    private static double[] getWinProbability() {
        int playerScore = getScore(player);
        int engineScore = getScore(computer);

        int totalScore = playerScore + engineScore;

        double playerProbability = (double) playerScore / totalScore;
        double engineProbability = (double) engineScore / totalScore;

        return new double[] { playerProbability, engineProbability };
    }

    private static void takeBackMove() {
        // Pop the most recent move off the stack.
        if (!moves.isEmpty()) {
            int[] lastMove = moves.pop();
            // Set the cell for this move back to its initial value.
            board[lastMove[0]][lastMove[1]] = '-';
            // Decrease the number of moves.
            numMoves--;
            System.out.println("Last move taken back. ");
            printBoard();
        } else {
            System.out.println("No moves to take back.");
        }
    }

    private void saveGame() {

        if (numMoves == 0) {
            System.out.println("There are no any moves yet, make a move!");
            return;
        }

        Scanner sc = new Scanner(System.in);
        GameState gameState = new GameState("Reverse", board, numMoves, round, playermark, enginemark, playerAccount);
        System.out.println("Enter a file name to be saved (No suffix)");
        String fileName = sc.nextLine() + ".ser";

        // Check the file already exits or not
        Path path = Paths.get(fileName);
        while (Files.exists(path)) {
            Scanner scanner = new Scanner(System.in);
            System.out.println(
                    "A saved game with this file name already exists. Please enter a different name for the saved game file.");
            fileName = scanner.nextLine();
            path = Paths.get(fileName);
        }

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("UserSaveGames\\" + fileName))) {
            out.writeObject(gameState);
            System.out.println("Game saved successfully!");
        } catch (IOException e) {
            System.out.println("Error saving the game: " + e.getMessage());
        }
    }

    public void loadGame(String currentGameVersion) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter a file name to load");
        String fileName = sc.nextLine() + ".ser";
        GameState loadedState = null;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("UserSaveGames\\" + fileName))) {
            loadedState = (GameState) in.readObject();

            // Check game version
            if (!loadedState.getGameVersion().equals(currentGameVersion)) {
                System.out.println(
                        "The loaded game is not compatible with the current version of Tic Tac Toe. Please load a compatible saved game.");
                return;
            }

            board = loadedState.board;
            numMoves = loadedState.numMoves;
            round = loadedState.round;
            playermark = loadedState.playerMark;
            enginemark = loadedState.engineMark;
            playerAccount = loadedState.playerAccount;
            System.out.println("Game loaded successfully!");
            System.out.println("The game was played by:" + playerAccount.getUsername());
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading the game.");
        }
    }

    public String getGameMode() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose game mode: ");
        System.out.println("1. Player vs Player (PVP)");
        System.out.println("2. Player vs Engine (PVE)");
        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                return "PVP";
            case 2:
                return "PVE";
            default:
                System.out.println("Invalid choice, defaulting to PVE");
                return "PVE";
        }
    }

    public String getDifficulty() {
        Scanner scanner = new Scanner(System.in);
        String difficulty = "";

        System.out.println("Choose game difficulty: ");
        System.out.println("0. Easy");
        System.out.println("1. Medium");
        System.out.println("Default: Hard");

        difficulty = scanner.nextLine();
        switch (difficulty) {
            case "0":
                return "0";
            case "1":
                return "1";
            default:
                return "hard";
        }
    }

}
