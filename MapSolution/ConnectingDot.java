import java.util.*;

public class ConnectingDot {
    public static void main(String[] args) {
        MapDecipher comMap = new MapDecipher();
        comMap.imageInputAndConvert();

        int[][] copyMap = comMap.completeMap();

        List<List<String>> allShortestPaths = BFSAllShortestPaths.findAllShortestPaths(comMap.completeMap());

        Random r = new Random();
        List<String> path = allShortestPaths.get(r.nextInt(allShortestPaths.size())); // Randomly choose a path

        System.out.println("The chosen shortest path is: \n" + path);

        int currentRow = 0;
        int currentCol = 0;
        int numStation = 0;
        Stack<Integer> previousStationRows = new Stack<>();
        Stack<Integer> previousStationCols = new Stack<>();

        Stack<String> pathToCurrentStation = new Stack<>();

        ListIterator<String> iter = path.listIterator();

        while(iter.hasNext()) {
            String direction = iter.next();
            pathToCurrentStation.push(direction);
            switch (direction) {
                case "Up":
                    currentRow--;
                    break;
                case "Down":
                    currentRow++;
                    break;
                case "Left":
                    currentCol--;
                    break;
                case "Right":
                    currentCol++;
                    break;
            }

            if (copyMap[currentRow][currentCol] == 2) { // if the new position is a station
                numStation++;
                System.out.println("\nYou have reached a station! Let's play a game.");
                System.out.println("You are currently in the station number: " + numStation + "\n");

                TicTacToe game = new TicTacToe();

                boolean win = game.playGame();
                game.getPlayerAccount().displayLeaderboard();
                game.getPlayerAccount().saveLeaderboard();

                if (!win) {
                    System.out.println("Sorry, you have lost the game at this station");
                    System.out.println("You have to fall back to the previous station");
                    System.out.println("------------------------------------------------------xxxxxxxxxxxxxxxxxxxxxxxx------------------------------------------------------------------");

                    numStation-=2;

                    // If player loses, they will fall back to the previous station
                    if (!previousStationRows.empty() && !previousStationCols.empty()) {
                        // Fall back and reprocess the station
                        while(iter.hasPrevious()) {
                            String revertDirection = iter.previous();
                            switch (revertDirection) {
                                case "Up":
                                    currentRow++;
                                    break;
                                case "Down":
                                    currentRow--;
                                    break;
                                case "Left":
                                    currentCol++;
                                    break;
                                case "Right":
                                    currentCol--;
                                    break;
                            }
                        }

                        /*
                         Since at the beginning of the first while loop we will update the points, so we need to go back
                         to the previous station's last location
                         */
                        if (currentRow == previousStationRows.peek() && currentCol == previousStationCols.peek() - 1 ||
                                currentRow == previousStationRows.peek() - 1 && currentCol == previousStationCols.peek() ||
                                currentRow == previousStationRows.peek() && currentCol == previousStationCols.peek() + 1 ||
                                currentRow == previousStationRows.peek() + 1 && currentCol == previousStationCols.peek()) {
                            break;
                        }
                        previousStationRows.pop();
                        previousStationCols.pop();
                    } else {
                        System.out.println("You lost at the first station. Game over.");
                        return;
                    }
                } else {
                    previousStationRows.push(currentRow);
                    previousStationCols.push(currentCol);
                    System.out.println("Congratulations! You have won the current station game, please proceed to the next station\n");
                    System.out.println("------------------------------------------------------xxxxxxxxxxxxxxxxxxxxxxxx------------------------------------------------------------------");
                }
            }

            if (copyMap[currentRow][currentCol] == 3) { // if the new position is the final destination
                System.out.println("Congratulations! You have reached your final destination!");
                break;
            }
        }
    }

}