import java.awt.Rectangle;		// Abstract Window Toolkit
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * This class implements SkipList data structure and contains an inner SkipNode
 * class which the SkipList will make an array of to store data.
 * 
 * @author CS Staff
 * 
 * @version 2021-08-23
 * @param <K> Key
 * @param <V> Value
 */
public class SkipList<K extends Comparable<? super K>, V> implements Iterable<KVPair<K, V>> {
	private SkipNode head;	// First element of the top level
	private int size; 		// number of entries in the the Skip List
	private int level;		// number of level in the Skip List
	private FileManager writeToFile;	// This object used for writing data to file.

	/**
	 * Initializes the fields head, size, level, and file object.
	 */
	public SkipList() {
		head = new SkipNode(null, 0);
		level = -1;
		size = 0;
		writeToFile = new FileManager();
	}

	/**
	 * Returns a random level number which is used as the depth of the SkipNode
	 * 
	 * @return a random level number
	 */
	int randomLevel() // flip coin.
	{
		int lev;
		Random value = new Random();
		for (lev = 1; Math.abs(value.nextInt()) % 2 == 0; lev++) {
			// Do nothing
		}
		return lev; // returns a random level
	}

	/**
	 * Searches for the KVPair using the key which is a Comparable object.
	 * 
	 * @param key key to be searched for
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<KVPair<K, V>> search(K key) // Function for searching by rectangle's names.
	{
		/*
		 * This ArrayList is used to store the result of the search.
		 * resultKeySearch object is used for storing the KVPair of the 
		 * finding node, if key is not found this object will be null.
		 */
		ArrayList<KVPair<K, V>> resultKeySearch = new ArrayList<>(level + 1);

		SkipNode current = head; // Dummy header node.
		for (int i = level; i >= 0; i--) // For each level...
		{
			/*
			 * If the forward (next) of the current node is not null,
			 * and the name of the Rectangle of the next node is less
			 * than searching key (Name of Rectangle) then go forward.
			 */
			while ((current.forward[i] != null) && (current.forward[i].getKey().compareTo(key) < 0)) // go forward.
			{
				current = current.forward[i]; // Go one last step.
			}
		}
		current = current.forward[0]; // Move to actual record (element), if it exists.
		/*
		 * If the current node is not null the key (Name of Rectangle)
		 * is the key that we are searching for add it to the ArrayList
		 * object and go forward.
		 */
		while ((current != null) && (current.getKey().compareTo(key) == 0))
		{
			resultKeySearch.add(current.element());
			current = current.forward[0]; // Move to next element.
		}
		return resultKeySearch;
	}

	/**
	 * @return the size of the SkipList
	 */
	public int size() {
		return size;
	}

	/**
	 * Inserts the KVPair in the SkipList at its appropriate spot as designated by
	 * its lexicographical order.
	 * 
	 * @param it the KVPair to be inserted
	 */
	@SuppressWarnings("unchecked")
	public void insert(KVPair<K, V> it) {
		// K: Name Of Rectangle.
		// v: Object Of Rectangle.
		int newLevel = randomLevel();	// New node's level
		if (newLevel > level) { 		// If new node is deeper
			adjustHead(newLevel);		// adjust the header
		}
		// Track end of level
		
		/*
		 * We used this way to make our array because we need it to store 
		 * object so that we take this idea from the forward array inside
		 * the SkipNode class, and we do not use ArrayList because it's 
		 * size only initial size and that makes error in our for loop
		 * with the random levels.
		 */
		SkipNode[] update = (SkipNode[]) Array.newInstance(SkipList.SkipNode.class, level + 1);

		SkipNode current = head; // Start at header node
		for (int i = level; i >= 0; i--) // Find insert position
		{
			/*
			 * If the forward (next) of the current node is not null,
			 * and it's key is less than the key of the pair we need 
			 * to insert go to the next node.
			 */
			while ((current.forward[i] != null) && (current.forward[i].getKey().compareTo(it.getKey()) < 0)) {
				current = current.forward[i];	// Go step forward.
			}
			
			/*
			 * Saving this node to update it's forward
			 * while adding the new node.
			 */
			update[i] = current; // Track end at level i
		}
		
		/*
		 * We exploit the current object and avoid
		 * creating new one for two reasons:
		 * 1) We don't need it again.
		 * 2) We keep our memory space.
		 */
		current = new SkipNode(it, newLevel);
		
		/*
		 * Splice into list by connecting the forward of our
		 * new node with the old forward of the update[i] node,
		 * and the and make the forward of the update[i] node
		 * pointing to the new node 
		 */
		for (int i = 0; i <= newLevel; i++)
		{
			current.forward[i] = update[i].forward[i]; // Who x points to
            update[i].forward[i] = current; // Who points to x
	    }
		size++; // Incrementing our size.
	}

	/**
	 * Increases the number of levels in head so that no element has more indices
	 * than the head.
	 * 
	 * @param newLevel the number of levels to be added to head
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void adjustHead(int newLevel) {
		SkipNode saveHead = head;
		/*
		 * creating a pair object with no name, and passing it 
		 * to the SkipNode constructor with the new level.
		 */
		head = new SkipNode(new KVPair(null, null), newLevel);
		for (int i = 0; i <= level; i++) {
			/*
			 * Copy the old data from the old head to the new one.
			 */
			head.forward[i] = saveHead.forward[i];
		}
		level = newLevel;
	}

	/**
	 * Removes the KVPair that is passed in as a parameter and returns true if the
	 * pair was valid and false if not.
	 * 
	 * @param pair the KVPair to be removed
	 * @return returns the removed pair if the pair was valid and null if not
	 */

	@SuppressWarnings("unchecked")
	public KVPair<K, V> remove(K key)
	{
		SkipNode current = head;
		// create update array.
		SkipNode[] update = (SkipNode[]) Array.newInstance(SkipList.SkipNode.class, level + 1);

		/*
		 * Start from highest level of skip list move the current pointer forward while
		 * key is greater than key of node next to current Otherwise inserted current in
		 * update and move one level down and continue search.
		 */
		for (int i = level; i >= 0; i--)
		{
			// We explained this line before.
			while (current.forward[i] != null && current.forward[i].getKey().compareTo(key) < 0) {
				current = current.forward[i];
			}
			update[i] = current;
		}
		
		/*
		 * reached level 0 and forward pointer to right, which is desired position to
		 * delete the basic node.
		 */
		current = current.forward[0];	// current here should be the element to delete.
		if (current != null && newCompareTo(current.getKey(), key) == 0)
		{
			// delete node by rearranging pointers.
			for (int i = 0; i <= level; i++)
			{
				if (update[i].forward[i] == current)
				{
					// We do not actually remove it but rearranging pointers.
					update[i].forward[i] = current.forward[i];
				}
				else		// we do not want to loop throw the undesired elements.
					break;
			}
			/*
			 * After deletion we may have some levels in the head
			 * that pointing to null remove it for efficiency.
			 */
			while (level > 0 && head.forward[level] == null) {
                level--;
            }
			size--;
			return current.element();
		}
		return null;
	}

	/**
	 * Removes a KVPair with the specified value.
	 * 
	 * @param val the value of the KVPair to be removed
	 * @return returns true if the removal was successful
	 */
	public KVPair<K, V> removeByValue(V val)
	{
		/*
		 * previous:
		 * For connecting it with the node that its forward (we want to delete) pointing to it after deletion.
		 */
		SkipNode previous = head;
		SkipNode current = head.forward[0];
		
		while (current != null)
		{
			/*
			 * Comparing the current rectangle with
			 * the rectangle we want to remove.
			 * If we find it remove and connect.
			 */
			if (comparingTwoRectangles(current.element().getValue(), val))
			{
				previous.forward[0] = current.forward[0];
				size--;
				return current.element();
			}
			previous = current;
			current = current.forward[0];
		}

		return null;
	}
	
	/*
	 * displayList function for displaying liked list elements.
	 * Function for developers for testing skip list operations.
	 * We developed it before developing the dump function.
	 */
	void displayList()
	{
		System.out.println("\n**liked list**");
		SkipNode node = head.forward[0];
		while (node != null) {
			System.out.println(node.element().toString() + " ");
			node = node.forward[0];
		}
	}

	/*
	 * Prints out the SkipList in a human readable
	 * format to the console and file.
	 */
	public void dump()
	{
		SkipNode current = head;
		
		String fileLine = "SkipList dump:";
		printFileConsole(fileLine);
		
		while (current != null)
		{
			if (current == head)		// If we have no nodes and current equals to the head level of the head.
			{
				fileLine = "Node has depth " + current.level + ", Value (null)";
			}
			else
			{
				fileLine = "Node has depth " + current.level + ", Value " + rectangleInfo(current.element());
			}
			printFileConsole(fileLine);
			current = current.forward[0];
		}
		fileLine = "SkipList size is: " + size;
		printFileConsole(fileLine);
	}
	
	/*
	 * We created this method for retrieving the
	 * rectangle information in a readable manner.
	 */
	public String rectangleInfo(KVPair<K, V> data)
	{
		Rectangle recValues = (Rectangle)data.getValue();
		
		int x = (int)recValues.getX();
		int y = (int)recValues.getY();
		
		int w = (int)recValues.getWidth();
		int h = (int)recValues.getHeight();
		
		return "(" + (String) data.getKey() + ", " + x + ", " + y + ", " + w + ", " + h + ")";
	}

	/*
	 * comparingTwoRectangles function for comparing two values of the KVPair class.
	 * This function takes two values casting them to Rectangle object and comparing
	 * the dimensions of this two Rectangles, and finally returns Boolean according
	 * to the result of the comparison.
	 */
	private Boolean comparingTwoRectangles(V firstRectangle, V secondRectangle)
	{
		// First Rectangle.
		Rectangle rec1 = (Rectangle)firstRectangle;
		Rectangle rec2 = (Rectangle)secondRectangle;

		// Data for the first rectangle.
		int rec1X = (int)rec1.getX();
		int rec1Y = (int)rec1.getY();
		int rec1W = (int)rec1.getWidth();
		int rec1H = (int)rec1.getHeight();

		
		// Data for the second rectangle.
		int rec2X = (int)rec2.getX();
		int rec2Y = (int)rec2.getY();
		int rec2W = (int)rec2.getWidth();
		int rec2H = (int)rec2.getHeight();

		if (rec1X == rec2X && rec1Y == rec2Y && rec1W == rec2W && rec1H == rec2H)
			return true;
		return false;
	}

	/*
	 * CompareTo Function we used it to make a compare
	 * for two strings if they contains numbers.
	 * This function takes a Comparable object and another generic 
	 * one and then casting them to strings.
	 */
	@SuppressWarnings("rawtypes")
	private int newCompareTo(Comparable firstString, K secondString)	// r4, r14
	{
		String comp1 = (String)firstString;
		String comp2 = (String)secondString;
		
		int countSameChars = 0;
		int minVal = Math.min(comp1.length(), comp2.length());

		// Loop through two strings and check how many strings are same
		for (int i = 0; i < minVal; i++)
		{
			if (comp1.charAt(i) == comp2.charAt(i))
				countSameChars++;
			else
				break;
		}
		if (countSameChars == 0)
		{
			// If there's no same letter, then use String compareTo method.
			return comp1.compareTo(comp2);
		}
		else
		{
			// slice same string from both strings, and take only the different substrings.
			String newStr1 = comp1.substring(countSameChars);	// From the beginning index to the end.
			String newStr2 = comp2.substring(countSameChars);

			// If both sliced strings are digits then use Integer compare method.
			if (newStr1.matches("\\d+") && newStr2.matches("\\d+"))
			{
				return Integer.compare(Integer.parseInt(newStr1), Integer.parseInt(newStr2));
			}
			else	// If not, use String compareTo method.
			{
				return comp1.compareTo(comp2);
			}
		}
	}
	
	/*
	 * We created this method to avoid using the same
	 * two lines when we need them, because we need to
	 * write to the console and the file.
	 */
	public void printFileConsole(String fileLine)
    {
    	System.out.println(fileLine);
		writeToFile.WriteInFile(fileLine);
    }

	/**
	 * This class implements a SkipNode for the SkipList data structure.
	 * 
	 * @author CS Staff
	 * 
	 * @version 2016-01-30
	 */
	private class SkipNode {

		// The KVPair to hold, the name of the rectangle and the rectangle itself.
		private KVPair<K, V> pair;
		// what is this
		/*
		 * This array is used for storing objects of the same value.
		 */
		private SkipNode[] forward;
		// the number of levels of a specific item or depth.
		private int level;

		/**
		 * Initializes the fields with the required KVPair and the number of levels from
		 * the random level method in the SkipList.
		 * 
		 * @param tempPair the KVPair to be inserted
		 * @param level    the number of levels that the SkipNode should have
		 */
		@SuppressWarnings("unchecked")
		public SkipNode(KVPair<K, V> tempPair, int level) {
			pair = tempPair;
			forward = (SkipNode[]) Array.newInstance(SkipList.SkipNode.class, level + 1);
			this.level = level;
		}

		@SuppressWarnings("rawtypes")
		public Comparable getKey() {
			return pair.getKey();
		}

		/**
		 * Returns the KVPair stored in the SkipList.
		 * 
		 * @return the KVPair
		 */
		public KVPair<K, V> element() {
			return pair;
		}

	}

	private class SkipListIterator implements Iterator<KVPair<K, V>> {
		private SkipNode current;

		public SkipListIterator() {
			current = head;
		}

		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			// If our forward (next) is not null return true.
			if (current.forward[0] != null)
			{
				return true;
			}
			return false;
		}

		@Override
		public KVPair<K, V> next() {
			// TODO Auto-generated method stub
			/*
			 * If we have next go to it and return the KVPair.
			 */
			if (hasNext())
			{
				current = current.forward[0];
				return current.element();
			}
			return null;
		}
	}

	@Override
	/*
	 * This method is used for creating out iterator
	 * that we will use in the Database class 
	 * (Instantiate a new object from the SkipListIterator class).
	 */
	public Iterator<KVPair<K, V>> iterator() {
		// TODO Auto-generated method stub
		return new SkipListIterator();
	}
}
