import java.awt.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

/**
 * The View class initializes the graphical user interface (GUI) of the application.
 * It includes elements for submitting the player's name, displaying the Tic Tac Toe board,
 * and showing instructions and message dialogs.
 * 
 * The View class has the following instance variables:
 * - board: an array of JButtons representing the Tic Tac Toe grid
 * - frame: the main JFrame where all elements are set
 * - quit: the JMenuItem used to quit the game
 * - submit: the JButton used to submit the player's name
 * - text: the JLabel used to display messages for the player
 * - menubar: the JMenuBar containing the menu items
 * - name: the JTextField used for getting the player's name input
 * 
 * 
 * This View class helps create and manage the graphical user interface for the Tic Tac Toe game.
 * It provides methods to update the board, display messages, handle player input, and interact with the game logic.
 * The GUI elements are created using Java Swing components such as JFrame, JButton, JLabel, JMenuBar, etc.
 * The View class works in conjunction with the Controller classe to implement the game functionality.
 * 
 * @author Farhan Yusuf Khan
 * @version 1.0
 * @since 2023-12-7
 */

public class View {
	private JButton[][] board;
	private JFrame frame;
	private JMenuItem quit;
	private JButton submit;
	private JLabel text;
	private JMenuBar menubar;
	private JTextField name;
	
	/**
	 * Constructor method that starts the initialization of the GUI by calling initUI().
	 */
	public View() {
		initUI();
	}
	/**
	 * Initializes the GUI elements.
	 */
	private void initUI() {
		// Instance variables
		menubar = new JMenuBar();
		JMenu controlMenu = new JMenu("Control");
		quit = new JMenuItem("Quit");
		JMenu helpMenu = new JMenu("Help");
		JMenuItem instruction = new JMenuItem("Instructions");		
		quit.addActionListener(e -> System.exit(0));
		instruction.addActionListener(e -> JOptionPane.showMessageDialog(null, "Play a game of Tic-Tac-Toe. \n"
				+ "Click on an empty cell on the board to make your move. \n"
				+ "Try to get three of your marks (either X or O) in a row, either horizontally, vertically, or diagonally, to win the game. \n"
				+ "If all cells are filled and no player has won, the game is a draw. \n", "Instructions", JOptionPane.INFORMATION_MESSAGE));
		controlMenu.add(quit);
		helpMenu.add(instruction);
		menubar.add(controlMenu);
		
		helpMenu.add(instruction);
		menubar.add(helpMenu);
		
		frame = new JFrame("Tic Tac Toe");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 500);
		JPanel mainPanel = new JPanel(new BorderLayout());
		JPanel namePanel = new JPanel(new FlowLayout());
		text = new JLabel("Enter your player name...");
		mainPanel.add(text, BorderLayout.NORTH);
		name = new JTextField(28);
		submit = new JButton("Submit");
		namePanel.add(name);
		namePanel.add(submit);
		mainPanel.add(namePanel, BorderLayout.SOUTH);
		
		frame.add(mainPanel);
		frame.setJMenuBar(menubar);
		frame.setVisible(true);
		
		JPanel gridPanel = new JPanel(new GridLayout(3, 3));
		gridPanel.setBackground(Color.BLACK);
		board = new JButton[3][3];
		
		for (int row = 0; row<3; row++) {
			for (int col = 0; col<3; col++) {
				JButton button = new JButton();
				button.setPreferredSize(new Dimension(80,80));
				button.setFont(new Font("Arial", Font.BOLD, 40));
				button.setBackground(Color.WHITE);
				button.setBorder(new LineBorder(Color.BLACK, 1));
				button.setEnabled(false);
				board[row][col] = button;
				gridPanel.add(button);
			}
		}
		mainPanel.add(gridPanel, BorderLayout.CENTER);
		
	}
	/**
	 * Resets the Tic Tac Toe board back to an empty state.
	 */
	public void resetBoard() {
	for (int i = 0; i<3; i++) {
		for (int j = 0; j<3; j++) {
				board[i][j].setText("");
				board[i][j].setEnabled(false);
			}
		}
	}
	/**
	 * Enables or disables the Tic Tac Toe board based on the player's turn.
	 * 
	 * @param on true to enable the board, false to disable it
	 */
	public void setBoard(boolean on) {
		for (int i = 0; i<3; i++) {
			for (int j = 0; j<3; j++) {
				board[i][j].setEnabled(on);
			}
		}
	}
	/**
	 * Updates the board after a move is played.
	 * 
	 * @param i The row index of the move
	 * @param j The column index of the move
	 * @param player The player who made the move
	 */
	public void updateBoard(int i, int j, String player) {
		Color color = null;
		board[i][j].setEnabled(false);
		if (player.equals("X")) {
			color = Color.RED;
		}
		else {
			color = Color.GREEN;
		}
		board[i][j].setText(player);
		board[i][j].setForeground(color);
		setBoard(false);
	}
	/**
	 * Displays a message dialog asking the user to play again.
	 * @param text The message to display
	 * @return true or false based on if the player wants to play again
	 */
	public boolean playAgainMessage(String text) {
		int choice = JOptionPane.showOptionDialog(frame, text, "Game Over", 
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, 
				null, null, null);
		if (choice == JOptionPane.NO_OPTION) {
			return false;
		}
		else {
			return true;
		}
	}
	/**
	 * Shows a message dialog indicating that a player has disconnected.
	 */
	public void disconnectMessage() {
		JOptionPane.showMessageDialog(frame, "Game Ends. One of the players left.");
	}
	/**
	 * Hides or shows the name field.
	 * 
	 * @param val true to show the name field, false to hide it
	 */
	public void setName(boolean val) {
		name.setEnabled(val);
	}
	/**
	 * Hides or shows the submit button.
	 * 
	 * @param val true to show the submit button, false to hide it
	 */
	public void setSubmit(boolean val) {
		submit.setEnabled(val);
	}
	/**
	 * Changes the title of the frame.
	 * 
	 * @param val The new title of the frame
	 */
	public void setFrame(String val) {
		frame.setTitle(String.format("Tic Tac Toe-Player: %s", val));
	}
	/**
	 * Returns the JButton array representing the board.
	 * 
	 * @return The JButton array representing the board
	 */
	public JButton[][] getBoard(){
		return board;
	}
	/**
	 * Returns the submit button.
	 * 
	 * @return The submit button
	 */
	public JButton getSubmit() {
		return submit;
	}
	/**
	 * Returns the player's name.
	 * 
	 * @return The player's name
	 */
	public String getName() {
		return name.getText();
	}
	/**
	 * Returns the JMenuItem used to quit the game.
	 * 
	 * @return The JMenuItem used to quit the game
	 */
	public JMenuItem getExitMenu() {
		return quit;
	}
	/**
	 * Sets the text of the instruction label.
	 * 
	 * @param message The text to set
	 */
	public void setText(String message) {
		text.setText(message);
	}
	
}
