import java.util.LinkedList;
import java.lang.Math;

/**
 * Dictionary is a dictionary class. It stores items of type Record for a
 * tictactoe game. To deal with collisions from the hash function, it implements
 * LinkedLists for separate chaining.
 * 
 * @author Will Malisch, 250846447
 *
 */

public class Dictionary implements DictionaryADT {

	/* Attribute declarations */
	private int tableSize;
	private LinkedList<Record>[] table;
	private int entries;

	/**
	 * Constructor creates a dictionary with a inputed table size, and fills all
	 * table locations with an empty list
	 * 
	 * @param size
	 *            is the size of the hash table
	 */
	public Dictionary(int size) {
		tableSize = size;
		table = new LinkedList[tableSize];
		for (int i = 0; i < tableSize; i++) {
			table[i] = new LinkedList<Record>();
		}
	}

	/**
	 * insert method places a Record object in the dictionary. It uses a hash
	 * function to optimize locations
	 * 
	 * @throws DictionaryException
	 *             if the exact board configuration has been added before
	 * @param pair
	 *            is a board configuration and respective score
	 * @return 0 if pair is first item in table location, 1 if it is anything but
	 *         the first item in table location
	 */
	public int insert(Record pair) throws DictionaryException {
		int key = this.h(pair.getConfig());
		if (table[key].isEmpty()) {
			table[key].add(pair);
			entries++;
			return 0;
		} else { // curr is not empty
			if (this.get(pair.getConfig()) != -1) {
				throw new DictionaryException();
			} else {
				table[key].add(pair);
				entries++;
				return 1;
			}
		}
	}

	/**
	 * remove method removes a Record object from the dictionary. It uses a hash
	 * function to find the location of this object.
	 * 
	 * @throws DictionaryException
	 *             if the inputed configuration is not in the dictionary
	 * @param config
	 *            is the string configuration of the board to be removed
	 */
	public void remove(String config) throws DictionaryException {
		LinkedList<Record> list = table[this.h(config)];
		if (list == null)
			throw new DictionaryException();
		if (!list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				Record curr = list.get(i);
				if (curr.getConfig().equals(config)) {
					list.remove(i);
					entries--;
				}
			}
		} else {
			throw new DictionaryException();
		}
	}

	/**
	 * get method returns the respective score of the inputed configuration
	 * 
	 * @param config
	 *            is the board configuration you want the score for
	 * @return -1 if the configuration is not in the dictionary, otherwise return
	 *         the score
	 */
	public int get(String config) {
		int key = this.h(config);
		if (table[key].isEmpty())
			return -1;
		if (!table[key].isEmpty()) {
			for (int i = 0; i < table[key].size(); i++) {
				Record curr = table[key].get(i);
				if (curr.getConfig().equals(config)) {
					return curr.getScore();
				}
			}
		}
		return -1;
	}

	/**
	 * numElements method gets the number of elements in the dictionary
	 * 
	 * @return the number of elements
	 */
	public int numElements() {
		return entries;
	}

	/**
	 * h is a private helper method. This is the has function
	 * 
	 * @param config
	 *            is a string representation of the board configuration
	 * @return an integer value known as a key, which corresponds to this
	 *         configurations location in the dictionary table
	 */
	private int h(String config) {
		int val = (int) config.charAt(config.length() - 1);
		for (int i = config.length() - 1; i > 0; i--) {
			val = val * 7 + (int) config.charAt(i);
		}
		return Math.abs(val % tableSize);
	}

}
