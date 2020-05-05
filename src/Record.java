/**
 * Record class is for an object that stores a tictactoe game boards
 * configuration and respective score
 * 
 * @author Will Malisch, 250846447
 *
 */
public class Record {

	/* Attribute declarations */
	private String con;
	private int scr;
	private Record next = null;

	/**
	 * Constructor creates a Record object with a score and configuration
	 * 
	 * @param config
	 *            is a string representation of the gameboard
	 * @param score
	 *            is a score that the computer can evaluate
	 */
	public Record(String config, int score) {
		con = config;
		scr = score;
	}

	/**
	 * getConfig method is a method to get the configuration
	 * 
	 * @return configuration
	 */
	public String getConfig() {
		return con;
	}

	/**
	 * getScore is a method to get the score
	 * 
	 * @return score
	 */
	public int getScore() {
		return scr;
	}

}
