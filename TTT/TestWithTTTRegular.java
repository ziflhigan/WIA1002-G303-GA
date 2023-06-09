import java.util.Scanner;

public class TestWithTTTRegular {
    public static void main(String[] args) {
        PlayerAccount playerAccount = new PlayerAccount();
        System.out.println("Sign Up or Log In? (0 : Sign Up, 1 : Log In)");
        // Scanner in = new Scanner(System.in);
        // int num = in.nextInt();
        // in.nextLine();
        int num=1;
        if(num == 0) {
            playerAccount.createAccount();
        }
        else if(num == 1) {
            playerAccount.login();
        }
        System.out.println();
        playerAccount.loadLeaderboard();

    }

}
