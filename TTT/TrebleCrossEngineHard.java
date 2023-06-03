public class TrebleCrossEngineHard implements Engine{
    @Override
    public int getMove(char[] board, String currentPlayer) {
        int move = -1;

        // Check if there's a move to block the opponent from winning or to win the game
        for (int i = 0; i < 9-2; i++) {
            if ((board[i] == 'X' && board[i+1] == 'X' && board[i+2] == '-')
                    || (board[i] == 'X' && board[i+1] == '-' && board[i+2] == 'X')
                    || (board[i] == '-' && board[i+1] == 'X' && board[i+2] == 'X')) {
                if(board[i] == '-') {
                    move = i;
                } else if(board[i+1] == '-') {
                    move = i+1;
                } else if(board[i+2] == '-') {
                    move = i+2;
                }
                return move;
            }
        }

        // If there's no immediate threat or win, try to create a sequence of 'X'-'-'-'X' only if it's the engine's turn
        if(currentPlayer.equals("Engine")) {
            for (int i = 0; i < 9-2; i++) {
                if (board[i] == '-' && board[i+1] == '-' && board[i+2] == '-') {
                    if (board[i] == '-' && board[i+2] == 'X') { // If there is already an 'X' in the third spot
                        move = i;
                    } else { // Otherwise, place the 'X' in the third spot
                        move = i+2;
                    }
                    return move;
                }
            }
        }

        // If there's no optimal move, make the first available move
        for (int i = 0; i < 9; i++) {
            if (board[i] == '-') {
                move = i;
                return move;
            }
        }

        // If no valid move, throw an exception
        if (move == -1) {
            throw new IllegalStateException("No valid moves left.");
        }

        return move;
    }

}
