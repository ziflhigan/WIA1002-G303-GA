/**
 *
 * @author Xiu Huan
 */
import GameEngine.DifficultEngine;

import java.util.Random;
import java.util.Scanner;

public class TicTacToe {
    public TicTacToeRegular game1;
    public ReverseTicTacToe game2;
    public Treblecross game3;
    private PlayerAccount playerAccount;

    public TicTacToe(){
        Scanner in = new Scanner(System.in);
        playerAccount = new PlayerAccount();
        game1 = new TicTacToeRegular(playerAccount);
        game2 = new ReverseTicTacToe(playerAccount);
        game3 =  new Treblecross(playerAccount);
        System.out.println("Sign Up or Log In? (0 : Sign Up, 1 : Log In)");
        int num = in.nextInt();
        in.nextLine();
        if(num == 0) {
            playerAccount.createAccount();
        }
        else if(num == 1) {
            playerAccount.login();
        }
        System.out.println();
        playerAccount.loadLeaderboard();
    }

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

    public PlayerAccount getPlayerAccount() {
        return playerAccount;
    }
}
