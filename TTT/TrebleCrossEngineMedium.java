public class TrebleCrossEngineMedium implements Engine{
    @Override
    public int getMove(char[] board, String currentPlayer) {
        // Check for winning move
        for (int i = 0; i < 9-2; i++) {
            if (board[i] == 'X' && board[i + 1] == 'X' && board[i + 2] == '-') {
                return i + 2;
            }
        }

        // Check for blocking player's winning move
        for (int i = 0; i < 9-2; i++) {
            if (board[i] != '-' && board[i] == board[i + 1] && board[i + 2] == '-') {
                return i + 2;
            }
        }

        // If there's no winning or blocking move, make a random move
        for (int i = 0; i < 9; i++) {
            if (board[i] == '-') {
                return i;
            }
        }

        throw new IllegalStateException("No valid moves left.");
    }
}
