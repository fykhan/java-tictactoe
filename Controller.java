import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import javax.swing.JButton;

/**
 * The Controller class is responsible for managing the game flow and communication with the server.
 * It handles user actions, updates the GUI, and sends instructions to the server.
 * 
 * This class establishes a connection with the server, initializes listeners for user actions,
 * and starts a client handler thread to handle communication with the server.
 * 
 * Instance Variables:
 * - game: An instance of the View class representing the GUI.
 * - buttonClickListener: ActionListener for handling button clicks on the game board.
 * - submitListener: ActionListener for handling the submit button click to start the game.
 * - exitMenuItemListener: ActionListener for handling the exit menu item click to exit the game.
 * - socket: The Socket used for communication with the server.
 * - in: Scanner used for reading input from the server.
 * - out: PrintWriter used for sending output to the server.
 * 
 * The Controller class interacts with the View class to update the GUI and with the server for communication.
 * It also includes a nested ClientHandler class that runs as a separate thread to handle server communication.
 * 
 * @author Farhan Yusuf Khan
 * @version 1.0
 * @since 2023-12-07
 */
public class Controller {
	private View game;
	private ActionListener ButtonClickListener;
	private ActionListener SubmitListener;
	private ActionListener ExitMenuItemListener;
	private Socket socket;
	private Scanner in;
	private PrintWriter out;
    /**
     * Constructs a new Controller object.
     * 
     * @param game an instance of the View class for the GUI
     */
	public Controller(View game) {
		this.game = game;
	}
    /**
     * Starts the game and establishes a connection with the server.
     */
	public void start() {
		try {
			this.socket = new Socket("127.0.0.1", 40003);
			this.in = new Scanner(socket.getInputStream());
			this.out = new PrintWriter(socket.getOutputStream(), true);
		} catch(UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        // Initialize listeners		
		ExitMenuItemListener = new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		};
		
		game.getExitMenu().addActionListener(ExitMenuItemListener);

		SubmitListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String playerName = game.getName();
				if (!playerName.isEmpty()) {
					game.setSubmit(false);
					game.setName(false);
					game.setFrame(playerName);
					game.setText("WELCOME "+playerName);
					out.println("START");
				}
			}
		};
		game.getSubmit().addActionListener(SubmitListener);
		ButtonClickListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JButton button = (JButton) e.getSource();
				for (int i = 0; i<3; i++) {
					for (int j = 0; j<3; j++) {
						if (button == game.getBoard()[i][j]) {
							if (button.getText().isEmpty()) {

								out.println("CLICKED"+i+j);
							}
							break;
						}
					}
				}			
				
			}
		};
        // Add button listeners
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j<3; j++) {
				game.getBoard()[i][j].addActionListener(ButtonClickListener);
			}
		}
        // Start the client handler thread		
		Thread handler = new ClientHandler(socket);
		handler.start();
	}
    /**
     * A thread that handles communication with the server.
     */
	class ClientHandler extends Thread{
		private Socket socket;
        /**
         * Constructs a new ClientHandler object.
         * 
         * @param socket the socket used for communication with the server
         */
		public ClientHandler(Socket socket) {
			this.socket = socket;
		}
        /**
         * Runs the client handler thread.
         */
		@Override
		public void run() {
			try {
				readFromServer();
			} catch (Exception e) {e.printStackTrace();}
		}
		
		public void readFromServer() throws Exception {
			try {
				while (in.hasNextLine()) {
					String command = in.nextLine();
					System.out.println("Client Received: "+ command);
					
					if (command.startsWith("MOVED")) {
						char i = command.charAt(5);
						char j = command.charAt(6);
						String player = Character.toString(command.charAt(7));
						game.updateBoard(i - '0', j - '0', player);
						game.setText("Valid move, wait for your opponent");
					}
					else if (command.startsWith("STARTGAME")) {
						System.out.println("Game started on client: 1");
						game.setBoard(true);
					}
					else if (command.startsWith("TURN")) {
						game.setBoard(true);
						game.setText("Your opponent has moved, now it is your turn");
					}
					else if (command.startsWith("RESET")) {
						game.resetBoard();
						game.setText("WELCOME "+game.getName());
					}
					else if (command.startsWith("WIN") | command.startsWith("DRAW") | command.startsWith("LOSE")) {
						Boolean playAgain;
						if (command.startsWith("WIN")) {
							playAgain = game.playAgainMessage("Congratulations! You Win. Do you want to play again?");
						}
						else if (command.startsWith("LOSE")) {
							playAgain = game.playAgainMessage("You lose. Do you want to play again?");
						}
						else {
							playAgain = game.playAgainMessage("Draw. Do you want to play again?");
						}
						
						if (playAgain) {
							game.resetBoard();
							out.println("START");
						}
						else {
							out.println("DISCONNECT");
							System.exit(0);
						}
					}
					else if (command.startsWith("DISCONNECT")) {
						game.disconnectMessage();
						System.exit(0);
					}
					out.flush();
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {socket.close();}
		}
	}
}
