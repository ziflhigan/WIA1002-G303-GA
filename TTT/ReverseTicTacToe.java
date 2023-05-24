
/**
 *
 * @author Xiu Huan
 */
import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.Stack;

public class ReverseTicTacToe {
    private static char[][] board = new char[3][3];
    private static char player = 'X';
    private static char computer = 'O';
    private static int numMoves = 0;
    static test engine = new test();
    private static int round =1;
    private static int playermark =0;
    private static int enginemark =0;

    private static Stack<int[]> moves = new Stack<>();
    private static PlayerAccount playerAccount;

    public ReverseTicTacToe(PlayerAccount playerAccount) {
        this.playerAccount = playerAccount;
    }

    public boolean playgame(){
        printInstructions();
        char currentPlayer = player;
        while(true){
            initializeBoard();
            numMoves =0;
            System.out.println("\nRound " + round);
            boolean endRound = false;

            while (!endRound) {

                double[] probabilities = getWinProbability();
                System.out.println("Player's win probability: " + probabilities[0] * 100 + "%");
                System.out.println("Engine's win probability: " + probabilities[1] * 100 + "%");

                printBoard();
                int[] move = getValidMove(currentPlayer);
                board[move[0]][move[1]] = currentPlayer;
                numMoves++;

                if (checkWin(currentPlayer)) {
                    printBoard();
                    currentPlayer = switchPlayer(currentPlayer);

                    if(currentPlayer =='X'){
                        playermark++;
                        System.out.println(currentPlayer + " wins!");
                    }
                    else{
                        enginemark++;
                        System.out.println("Engine wins!");
                    }
                    System.out.println(playerAccount.getUsername()+"\t:\tEngine");
                    System.out.println(playermark + "\t:\t" + enginemark);
                    endRound = true;
                    round ++;

                    playerAccount.updateLeaderboard(playerAccount.getUsername(), playermark);
                    playerAccount.updateLeaderboard("Engine", enginemark);

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
    }

    private static void printInstructions() {
        System.out.println("Welcome to Reverse Tic-Tac-Toe!");
        System.out.println("The rules are simple: the loser is the first player to place 3 of their shape in either a horizontal, vertical, or diagonal row.");
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

    private static int[] getValidMove(char currentPlayer) {
        Scanner scanner = new Scanner(System.in);
        int row = -1;
        int col = -1;
        boolean validMove = false;

        if(currentPlayer == 'X'){
            while (!validMove) {
                try {

                    if (!moves.isEmpty()){
                        System.out.println("Do you want to take back a move? (1: Yes, Other Numbers: No)");
                        if (scanner.nextInt() == 1){
                            takeBackMove();
                            continue;
                        }
                    }else {
                        System.out.println(" No more moves left to be taken back, please make a move");
                    }

                    System.out.print(playerAccount.getUsername()+"'s turn.Enter your move (row[1-3] column[1-3]): ");
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
        moves.push(move);
        return move;
    }

    public boolean checkWin(char currentPlayer) {
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

    private static double[] getWinProbability() {
        int playerScore = getScore(player);
        int engineScore = getScore(computer);

        int totalScore = playerScore + engineScore;

        double playerProbability = (double) playerScore / totalScore;
        double engineProbability = (double) engineScore / totalScore;

        return new double[]{playerProbability, engineProbability};
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
}

