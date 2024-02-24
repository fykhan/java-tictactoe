import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The Server class represents the server-side of the Tic Tac Toe game.
 * It listens for client connections, handles communication between clients,
 * and manages the game logic.
 * 
 * The server uses the Handler class to handle communication with each client.
 * It maintains a list of active client connections and manages the game board
 * using an instance of the SharedBoard class.
 * 
 * The server accepts two client connections, assigns a Handler to each client using the Runnable interface,
 * and starts the game when both clients are ready. It receives player moves,
 * updates the game board, checks for a win or draw, and sends game updates
 * to the clients.
 * 
 * The SharedBoard class represents the game board shared by the clients.
 * It keeps track of the current player's turn, updates the board with player moves,
 * and checks for a win or draw.
 * 
 * @author Farhan Yusuf Khan
 * @version 1.0
 * @since 2023-12-07
 */
public class Server {
	//Server implementation
	private ServerSocket serverSock;
	private SharedBoard board;
	private int readyCount = 0;
	private int clientCount=0;
	
	private ArrayList<PrintWriter> writers = new ArrayList<PrintWriter>();
    /**
     * Constructs a Server object with the specified ServerSocket.
     * 
     * @param serverSock the ServerSocket to use for accepting client connections
     */
	public Server(ServerSocket serverSock) {
		this.serverSock = serverSock;
	}
    /**
     * Starts the server by accepting client connections and assigning a Handler to each client.
     * The server waits until two clients are connected and ready to start the game.
     */
	public void start() {
		ExecutorService pool = Executors.newFixedThreadPool(200);
		while (clientCount < 2) {
			try {
				Socket socket = serverSock.accept();
				pool.execute(new Handler(socket));
				clientCount++;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
    /**
     * The Handler class represents a client connection handler.
     * It handles communication with a specific client, receives player moves,
     * updates the game board, and sends game updates to the clients.
     */
	public class Handler implements Runnable {
		private Socket socket;
		private Scanner input;
		private PrintWriter output;
        /**
         * Constructs a Handler object with the specified Socket implementing the Runnable interface.
         * 
         * @param socket the Socket representing the client connection
         */
		public Handler(Socket socket) {
			this.socket = socket;
		}

		@Override
		public void run() {
			System.out.println("Connected: "+ socket);
			try {
				input =  new Scanner(socket.getInputStream());
				output = new PrintWriter(socket.getOutputStream(), true);
				
				writers.add(output);
				
				while (input.hasNextLine()) {
					String command = input.nextLine();
					System.out.println("Server Received: "+ command);
					
					if (command.startsWith("START")) {
						readyCount++;
						if (readyCount == 2) {
							board = new SharedBoard();
							for (PrintWriter writer: writers) {
								writer.println("RESET");
							}
							writers.get(0).println("STARTGAME");
							readyCount = 0;
						}
					}
					if (command.startsWith("CLICKED")) {
						char i = command.charAt(7);
						char j = command.charAt(8);
						
						board.setBoard(i - '0', j - '0');
						
						for (PrintWriter writer: writers) {
							System.out.println(("MOVED"+i+j+board.getTurn()));
							writer.println("MOVED"+i+j+board.getTurn());
						}
						if (board.checkWin(board.getTurn()) == 'X') {
							writers.get(0).println("WIN");
							writers.get(1).println("LOSE");
						}
						else if (board.checkWin(board.getTurn()) == 'O') {
							writers.get(0).println("LOSE");
							writers.get(1).println("WIN");
						}
						else if (board.checkWin(board.getTurn()) == 'D') {
							writers.get(0).println("DRAW");
							writers.get(1).println("DRAW");
						}
						else {
							board.changeTurn();
							System.out.println(board.getTurn() + "'s turn.");
							if (board.getTurn().equals("X")) {
								writers.get(0).println("TURN");
							}
							else {
								writers.get(1).println("TURN");
							}
						}						
					}

				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}finally {
				if (output != null) {
					writers.remove(output);
					for (PrintWriter writer: writers) {
						writer.println("DISCONNECT");
					}
					System.out.println("Client disconnected. Current client count: "+ clientCount);
				}
			}
			
		}
	}
}
