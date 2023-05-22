/**
 *
 * @author Xiu Huan
 */
import java.util.Random;

public class TicTacToe {
    public TicTacToeRegular game1 = new TicTacToeRegular();
    public ReverseTicTacToe game2 = new ReverseTicTacToe();
    public Treblecross game3 = new Treblecross();

    public boolean playGame() {
        Random r = new Random();
        int numGame = r.nextInt(3);
        if (numGame == 0) {
            return game1.playgame();
        } else if (numGame == 1) {
            return game2.playgame();
        } else {
            return game3.playgame();
        }
    }
}
