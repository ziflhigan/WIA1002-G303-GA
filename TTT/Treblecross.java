/**
 *
 * @author Xiu Huan
 */
import GameEngine.EasyTTTEngine;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

public class Treblecross {

    private static char[] board = new char[9];
    private static int numMoves = 0;
    //static testTreblecross engine = new testTreblecross();
    private static int round =1;
    private static int playermark =0;
    private static int enginemark =0;
    private static Stack<Integer> moveHistory = new Stack<>();
    private static PlayerAccount playerAccount;
    private Engine engine;

    public Treblecross(PlayerAccount playerAccount) {
        this.playerAccount = playerAccount;
    }

    public boolean playgame() {
        Random rd = new Random();
        int engNum = rd.nextInt(3);

        if (engNum == 0){
            this.engine = new TrebleCrossEngineEasy();
            System.out.println("You engine's difficulty is easy, you can do it!");
        }
        else if (engNum == 1){
            this.engine = new TrebleCrossEngineMedium();
            System.out.println("The engine's difficulty level is medium, good luck!");
        }
        else {
            this.engine = new TrebleCrossEngineHard();
            System.out.println("The engine's difficulty is hard, try your best! ");
        }

        printInstructions();
        String currentPlayer = playerAccount.getUsername();

            initializeBoard();
            numMoves =0;
            System.out.println("\nRound " + round);
            boolean endRound = false;

            while (!endRound) {

                double[] probabilities = getWinProbability(currentPlayer);
                System.out.println("Player's win probability: " + probabilities[0] * 100 + "%");
                System.out.println("Engine's win probability: " + probabilities[1] * 100 + "%");

                printBoard();
                int move = getValidMove(currentPlayer);
                board[move] = 'X';
                numMoves++;

                if(checkWin()){
                    printBoard();
                    if(currentPlayer.equals(playerAccount.getUsername())){
                        playermark++;
                        System.out.println(playerAccount.getUsername()+" wins!");
                    }
                    else{
                        enginemark++;
                        System.out.println("Engine wins!");
                    }
                    System.out.println(playerAccount.getUsername()+"\t:\tEngine");
                    System.out.println(playermark + "\t:\t" + enginemark);
                    round ++;

                    playerAccount.updateLeaderboard(playerAccount.getUsername(), playermark);
                    playerAccount.updateLeaderboard("Engine", enginemark);

                    moveHistory.clear();
                    return !currentPlayer.equals("Engine");
                }
                else if(numMoves ==9){
                    System.out.println("It's a draw!");
                    System.out.println(playerAccount.getUsername()+"\t:\tEngine");
                    System.out.println(playermark + "\t:\t" + enginemark);
                    System.out.println("Play again!");
                    round++;
                }
                else{
                    currentPlayer = switchPlayer(currentPlayer);
                }
            }

        return false;
    }

    private int getValidMove(String currentPlayer){
        Scanner scanner = new Scanner(System.in);
        int row = -1;
        boolean validMove = false;

        if(currentPlayer.equals(playerAccount.getUsername())){
            while (!validMove) {
                try {

                    if (!moveHistory.isEmpty()){
                        System.out.println("Do you want to take back a move? (1: Yes, Other Numbers: No)");
                        if (scanner.nextInt() == 1){
                            takeBackMove();
                            takeBackMove();
                            System.out.println("Both of the player and Engine's turn have been taken back");
                            continue;
                        }
                    }

                    System.out.print(playerAccount.getUsername()+"'s turn\n 1. make a move\n 2. save game \n 3. load game \n");
                    int choice = scanner.nextInt();
                    scanner.nextLine();
                    switch (choice){
                        case 1:
                            break;
                        case 2:
                            saveGame(board);
                            break;
                        case 3:
                            System.out.println("Enter a filename to load (no suffix)");
                            String fN = scanner.nextLine() + ".txt";
                            loadGame(fN);
                            printBoard();
                            break;
                        default:
                            System.out.println("Invalid choice, try again");
                            continue;
                    }

                    System.out.println("enter your move(row[1-9])");
                    row = scanner.nextInt() - 1;
                    scanner.nextLine();

                    if (row >= 0 && row < 9) {
                        if (board[row] != '-') {
                            System.out.println("Invalid move: cell is already occupied");
                        } else {
                            board[row] = 'X';
                            validMove = true;
                        }
                    } else {
                        System.out.println("Invalid move: number must be between 1 and 9");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter integers.");
                    scanner.nextLine(); // consume the invalid input
                }
            }
        }
        else{
            System.out.println("Engine turns.");
            row = engine.getMove(board,currentPlayer);

        }

        moveHistory.push(row);
        return row;
    }

    private static void initializeBoard() {
        for (int i = 0; i < 9; i++) {
            board[i] = '-';
        }
    }

    private static void printInstructions() {
        System.out.println("Treblecross");
        System.out.println("The game begins with all the 1×n spaces empty. Each player plays an X on the one-dimensional board in an empty cell.");
        System.out.println("The game is won when a player makes a row of three Xs");
        System.out.println("Let's start the game!");
        System.out.println();
    }

    private static void printBoard() {
        for (int i = 0; i < 9; i++) {
            System.out.print(board[i] + " ");
        }
        System.out.println();
    }

    public boolean checkWin() {
        for (int i = 0; i < 9-2; i ++) {
            if (board[i] == board[i + 1] && board[i] == board[i + 2] && board[i] != '-') {
                return true;
            }
        }
        return false;
    }

    public static String switchPlayer(String currentPlayer) {
        if (currentPlayer.equals(playerAccount.getUsername())) {
            currentPlayer = "Engine";
        } else {
            currentPlayer = playerAccount.getUsername();
        }
        return currentPlayer;
    }

    private static double[] getWinProbability(String currentPlayer) {
        int totalXs = getTotalXs();
        int currentPlayerScore = currentPlayer.equals("Player") ? (numMoves + 1) / 2 : numMoves / 2;  // it's the Player's turn every odd move and the engine's turn every even move

        double currentPlayerProbability = (double) currentPlayerScore / totalXs;
        double otherPlayerProbability = (double) (totalXs - currentPlayerScore) / totalXs;

        return currentPlayer.equals("Player") ? new double[]{currentPlayerProbability, otherPlayerProbability} : new double[]{otherPlayerProbability, currentPlayerProbability};
    }

    private static int getTotalXs() {
        int total = 0;

        for (int i = 0; i < 9; i++) {  // there are 9 cells on the board
            if (board[i] == 'X') {
                total++;
            }
        }

        return total;
    }

    private static void takeBackMove() {
        if (!moveHistory.isEmpty()) {
            int lastMove = moveHistory.pop();
            board[lastMove] = '-';
            numMoves--;
            System.out.println("Took back last move.");
            printBoard();
        } else {
            System.out.println("No moves to take back.");
        }
    }

    public void saveGame(char[] board) {

        //CHeck the board is empty or not first
        int count = 0;
        for (int i = 0; i < board.length; i++) {
            if (board[i] == '-'){
                count++;
            }
        }
        if (count == 9){
            System.out.println("The board is empty, make move first!");
            return;
        }

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter a file name to be saved");
        String fileName = sc.nextLine() + ".txt";

        Path path = Paths.get( fileName);
        while (Files.exists(path)) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("A saved game with this file name already exists. Please enter a different name for the saved game file.");
            fileName = scanner.nextLine();
            path = Paths.get( fileName);
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter( "UserSaveGames\\" + fileName ))) {
            for (int i = 0; i < board.length; i++) {
                writer.write(board[i] + " ");
            }

            writer.println();
            writer.println("The game was played by the player: " + playerAccount.getUsername());
            System.out.println("Game saved successfully!");
        } catch (IOException e) {
            System.out.println("Error saving the game.");
        }
    }

    public void loadGame(String fileName) {
        char[] tempBoard = new char[9];

        boolean success = false;
        try (Scanner scanner = new Scanner(new FileReader("UserSaveGames\\" + fileName))) {
            String[] line = scanner.nextLine().split(" ");
            for (int i = 0; i < 9; i++) {
                tempBoard[i] = line[i].charAt(0);
            }
            while (scanner.hasNextLine()){
                System.out.println(scanner.nextLine());
            }

            System.out.println("Game loaded successfully!");
        } catch (IOException e) {
            System.out.println("Error loading the game.");
            return;
        }

        for (int i = 0; i < board.length; i++){
            board [i] = tempBoard[i];
        }
    }

}