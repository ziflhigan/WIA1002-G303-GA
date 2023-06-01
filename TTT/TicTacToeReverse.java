import GameEngine.EngineInterface;
import GameEngine.RegularEngineEasy;
import GameEngine.RegularEngineHard;
import GameEngine.RegularEngineMedium;
import GameEngine.ReverseEngineEasy;
import GameEngine.ReverseEngineHard;
import GameEngine.ReverseEngineMedium;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.InputMismatchException;
// import java.util.Random;
import java.util.Scanner;
import java.util.Stack;

public class TicTacToeReverse implements Serializable{

    private static char[][] board = new char[3][3];
    private static char player = 'X';
    private static char computer = 'O';
    private static int numMoves = 0;
    private static int round =1;
    private static int playermark =0;
    private static int enginemark =0;
    private static Stack<Integer> historyMoveRow, historyMoveCol;
    private static PlayerAccount playerAccount;

    EngineInterface engine;

    public TicTacToeReverse (PlayerAccount playerAccount) {

        Scanner in = new Scanner(System.in);
        // int engNum = in.nextInt();
        int engNum = 2;

        if (engNum == 0){
            engine = new ReverseEngineEasy();
        } else if (engNum == 1) {
            engine = new ReverseEngineMedium();
        }else {
            engine = new ReverseEngineHard();
        }

        this.playerAccount = playerAccount;

    }

    public boolean playgame(){
        printInstructions();
        char currentPlayer = player;
            // To make sure if 'draw' happens, the player needs to play again
            while(true){
                initializeBoard();
                numMoves=0;
                //System.out.println("\nRound " + round);
                boolean endRound = false;


                while (!endRound) {
                    double[] probabilities = getWinProbability();
                    System.out.println("Player's win probability: " + probabilities[0] * 100 + "%");
                    System.out.println("Engine's win probability: " + probabilities[1] * 100 + "%");

                    printBoard();
                    int[] move = getValidMove(currentPlayer);
                    board[move[0]][move[1]] = currentPlayer;
                    numMoves++;

                    if(checkWin()){
                        printBoard();

                        if(currentPlayer =='X'){
                            playermark++;
                            System.out.println(playerAccount.getUsername()+" wins!");
                        }
                        else{
                            enginemark++;
                            System.out.println("Engine wins!");
                        }

                        System.out.println(playerAccount.getUsername()+"\t:\tEngine");
                        System.out.println(playermark + "\t:\t" + enginemark);
                        round++;

                        playerAccount.updateLeaderboard(playerAccount.getUsername(), playermark);
                        playerAccount.updateLeaderboard("Engine", enginemark);

                        historyMoveRow.clear();
                        historyMoveCol.clear();
                        return currentPlayer == 'X';
                    }
                    else if (numMoves == 9) {
                        System.out.println("It's a draw!");
                        System.out.println(playerAccount.getUsername()+"\t:\tEngine");
                        System.out.println(playermark + "\t:\t" + enginemark);
                        System.out.println("Play again!");
                        round++;

                        playerAccount.updateLeaderboard(playerAccount.getUsername(), playermark);
                        playerAccount.updateLeaderboard("Engine", enginemark);

                        historyMoveRow.clear();
                        historyMoveCol.clear();
                        break;
                    }
                    else {
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
        historyMoveRow = new Stack<>();
        historyMoveCol = new Stack<>();
    }

    private static void printInstructions() {
        System.out.println("In Reverse Tic-Tac-Toe, players take turns placing shapes either a cross (X) or a nought (O) on a 3x3 square grid.");
        System.out.println("The goal of the game is to avoid making a row of 3 of your shapes in either a horizontal, vertical, or diagonal line.");
        System.out.println("The player who is unable to make a row of 3 of their shapes wins the game.");
        System.out.println(playerAccount.getUsername()+" : X, Engine : O");
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

    private int[] getValidMove(char currentPlayer) {
        Scanner scanner = new Scanner(System.in);
        int row = -1;
        int col = -1;
        boolean validMove = false;

        if(currentPlayer == 'X'){
            while (!validMove) {
                try {

                    if (!historyMoveRow.isEmpty() && !historyMoveCol.isEmpty()){
                        System.out.println("Do you want to take back a move? (1: Yes, Other Numbers: No)");
                        if (scanner.nextInt() == 1){
                            takeBackMove();
                            takeBackMove();
                            System.out.println("Both of the player's and Engine's move have been taken back");
                            continue;
                        }
                    }else {
                        System.out.println("There are no moves to be taken back");
                    }

                    System.out.println(playerAccount.getUsername()+"\s turn \n 1. Make a move\n 2. Save game\n 3. Load game\n");
                    int choices = scanner.nextInt();
                    scanner.nextLine();

                    switch (choices){
                        case 1:
                            break;
                        case 2:
                           saveGame();
                           break;
                        case 3:
                            loadGame("Regular");
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

                            if (board[row][col] == '-') {
                                board[row][col] = currentPlayer;
                                validMove= true;
                            }

                            board[row][col] = currentPlayer;
                            validMove= true;
                        }
                    } else {
                        System.out.println("Invalid move: row and column must be between 1 and 3");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter integers.");
                    scanner.nextLine(); // consume the invalid input
                }
            }
        }
        else{
            System.out.println("Engine's turn.");
            int[] enginemove = engine.getMove(board);
            row  = enginemove[0];
            col = enginemove[1];
        }
        historyMoveRow.push(row);
        historyMoveCol.push(col);
        int[] move = {row, col};
        return move;
    }


    private static char switchPlayer(char currentPlayer) {
        if (currentPlayer == player) {
            currentPlayer = computer;
        } else {
            currentPlayer = player;
        }

        return currentPlayer;
    }

    public boolean checkWin() {
        return (checkRowsForWin() || checkColumnsForWin() || checkDiagonalsForWin());
    }
    
    private static boolean checkRowsForWin() {
        for (int i = 0; i < 3; i++) {
            if (checkRowCol(board[i][0], board[i][1], board[i][2])) {
                return true;
            }
        }
        return false;
    }
    
    private static boolean checkColumnsForWin() {
        for (int i = 0; i < 3; i++) {
            if (checkRowCol(board[0][i], board[1][i], board[2][i])) {
                return true;
            }
        }
        return false;
    }
    
    private static boolean checkDiagonalsForWin() {
        return (checkRowCol(board[0][0], board[1][1], board[2][2])
                || checkRowCol(board[0][2], board[1][1], board[2][0]));
    }
    
    private static boolean checkRowCol(char c1, char c2, char c3) {
        return (c1 != '-' && c1 == c2 && c2 == c3);
    }
    
    private static double[] getWinProbability() {
        int playerScore = getScore(player);
        int engineScore = getScore(computer);
    
        int totalScore = playerScore + engineScore;
    
        double playerProbability = (double) playerScore / totalScore;
        double engineProbability = (double) engineScore / totalScore;
    
        return new double[]{playerProbability, engineProbability};
    }
    
    private static int getScore(char player) {
        int score = 0;
    
        // Check rows
        for (int i = 0; i < 3; i++) {
            score += getLineScore(new char[]{board[i][0], board[i][1], board[i][2]}, player);
        }
    
        // Check columns
        for (int i = 0; i < 3; i++) {
            score += getLineScore(new char[]{board[0][i], board[1][i], board[2][i]}, player);
        }
    
        // Check diagonals
        score += getLineScore(new char[]{board[0][0], board[1][1], board[2][2]}, player);
        score += getLineScore(new char[]{board[0][2], board[1][1], board[2][0]}, player);
    
        return score;
    }
    
    private static int getLineScore(char[] line, char player) {
        int score = 0;
    
        for (char c : line) {
            if (c == player) {
                score++;
            } else if (c != '-') {
                return 0;
            }
        }
    
        return score;
    }
    
    private static void takeBackMove(){
        if (!historyMoveRow.isEmpty() && !historyMoveCol.isEmpty()) {
            int row = historyMoveRow.pop();
            int col = historyMoveCol.pop();
            board[row][col] = '-';
            numMoves--;
            printBoard();
        } else {
            System.out.println("No moves to take back!");
        }
    }

    public void saveGame() {

        if (numMoves == 0){
            System.out.println("There are no any moves yet, make a move!");
            return;
        }

        Scanner sc = new Scanner(System.in);
        GameState gameState = new GameState("Regular", board, numMoves, round, playermark, enginemark, playerAccount);
        System.out.println("Enter a file name to be saved (No suffix)");
        String fileName = sc.nextLine() + ".ser";


        // Check the file already exits or not
        Path path = Paths.get( fileName);
        while (Files.exists(path)) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("A saved game with this file name already exists. Please enter a different name for the saved game file.");
            fileName = scanner.nextLine();
            path = Paths.get( fileName);
        }

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream( "UserSaveGames\\" + fileName))) {
            out.writeObject(gameState);
            System.out.println("Game saved successfully!");
        } catch (IOException e) {
            System.out.println("Error saving the game: " + e.getMessage());
        }
    }

    public void loadGame(String currentGameVersion) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter a file name to load (No suffix)");
        String fileName = sc.nextLine() + ".ser";
        GameState loadedState = null;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream( "UserSaveGames\\" + fileName))) {
            loadedState = (GameState) in.readObject();

            // Check game version
            if (!loadedState.getGameVersion().equals(currentGameVersion)) {
                System.out.println("The loaded game is not compatible with the current version of Tic Tac Toe. Please load a compatible saved game.");
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
        } catch (FileNotFoundException e) {
            System.out.println("Error finding the save game file: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Error loading the game state class: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error reading the save game file: " + e.getMessage());
        }
    }

    public static char[][] getBoard() {
        return board;
    }
}