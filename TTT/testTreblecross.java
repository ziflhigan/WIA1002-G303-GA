/**
 *
 * @author Xiu huan
 */
import java.util.Random;
public class testTreblecross {

    public int getinput(String currentPlayer, char[] board, int boardSize){
        Random r = new Random();
        boolean validMove = false;
        int row=-1;

        while(!validMove){
            row = r.nextInt(boardSize);
            if (board[row] == '-'){
                validMove = true;
            }
        }
        int move = row;
        return move;
    }
}
