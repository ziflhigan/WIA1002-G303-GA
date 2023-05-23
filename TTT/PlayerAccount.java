/**
 * @Author Ikmal
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class PlayerAccount {
    private static String username;
    private static String password;
    private static List<Player> leaderboard;
    private static List<PlayerAccount> accounts = new ArrayList<>();
    static Scanner in = new Scanner(System.in);


    public PlayerAccount() {
        leaderboard = new ArrayList<>();
    }


    public PlayerAccount(String name, String enteredPassword){
        name = username;
        enteredPassword = password;
    }

    @Override
    public String toString() {
        return "PlayerAccount [getUsername()=" + getUsername() + ", getPassword()=" + getPassword() + "]";
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String name) {
        username = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        PlayerAccount.password = password;
    }


    public static void createAccount() {

        System.out.print("Username: ");
        username = in.next();

        System.out.print("Password: ");
        password = in.next();

        leaderboard.add(new Player(username, 0));
        System.out.println("Account created successfully.");
        saveAccount(username, password); // Save the account into txt file


    }

    public static void login() {

        System.out.print("Username: ");
        String username = in.next();

        System.out.print("Password: ");
        String enteredPassword = in.next();

        loadAccount(username, enteredPassword); // Access account in the txt file


    }

    public static void saveAccount(String username, String password) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("player_account.txt",true))) {
            writer.println(username);
            writer.println(password);
            writer.println(leaderboard.size());
            System.out.println("Player account saved successfully!");
        } catch (IOException e) {
            System.out.println("Error saving the player account.");
        }
    }

    public static void loadAccount(String username, String enteredPassword) {
        try (BufferedReader reader= new BufferedReader(new FileReader("player_account.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String name = line;
                String password = reader.readLine();

                PlayerAccount account = new PlayerAccount(name, password);
                account.setUsername(name);
                accounts.add(account);
                //System.out.println(account.toString());

                if (account.getUsername().equals(username) && password.equals(enteredPassword)) {
                    System.out.println("Login successful!");
                    return;
                }
            }
            System.out.println("The username and/or password is invalid. Please try again.");
            login();

        } catch (IOException e) {
            System.out.println("Error loading the player account.");
        }
    }
    public void updateLeaderboard(String name, int score) {
        for (Player player : leaderboard) {
            if (player.getUsername().equals(name)) {
                player.setScore(player.getScore() + score);
                return;
            }
        }

        leaderboard.add(new Player(name, score));
    }

    public void displayLeaderboard() {
        System.out.println("Leaderboard:");
        // Sort the leaderboard in descending order based on the scores
        leaderboard.sort(Comparator.comparingInt(Player::getScore).reversed());
        for (Player player : leaderboard) {
            System.out.println(player.getUsername() + ": " + player.getScore());
        }
    }

    public void saveLeaderboard() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("playerLeaderboard.txt"),true)) {
            for (Player player : leaderboard) {
                writer.println(player.getUsername());
                writer.println(player.getScore());
            }
            System.out.println("Leaderboard saved successfully!");
        } catch (IOException e) {
            System.out.println("Error saving the leaderboard.");
        }
    }

    public static void loadLeaderboard() {
        try (Scanner scanner = new Scanner(new FileReader("playerLeaderboard.txt"))) {
            while (scanner.hasNextLine()) {
                String name = scanner.nextLine();
                int score = Integer.parseInt(scanner.nextLine());
                leaderboard.add(new Player(name, score));
            }
            System.out.println("Leaderboard loaded successfully!");
        } catch (IOException e) {
            System.out.println("Error loading the leaderboard.");
        }
    }
}

class Player {
    private String username;
    private String password;
    private int score;

    public Player(String username, int score) {
        this.username = username;
        this.score = score;
    }
    public Player(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getUsername() {
        return username;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}