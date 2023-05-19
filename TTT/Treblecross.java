/**
 *
 * @author Xiu Huan
 */
import java.util.Scanner;
import java.util.InputMismatchException;

public class Treblecross {

    private static char[] board = new char[9];
    private static int numMoves = 0;
    static testTreblecross engine = new testTreblecross();
    private static int round =1;
    private static int playermark =0;
    private static int enginemark =0;

    public void playgame() {
        printInstructions();
        String currentPlayer = "Player";
        while(round<4){
            initializeBoard();
            numMoves =0;
            System.out.println("\nRound " + round);
            boolean endRound = false;

            int position = 99999;
            while (!endRound) {
                printBoard();
                int move = getValidMove(currentPlayer);
                board[move] = 'X';
                numMoves++;

                if(checkWin()){
                    printBoard();
                    if(currentPlayer.equals("Player")){
                        playermark++;
                        System.out.println("Player wins!");
                    }
                    else{
                        enginemark++;
                        System.out.println("Engine wins!");
                    }
                    System.out.println("Player\t:\tEngine");
                    System.out.println(playermark + "\t:\t" + enginemark);
                    endRound = true;
                    round ++;
                }
                else if(numMoves ==9){
                    System.out.println("It's a draw!");
                    System.out.println("Player\t:\tEngine");
                    System.out.println(playermark + "\t:\t" + enginemark);
                    endRound = true;
                    round++;
                }
                else{
                    currentPlayer = switchPlayer(currentPlayer);
                }
            }
        }
    }

    private static int getValidMove(String currentPlayer){
        Scanner scanner = new Scanner(System.in);
        int row = -1;
        boolean validMove = false;

        if(currentPlayer.equals("Player")){
            while (!validMove) {
                try {
                    System.out.print("Player's turn, enter your move(row[1-9]): ");
                    row = scanner.nextInt() -1;

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
            row = engine.getinput(currentPlayer, board, 9);
        }

        int move = row;
        return move;
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

    private static boolean checkWin() {
        for (int i = 0; i < 9-2; i ++) {
            if (board[i] == board[i + 1] && board[i] == board[i + 2] && board[i] != '-') {
                return true;
            }
        }
        return false;
    }

    public static String switchPlayer(String currentPlayer) {
        if (currentPlayer.equals("Player")) {
            currentPlayer = "Engine";
        } else {
            currentPlayer = "Player";
        }
        return currentPlayer;
    }
}