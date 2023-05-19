/**
 *
 * @author Xiu Huan
 */
import java.util.InputMismatchException;
import java.util.Scanner;

public class TicTacToeRegular{

    private static char[][] board = new char[5][5];
    private static char player = 'X';
    private static char computer = 'O';
    private static int numMoves = 0;
    static test engine = new test();
    private static int round =1;
    private static int playermark =0;
    private static int enginemark =0;


    public void playgame(){
        printInstructions();
        char currentPlayer = player;
        while (round <4){
            initializeBoard();
            numMoves=0;
            System.out.println("\nRound " + round);
            boolean endRound = false;

            while (!endRound) {
                printBoard();
                int[] move = getValidMove(currentPlayer);
                board[move[0]][move[1]] = currentPlayer;
                numMoves++;

                if(checkWin()){
                    printBoard();
                    System.out.println("Player " + currentPlayer + " wins!");
                    if(currentPlayer =='X')
                        playermark++;
                    else
                        enginemark++;
                    System.out.println("Player\t:\tEngine");
                    System.out.println(playermark + "\t:\t" + enginemark);
                    endRound = true;
                    round++;
                }
                else if (numMoves == 25) {
                    System.out.println("It's a draw!");
                    System.out.println("Player\t:\tEngine");
                    System.out.println(playermark + "\t:\t" + enginemark);
                    endRound = true;
                    round++;
                }
                else {
                    currentPlayer = switchPlayer(currentPlayer);
                }
            }
        }
    }

    private static void initializeBoard() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                board[i][j] = '-';
            }
        }
    }

    private static void printInstructions() {
        System.out.println("Welcome to Regular Tic-Tac-Toe!");
        System.out.println("A regular game of TTT in a 5x5 square, players take turns placing shapes either\n" +
                "a cross (X) or a nought (O), the winner is the first player to place 3 of their shape\n" +
                "in either a horizontal, vertical, or diagonal row.");
        System.out.println("Player : X, Engine : O");
        System.out.println("Let's start the game!");
        System.out.println();
    }

    private static void printBoard() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private static int[] getValidMove(char currentPlayer) {
        Scanner scanner = new Scanner(System.in);
        int row = -1;
        int col = -1;
        boolean validMove = false;

        if(currentPlayer == 'X'){
            while (!validMove) {
                try {
                    System.out.print("Player turns.Enter your move (row[1-5] column[1-5]): ");
                    row = scanner.nextInt() - 1;
                    col = scanner.nextInt() - 1;
                    if (row >= 0 && row < 5 && col >= 0 && col < 5) {
                        if (board[row][col] != '-') {
                            System.out.println("Invalid move: cell is already occupied");
                        } else {
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
            System.out.println("Engine turns.");
            int[] enginemove = engine.getinput(currentPlayer, board, 5);
            row  = enginemove[0];
            col = enginemove[1];
        }
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

    private static boolean checkWin() {
        return (checkRowsForWin() || checkColumnsForWin() || checkDiagonalsForWin());
    }

    private static boolean checkRowsForWin() {
        for (int i = 0; i < 5; i++) {
            if (checkRowCol(board[i][0], board[i][1], board[i][2])
                    || checkRowCol(board[i][1], board[i][2], board[i][3])
                    || checkRowCol(board[i][2], board[i][3], board[i][4])) {
                return true;
            }
        }
        return false;
    }

    private static boolean checkColumnsForWin() {
        for (int i = 0; i < 5; i++) {
            if (checkRowCol(board[0][i], board[1][i], board[2][i])
                    || checkRowCol(board[1][i], board[2][i], board[3][i])
                    || checkRowCol(board[2][i], board[3][i], board[4][i])) {
                return true;
            }
        }
        return false;
    }
    private static boolean checkDiagonalsForWin() {
        return (checkRowCol(board[0][0], board[1][1], board[2][2])
                || checkRowCol(board[1][1], board[2][2], board[3][3])
                || checkRowCol(board[2][2], board[3][3], board[4][4])
                || checkRowCol(board[0][4], board[1][3], board[2][2])
                || checkRowCol(board[1][3], board[2][2], board[3][1])
                || checkRowCol(board[2][2], board[3][1], board[4][0])
                || checkRowCol(board[0][1], board[1][2], board[2][3])
                || checkRowCol(board[1][2], board[2][3], board[3][4])
                || checkRowCol(board[1][0], board[2][1], board[3][2])
                || checkRowCol(board[2][1], board[3][2], board[4][3]));
    }

    private static boolean checkRowCol(char c1, char c2, char c3) {
        return (c1 != '-' && c1 == c2 && c2 == c3);
    }
}