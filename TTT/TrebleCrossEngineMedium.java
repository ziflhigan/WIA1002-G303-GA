import java.util.Random;

public class TrebleCrossEngineMedium implements Engine{

    Random random = new Random();

    public int getMove(char[] board, String currentPlayer) {
        // Check for a winning opportunity
        for (int i = 0; i < 9-2; i++) {
            if ((board[i] == 'X' && board[i + 1] == 'X' && board[i + 2] == '-') ||
                    (board[i] == 'X' && board[i + 1] == '-' && board[i + 2] == 'X') ||
                    (board[i] == '-' && board[i + 1] == 'X' && board[i + 2] == 'X')) {
                return i + 2;
            }
        }

        // If no immediate win, try to avoid creating two 'X's in a sequence, unless there is no other option
        boolean foundMove = false;
        int move = -1;
        for (int i = 0; i < 9-2; i++) {
            if (board[i] == '-' && board[i + 1] == '-' && board[i + 2] == '-') {
                move = i;
                foundMove = true;
                break;
            }
        }

        // If no safe move was found, make a random move
        if (!foundMove) {
            do {
                move = random.nextInt(9);
            } while (board[move] != '-');
        }

        return move;
    }
}
