/**
 * nk_TicTacToe is a class which supports all the back end operations of
 * computerPlay, by storing a dictionary of game boards, and evaluating the
 * scores per move, move made, and more.
 * 
 * @author Will Malisch, 250846447
 *
 */
public class nk_TicTacToe {

	/* Atribute Declarations */
	private int board_size;
	private int inline;
	private int max_levels;
	private char[][] gameBoard;
	private int entries;
	private final int TABLE_SIZE = 7001;
	private Dictionary dict;
	private int pointsO;
	private int pointsX;
	private int N;

	/**
	 * Constructor creates a operations and variable for tic tac toe game.
	 * 
	 * @param board_size
	 *            is the dimensions of the board, also known as n in the nk
	 * @param inline
	 *            is the number of symbols (X's or O's) needed in a line to win
	 * @param max_levels
	 *            is the number of maximum levels of scenarios
	 */
	public nk_TicTacToe(int board_size, int inline, int max_levels) {
		this.board_size = board_size;
		gameBoard = new char[board_size][board_size];
		this.inline = inline;
		this.max_levels = max_levels;
		N = board_size * board_size;
	}

	/**
	 * createDictionary method creates a dictionary of default recommended size
	 * 
	 * @return the dictionary used to store all the game configurations
	 */
	public Dictionary createDictionary() {
		dict = new Dictionary(TABLE_SIZE);
		return dict;
	}

	/**
	 * repeatedConfig method is checks if a game board configuration is in the
	 * dictionary or not
	 * 
	 * @param configurations
	 *            is a dictionary of all the past board configurations
	 * @return is the score of the configuration if it is in the dictionary, and -1
	 *         if it is not
	 */
	public int repeatedConfig(Dictionary configurations) {
		String config = this.boardToString();
		return configurations.get(config);
	}

	/**
	 * insertConfig method creates a record object of the game board configuration
	 * and inserts it into the dictionary
	 * 
	 * @param configurations
	 *            is a dictionary of all the past board configurations
	 * @param score
	 *            is the score for the current game board configuration
	 */
	public void insertConfig(Dictionary configurations, int score) {
		String config = this.boardToString();
		Record pair = new Record(config, score);
		configurations.insert(pair);
	}

	/**
	 * storePlay method stores the inputed play on the gameBoard
	 * 
	 * @param row
	 *            is the row user wants to place their move
	 * @param col
	 *            is the column user wants to place their move
	 * @param symbol
	 *            is what determines if they are computer or user
	 */
	public void storePlay(int row, int col, char symbol) {
		gameBoard[row][col] = symbol;
	}

	/**
	 * squareIsEmpty method tests if the location on gameBoard is blank or not
	 * 
	 * @param row
	 *            is the row user wants to check
	 * @param col
	 *            is the column user wants to check
	 * @return true if the space holds a space
	 */
	public boolean squareIsEmpty(int row, int col) {
		if (gameBoard[row][col] == 'X' || gameBoard[row][col] == 'O') {
			return false;
		}
		return true;
	}

	/**
	 * wins method tests whether the inputed symbol wins the game
	 * 
	 * @param symbol
	 *            is what determines whether we choose humans or computer
	 * @return false if neither O or X is a win
	 */
	public boolean wins(char symbol) {
		if (symbol == 'O')
			return this.evalBoard() == 3;
		if (symbol == 'X')
			return this.evalBoard() == 0;
		return false;
	}

	/**
	 * isDraw method tests if the game was a draw
	 * 
	 * @return true if the board is full and no win has been found
	 */
	public boolean isDraw() {

		int draw = this.evalBoard();
		return (draw == 2);
	}

	/**
	 * evalBoard method determines whether the board has a winner, is a draw, or is
	 * undecided
	 * 
	 * @return a 3 is computer wins, 0 if human wins, a 2 if there's a draw, and a 1
	 *         if it is undecided
	 */
	public int evalBoard() {
		for (int j = 0; j < board_size; j++) {
			for (int i = 0; i < board_size; i++) {
				char eval = gameBoard[j][i];
				if (eval == 'O') {
					pointsO = this.evalCol(j, i, 'O');
					if (pointsO == 3)
						return pointsO;
				} else if (eval == 'X') {
					pointsX = this.evalCol(j, i, 'X');
					if (pointsX == 0)
						return pointsX;
				}
			}
		}

		if (pointsO == 2)
			return 2;
		return 1;
	}

	/**
	 * evalCol method is a private helper method for evalBoard. It evaluates if
	 * there is a column win
	 * 
	 * @param currRow
	 *            is the current row index
	 * @param currCol
	 *            is the current column index
	 * @param symbol
	 *            is the symbol your evaluating the win for
	 * @return 3 if computer wins, 0 if human wins, and return the next evaluation
	 *         function otherwise
	 */
	private int evalCol(int currRow, int currCol, char symbol) {
		int rowStore = currRow;
		int colStore = currCol;

		int count_inline = 1;
		currRow++;
		while (currRow < board_size && gameBoard[currRow][currCol] == symbol) {
			currRow++;
			count_inline++;
		}
		if (count_inline == inline) {
			if (symbol == 'O') {
				return 3;
			} else if (symbol == 'X') {
				return 0;
			}
		}

		return evalRow(rowStore, colStore, symbol);
	}

	/**
	 * evalRow method is a private helper method for evalBoard. It evaluates if
	 * there is a row win
	 * 
	 * @param currRow
	 *            is the current row index
	 * @param currCol
	 *            is the current column index
	 * @param symbol
	 *            is the symbol your evaluating the win for
	 * @return 3 if computer wins, 0 if human wins, and return the next evaluation
	 *         function otherwise
	 */
	private int evalRow(int currRow, int currCol, char symbol) {
		int rowStore = currRow;
		int colStore = currCol;

		int count_inline = 1;
		currCol++;
		while (currCol < board_size && gameBoard[currRow][currCol] == symbol) {
			currCol++;
			count_inline++;
		}
		if (count_inline == inline) {
			if (symbol == 'O') {
				return 3;
			} else if (symbol == 'X') {
				return 0;
			}
		}
		return evalBackCross(rowStore, colStore, symbol);
	}

	/**
	 * evalBackCross method is a private helper method for evalBoard. It evaluates
	 * if there is a \ diagonal win
	 * 
	 * @param currRow
	 *            is the current row index
	 * @param currCol
	 *            is the current column index
	 * @param symbol
	 *            is the symbol your evaluating the win for
	 * @return 3 if computer wins, 0 if human wins, and return the next evaluation
	 *         function otherwise
	 */
	private int evalBackCross(int currRow, int currCol, char symbol) {
		int rowStore = currRow;
		int colStore = currCol;

		int count_inline = 1;
		currCol++;
		currRow++;
		while (currRow < board_size && currCol < board_size && gameBoard[currRow][currCol] == symbol) {
			currCol++;
			currRow++;
			count_inline++;
		}
		// System.out.println(count_inline);
		if (count_inline == inline) {
			if (symbol == 'O') {
				return 3;
			} else if (symbol == 'X') {
				return 0;
			}
		}
		return evalFrontCross(rowStore, colStore, symbol);
	}

	/**
	 * evalFrontCross method is a private helper method for evalBoard. It evaluates
	 * if there is a / diagonal win
	 * 
	 * @param currRow
	 *            is the current row index
	 * @param currCol
	 *            is the current column index
	 * @param symbol
	 *            is the symbol your evaluating the win for
	 * @return 3 if computer wins, 0 if human wins, and return the next evaluation
	 *         function otherwise
	 */
	private int evalFrontCross(int currRow, int currCol, char symbol) {
		int count_inline = 1;
		currCol--;
		currRow++;
		while (currCol >= 0 && currRow < board_size && gameBoard[currRow][currCol] == symbol) {
			currCol--;
			currRow++;
			count_inline++;
		}
		System.out.println(count_inline);
		if (count_inline == inline) {
			if (symbol == 'O') {
				return 3;
			} else if (symbol == 'X') {
				return 0;
			}
		}

		if (this.fullBoard() == N) {
			return 2;
		}
		return 1;
	}

	/**
	 * fullBoard is a private helper method for determining a draw. It counts how
	 * many characters on the board
	 * 
	 * @return an integer value for the number of spaces filled by characters
	 */
	private int fullBoard() {
		int charCount = 0;
		for (int j = 0; j < board_size; j++) {
			for (int i = 0; i < board_size; i++) {
				if (gameBoard[j][i] == 'X' || gameBoard[j][i] == 'O')
					charCount++;
			}
		}
		return charCount;
	}

	/**
	 * boardToString is a private helper method that converts the board contents to
	 * a string configuration
	 * 
	 * @return the string configuration of a board
	 */
	private String boardToString() {
		String result = "";
		for (int j = 0; j < board_size; j++) {
			for (int i = 0; i < board_size; i++) {
				result = result + gameBoard[j][i];
			}
		}
		return result;
	}
}