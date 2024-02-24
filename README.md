# java-tictactoe
Server based tic-tac-toe game made using java swing and networking packages

Tic Tac Toe Server
This is a server-based Tic Tac Toe game implemented in Java. It allows multiple clients to connect and play the game over a network.

How to Run the Game
To run the Tic Tac Toe server, follow these steps:

Compile the Java files:

Copy: 
javac RunServer.java

Start the server:

Copy
java RunServer

The server will start listening for client connections on port 40003.

Clients can now connect to the server and play the game.

How to Run the Client

To run the Tic Tac Toe client, follow these steps:

Compile the Java files:

Copy
javac Client.java
Start the client:

Copy
java Client
The client will connect to the server and display the game GUI.

Follow the on-screen instructions to play the game.

Game Rules
The Tic Tac Toe game follows the standard rules:

The game is played on a 3x3 grid.
Two players take turns marking empty squares on the grid with their respective symbols (X or O).
The first player to get three of their symbols in a horizontal, vertical, or diagonal line wins the game.
If all squares are filled and no player has won, the game ends in a draw.
File Structure
The project consists of the following files:

RunServer.java: This file contains the main class to start the Tic Tac Toe server. It listens for client connections and manages the server's lifecycle.

Server.java: This file contains the server-side logic of the game. It handles client communication, manages the game board, and coordinates the game flow.

SharedBoard.java: This file represents the game board shared by the clients. It implements the logic behind the game, tracks the current player's turn, and updates the board with player moves.

Client.java: This file contains the main class to start the Tic Tac Toe client. It creates the game GUI and connects to the server.

View.java: This file represents the graphical user interface (GUI) of the game. It displays the game board and handles user input.

Controller.java: This file manages the game flow and communication with the server. It handles user actions, updates the GUI, and sends instructions to the server.

Dependencies

The Tic Tac Toe server and client are implemented using Java's standard libraries. No external dependencies are required.
