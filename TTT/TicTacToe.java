/**
 *
 * @author Xiu Huan
 */
import java.util.Random;

public class TicTacToe {
    public static void main(String[] args){
        TicTacToeRegular game1= new TicTacToeRegular();
        ReverseTicTacToe game2= new ReverseTicTacToe();
        Treblecross game3= new Treblecross();

        Random r = new Random();
        int numGame = r.nextInt(3);
        if(numGame == 0)
            game1.playgame() ;
        else if (numGame == 1)
            game2.playgame() ;
        else
            game3.playgame()  ;
    }
}
