import java.util.Random;

public class TrebleCrossEngineEasy implements Engine{
    @Override
    public int getMove(char[] board, String currentPlayer){
        Random r = new Random();
        boolean validMove = false;
        int row=-1;

        while(!validMove){
            row = r.nextInt(9);
            if (board[row] == '-'){
                validMove = true;
            }
        }
        int move = row;
        return move;
    }

}
