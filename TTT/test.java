/**
 *
 * @author Xiu Huan
 */
import java.util.Random;
public class test {
    public int[] getinput(char symbol, char[][] board, int boardSize){
        Random r = new Random();
        boolean validMove = false;
        int row=-1;
        int col=-1;

        while(!validMove){
            row = r.nextInt(boardSize);
            col = r.nextInt(boardSize);
            if (board[row][col] == '-'){
                validMove = true;
            }
        }
        int[] move = {row, col};
        return move;
    }
}
