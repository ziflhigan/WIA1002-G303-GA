# WIA1002 Data Structure Group Assignment 

## Assignment Topic 4: Suzume plays Tic Tac Toe

Group Members:

| Name                      | Matric Number |
|---------------------------|:-------------:|
| Zhili Fang                |   S2189538    |
| Wong Xiu Huan             |   22004834    |
| Calvin Jee Meng Yau       |   22057003    |
| Tan Jin Khye              |   22004758    |
| Ikmal Hakim Bin Radzali   |   U2101682    |

## Table of Contents

- [Introduction](#introduction) 💡
- [Features](#features) ✨
  - [Map merging](#map-merging) 🌍
  - [Pathfinding algorithms](#pathfinding-algorithms) 🧭
  - [Tic Tac Toe games](#tic-tac-toe-games) ❌⭕
  - [Shortest path analysis](#shortest-path-analysis) 📈
  - [Station challenges](#station-challenges) 🏞️
- [Extra Features](#extra-features) 🌟
  - [GUI (Displaying the Complete Map along with all the possible shortest paths)](#gui-displaying-the-complete-map-along-with-all-the-possible-shortest-paths) 🖥️
  - [Win Probability Calculation](#win-probability-calculation) 🎲📊
  - [Take Back Move](#take-back-move) ⏪
  - [Password Encryption](#password-encryption) 🔒
  - [Engine Optimization (For 5 * 5 board)](#engine-optimization-for-5--5-board) ⚙️
  - [Different Modes (PVP and PVE)](#different-modes-pvp-and-pve) 🎮
- [Report](#report) 📄
- [Feedback](#feedback) 📝




## Introduction
In this project, we aimed to help Suzume, a young adventurer, navigate through a map filled with various obstacles and challenges. The map, represented as a grid, consists of open paths, obstacles, stations, and a final destination. Suzume's ultimate goal is to start from the top-left corner of the grid and reach the final destination within the shortest time.

However, initially, we only got four map pieces and a map template that contains the number of possible paths to reach the final destination for the map pieces while passing through three stations. We need to first satisfy the requirement of the map template, then only we can merge the map pieces to form a complete map according to it. After that, Suzume also required us to figure out the possible paths for the complete map while passing through four stations. Then, finally, Suzume acknowledged our work, and we started to find the shortest paths for her.

To accomplish this, we implemented various pathfinding algorithms, including depth-first search (DFS) and breadth-first search (BFS).

While we were still working with the map, we got caught by mysterious creatures, Suzume’s enemies, and they required us to develop three games as their station missions, for which Suzume would have to play against and win those games. We then decided to implement three Tic Tac Toe (TTT) variants with all the necessary functions.

Finally, after completing the TTT games and the formation of the complete map, we provided Suzume with a detailed list of the shortest paths, including the number of steps and directions needed for each path. This allows her to choose the most favorable route, considering the unique conditions and challenges she may face during her adventure. For the shortest path she has chosen, whenever she reached the stations, she would need to play a randomized TTT game and need to succeed in all of the stations.

## Features 
### Map merging 
- Combine multiple map pieces to create a complete map based on a given template.

### Pathfinding algorithms 
- Utilize DFS and BFS to determine possible paths to the final destination, accounting for stations along the way.

### Tic Tac Toe games 
- Develop three TTT variants as station missions for Suzume's enemies.

### Shortest path analysis 
- Provide a detailed list of the shortest paths, including step counts and directions.

### Station challenges 
- Engage in randomized TTT games at the stations to progress in the adventure.

## Extra Features 
- In addition to the core functionalities of the project, we implemented several extra features:

### GUI (Displaying the Complete Map along with all the possible shortest paths) 
  - The MapPanel class extends JPanel and provides a custom panel that visualizes the complete map and the shortest paths found using the BFS algorithm.
  - The drawArrow method draws arrows on the panel to represent the directions of the shortest paths.
  - The paintComponent method is called by the Swing framework to render the panel and handles the drawing of cells, colors, outlines, cell values, and arrows.
### Win Probability Calculation 
  - The getWinProbability method computes the probabilities of each player winning the game based on the current board state.
  - It calculates the scores for each player by evaluating potential winning lines (rows, columns, and diagonals) on the game board.
  - The getLineScore method checks each character in a line and increments the score counter for the current player.
  - The scores are then summed and divided by the total score to calculate the win probability for each player.
### Take Back Move 
  - The takeBackMove method allows the player to undo one or more moves made in the game.
  - It relies on two stacks, historyMoveRow and historyMoveCol, to store the row and column indices of each move in the order they were made.
  - When called, the method removes the top element from each stack, retrieves the corresponding indices, and sets the board cell back to its initial empty state.
  - The numMoves counter is decremented to reflect the reduction in the total number of moves made.
### Password Encryption 
  - The bytesToHex method converts a byte array into a hexadecimal string, typically used for hashing functions' output.
  - The hashPassword method hashes a password using the SHA-256 hash function.
  - It utilizes the MessageDigest class in Java to create a MessageDigest object and compute the hash of the password.
  - The output is converted to a hexadecimal string using bytesToHex before returning.
### Engine Optimization (For 5 * 5 board) 
  - Alpha-beta pruning is implemented as an optimization technique in the traditional minimax algorithm for the Tic Tac Toe game.
  - It reduces the computation time by eliminating branches of the game tree that don't need to be explored.
  - The algorithm keeps track of the best scores for the maximizing and minimizing players at each level of the game tree and prunes unnecessary branches.
  - Depth limiting strategy is also implemented, allowing the algorithm to limit the number of branches it explores, providing a trade-off between accuracy and computational time.
### Different Modes (PVP and PVE) 
  - The project supports two game modes: Player vs. Player (PVP) and Player vs. Engine (PVE).
  - In PVE mode, the player selects a difficulty level (easy, medium, or hard) for the engine, and the game proceeds with the player and the engine taking turns.
  - In PVP mode, the game proceeds with two players taking turns.

These extra features enhance the user experience and provide additional functionality and customization options to the project.

## Report 
Please dive into the captivating journey of our project and uncover the secrets behind Suzume's adventure. Discover the challenges, strategies, and triumphs that await you in our detailed report. Explore the report [here](https://drive.google.com/file/d/15FE7fc_MORam7LX3eAI7Biq_jCymL89W/view?usp=sharing).

## Feedback
We welcome any feedback or suggestions regarding the project. If you have any comments, questions, or ideas, please feel free to reach out to us. Your input is valuable and will help us improve our work.

Thank you for your support! ✨