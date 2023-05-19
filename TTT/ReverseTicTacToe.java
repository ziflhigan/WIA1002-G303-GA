
/**
 *
 * @author Xiu Huan
 */
import java.util.Scanner;
import java.util.InputMismatchException;

public class ReverseTicTacToe {
    private static char[][] board = new char[3][3];
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
        while(round < 4){
            initializeBoard();
            numMoves =0;
            System.out.println("\nRound " + round);
            boolean endRound = false;

            while (!endRound) {
                printBoard();
                int[] move = getValidMove(currentPlayer);
                board[move[0]][move[1]] = currentPlayer;
                numMoves++;

                if (checkWin(currentPlayer)) {
                    printBoard();
                    currentPlayer = switchPlayer(currentPlayer);
                    System.out.println("Player " + currentPlayer + " wins!");
                    if(currentPlayer =='X')
                        playermark++;
                    else
                        enginemark++;
                    System.out.println("Player\t:\tEngine");
                    System.out.println(playermark + "\t:\t" + enginemark);
                    endRound = true;
                    round ++;
                }
                else if (numMoves == 9) {
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
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = '-';
            }
        }
    }

    private static void printInstructions() {
        System.out.println("Welcome to Reverse Tic-Tac-Toe!");
        System.out.println("The rules are simple: the loser is the first player to place 3 of their shape in either a horizontal, vertical, or diagonal row.");
        System.out.println("Player : X, Engine : O");
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

    private static int[] getValidMove(char currentPlayer) {
        Scanner scanner = new Scanner(System.in);
        int row = -1;
        int col = -1;
        boolean validMove = false;

        if(currentPlayer == 'X'){
            while (!validMove) {
                try {
                    System.out.print("Player turns.Enter your move (row[1-3] column[1-3]): ");
                    row = scanner.nextInt() - 1;
                    col = scanner.nextInt() - 1;
                    if (row >= 0 && row < 3 && col >= 0 && col < 3) {
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
            int[] enginemove = engine.getinput(currentPlayer, board, 3);
            row  = enginemove[0];
            col = enginemove[1];
        }
        int[] move = {row, col};
        return move;
    }

    private static boolean checkWin(char currentPlayer) {
        return (checkRowsForWin(currentPlayer) || checkColumnsForWin(currentPlayer) || checkDiagonalsForWin(currentPlayer));
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


}

