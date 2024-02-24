import java.io.IOException;

import javax.swing.SwingUtilities;
/**
 * The Client class is responsible for starting the Tic Tac Toe client application.
 * It creates an instance of the View and Controller classes, and starts the client.
 * 
 * The client application is started by invoking the run method in the Event Dispatch Thread
 * using the SwingUtilities.invokeLater method. This ensures that the GUI components are
 * created and updated in the correct thread.
 * 
 * The Client class also provides a main method to run the client as a standalone application.
 * It catches and prints any exceptions that occur during the client's execution.
 * 
 * @author Farhan Yusuf Khan
 * @version 1.0
 * @since 2023-12-07
 */
public class Client {
	public static void main(String[] args) throws IOException {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				View view = new View();
				Controller controller = new Controller(view);
				controller.start();
			}
		});
	}
}
