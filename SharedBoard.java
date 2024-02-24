
/**
 * The SharedBoard class represents the game board shared by the clients, and implements the logic behind the game.
 * It keeps track of the current player's turn, updates the board with player moves,
 * and checks for a win or draw.
 * 
 * @author Farhan Yusuf Khan
 * @version 1.0
 * @since 2023-12-07
 */
public class SharedBoard {
	//instance variables
	private char turn;
	private char[][] board;
    /**
     * Constructs a SharedBoard object with the initial state of the game board.
     * The turn is set to 'X', and the board is initialized with empty spaces.
     */
	public SharedBoard() {
		this.turn = 'X';
		this.board = new char[3][3];
		for (int i = 0; i<3; i++) {
			for (int j = 0; j<3; j++) {
				board[i][j] = ' ';
			}
		}
	}
    /**
     * Changes the current player's turn.
     * If the turn is 'X', it changes it to 'O', and vice versa.
     */
	public void changeTurn() {
		if (turn == 'X') {
			turn = 'O';
		}
		else {
			turn = 'X';
		}
	}
    /**
     * Sets the specified position on the game board with the current player's symbol.
     * 
     * @param x the row index of the position on the board
     * @param y the column index of the position on the board
     */
	public synchronized void setBoard(int x, int y) {
		board[x][y] = turn;
	}
    /**
     * Gets the current player's turn.
     * 
     * @return the current player's turn ('X' or 'O')
     */
	public String getTurn() {
		return Character.toString(turn);
	}
	
    /**
     * Checks if the current player has won the game or if it's a draw.
     * 
     * @param Player the player to check for a win ('X' or 'O')
     * @return 'X' if player X wins, 'O' if player O wins, 'D' if it's a draw, or 'N' if the game is not over
     */
	public synchronized char checkWin(String Player) {
		char player;
		if (Player.equals("X")) {player = 'X';}
		else {player = 'O';}
		
		for (int i = 0; i < 3; i++) {
			if (board[i][0] == board[i][1] && board[i][0] == board[i][2] && board[i][2] == player)
                return player;
            if (board[0][i] == board[1][i] && board[1][i] == board[2][i] && board[2][i] == player)
            	return player;
		}
		if (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[2][2] == player)
			return player;
		if (board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[2][0] == player)
			return player;
		
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (board[i][j] == ' ')
					return 'N';
			}
		}
		return 'D';
	}
}
