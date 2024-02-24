import java.io.IOException;
import java.net.ServerSocket;

/**
 * The RunServer class is responsible for starting the Tic Tac Toe server.
 * It creates an instance of the Server class, listens for client connections,
 * and manages the server's lifecycle.
 * 
 * The server is started by creating a ServerSocket and passing it to the Server class.
 * It prints a "Server is Running..." message when the server starts, and a "Server Stopped."
 * message when the server is shut down.
 * 
 * The RunServer class also provides a main method to run the server as a standalone application.
 * It catches and prints any exceptions that occur during the server's execution.
 * 
 * @author Farhan Yusuf Khan
 * @version 1.0
 * @since 2023-12-07
 */
public class RunServer {
    /**
     * The main method that starts the Tic Tac Toe server.
     * It creates a ServerSocket, initializes the server, and listens for client connections.
     * 
     * @param args command-line arguments (not used)
     * @throws IOException if an error occurs when creating the ServerSocket or accepting client connections
     */
	public static void main(String[] args) throws IOException {
		System.out.println("Server is Running...");
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			public void run() {
				System.out.println("Server Stopped.");
			}
		}));

		try (ServerSocket listener = new ServerSocket(40003)) {
			Server myServer = new Server(listener);
			myServer.start();
		} catch (Exception e) {	
			System.out.println(e.getMessage());
		}
	}
}