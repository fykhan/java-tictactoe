# java-tictactoe

Server-based Tic Tac Toe game made using Java Swing and networking packages.

## Tic Tac Toe Server

This is a server-based Tic Tac Toe game implemented in Java. It allows multiple clients to connect and play the game over a network.

### How to Run the Game

To run the Tic Tac Toe server, follow these steps:

1. **Compile the Java files:**
    ```
    javac RunServer.java
    ```

2. **Start the server:**
    ```
    java RunServer
    ```

The server will start listening for client connections on port 40003.

Clients can now connect to the server and play the game.

## How to Run the Client

To run the Tic Tac Toe client, follow these steps:

1. **Compile the Java files:**
    ```
    javac Client.java
    ```

2. **Start the client:**
    ```
    java Client
    ```

The client will connect to the server and display the game GUI. Follow the on-screen instructions to play the game.

## Game Rules

The Tic Tac Toe game follows the standard rules:

- The game is played on a 3x3 grid.
- Two players take turns marking empty squares on the grid with their respective symbols (X or O).
- The first player to get three of their symbols in a horizontal, vertical, or diagonal line wins the game.
- If all squares are filled, and no player has won, the game ends in a draw.

## File Structure

The project consists of the following files:

- `RunServer.java`: Main class to start the Tic Tac Toe server.
- `Server.java`: Server-side logic of the game.
- `SharedBoard.java`: Represents the shared game board.
- `Client.java`: Main class to start the Tic Tac Toe client.
- `View.java`: Represents the GUI of the game.
- `Controller.java`: Manages the game flow and communication with the server.

## Dependencies

The Tic Tac Toe server and client are implemented using Java's standard libraries. No external dependencies are required.
