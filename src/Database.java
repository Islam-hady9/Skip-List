import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.sql.SQLException;
/**
 * This class is responsible for interfacing between the command processor and
 * the SkipList. The responsibility of this class is to further interpret
 * variations of commands and do some error checking of those commands. This
 * class further interpreting the command means that the two types of remove
 * will be overloaded methods for if we are removing by name or by coordinates.
 * Many of these methods will simply call the appropriate version of the
 * SkipList method after some preparation.
 * 
 * @author CS Staff
 * 
 * @version 2021-08-23
 * @param <V>
 * @param <K>
 */
public class Database<V, K> {

    // this is the SkipList object that we are using
    // a string for the name of the rectangle and then
    // a rectangle object, these are stored in a KVPair,
    // see the KVPair class for more information
    private SkipList<String, Rectangle> list;
    private DatabaseJDBC JDBC;

    /**
     * The constructor for this class initializes a SkipList object with String
     * and Rectangle a its parameters.
     * @throws SQLException 
     * @throws ClassNotFoundException 
     */
    public Database() throws ClassNotFoundException, SQLException {
        list = new SkipList<String, Rectangle>();
        JDBC = new DatabaseJDBC();
    }


    /**
     * Inserts the KVPair in the SkipList if the rectangle has valid coordinates
     * and dimensions, that is that the coordinates are non-negative and that
     * the rectangle object has some area (not 0, 0, 0, 0). This insert will
     * insert the KVPair specified into the sorted SkipList appropriately
     * 
     * @param pair
     *            the KVPair to be inserted
     * @throws SQLException 
     */
    public void insert(KVPair<String, Rectangle> pair) throws SQLException {
    	list.insert(pair);
    	JDBC.insertRectangleData(pair);
    }


    /**
     * Removes a rectangle with the name "name" if available. If not an error
     * message is printed to the console.
     * 
     * @param name
     *            the name of the rectangle to be removed
     */
    public Boolean remove(String name) {
    	KVPair<String, Rectangle> obj = list.remove(name);
    	
    	String fileLine;
    	if (obj == null)
    	{
    		fileLine = "Rectangle not removed: (" + name + ")";
    		printFileConsole(fileLine);
    		return false;
    	}
    	else
    	{
    		fileLine = "Rectangle removed: " + rectangleInfo(obj);
    		printFileConsole(fileLine);
    		return true;
    	}
    }

    /**
     * Removes a rectangle with the specified coordinates if available. If not
     * an error message is printed to the console.
     * 
     * @param x
     *            x-coordinate of the rectangle to be removed
     * @param y
     *            x-coordinate of the rectangle to be removed
     * @param w
     *            width of the rectangle to be removed
     * @param h
     *            height of the rectangle to be removed
     */
    public Boolean remove(int x, int y, int w, int h) {
    	KVPair<String, Rectangle> obj = list.removeByValue(new Rectangle(x, y, w, h));
    	
    	String fileLine;
    	if(obj != null)
    	{
    		fileLine = "Rectangle removed: " + rectangleInfo(obj);
    		printFileConsole(fileLine);
    		return true;
    	}
    	else
    	{
    		fileLine = "Rectangle rejected: (" + x + ", " + y + ", " + w + ", " + h +")";
    		printFileConsole(fileLine);
    		return false;
    	}
    }


    /**
     * Displays all the rectangles inside the specified region. The rectangle
     * must have some area inside the area that is created by the region,
     * meaning, Rectangles that only touch a side or corner of the region
     * specified will not be said to be in the region. You will need a SkipList Iterator for this 
     * 
     * @param x
     *            x-Coordinate of the region
     * @param y
     *            y-Coordinate of the region
     * @param w
     *            width of the region
     * @param h
     *            height of the region
     */
    public void regionsearch(int x, int y, int w, int h)
    {
		/*
		 * regionsearchRectangle is an ArrayList use to store the rectangles
		 * that exist in (x, y, w, h) region .
		 */
    	ArrayList<KVPair<String, Rectangle>> regionsearchedRectangle = new ArrayList<>();
    	
		/*
		 * current iterator use to iterate on rectangles . 
		 */
    	Iterator<KVPair<String, Rectangle>> current = list.iterator();
    	
    	// CurrentSaver, because we need to save the current before moving to forward.
    	KVPair<String, Rectangle> currentElement;
    	
    	while (current.hasNext())
    	{
			currentElement = current.next();

			/*
			 * Comparing the rectangle of the current KVPair with other rectangle
			 * we take its dimension from the function.
			 */
			if (currentElement.getValue().intersects(new Rectangle(x, y, w, h)))
			{
				regionsearchedRectangle.add(currentElement);
			}
    	}
		/*
		 * if the size of regionsearchRectangle is zero then no
		 * regionRectangle else print the rectangle intersecting 
		 * with region (x, y, w, h)
		 */
    	String fileLine;
    	if (regionsearchedRectangle.size() != 0)
    	{
    		fileLine = "Rectangles intersecting region (" + x + ", " + y + ", " + w + ", " + h + "):";
    		printFileConsole(fileLine);
    		for (int i = 0; i < regionsearchedRectangle.size(); i++)
        	{
    			fileLine = rectangleInfo(regionsearchedRectangle.get(i));
        		printFileConsole(fileLine);
        	}
    	}
    	else
    	{
    		fileLine = "Rectangles intersecting region (" + x + ", " + y + ", " + w + ", " + h + "):\n";
    		printFileConsole(fileLine);
    	}
    }

    /**
     * Prints out all the rectangles that Intersect each other by calling the
     * SkipList method for intersections. You will need to use two SkipList Iterators for this
     */
	public void intersections()
	{
		/*
		 * ArrayList to store the intersected Rectangles.
		 */
		ArrayList<KVPair<String, Rectangle>> intersectedRectangle = new ArrayList<>();

		/*
		 * We have two iterates here, because we have two nested loops,
		 * the current iterator use for outer loop.
		 */
		Iterator<KVPair<String, Rectangle>> current = list.iterator();

		while (current.hasNext())
		{
			/*
			 * goes from head to the next in the first iteration.
			 * goes one step next in the other iterations.
			 * and but currentElement inside the loop, because if the current
			 * has no next we can not go next.
			 */
			KVPair<String, Rectangle> currentElement = current.next();
			
			/*
			* We have two iterates here, because we have two nested loops,
			* the iterate iterator use for inner loop.
			*/
			Iterator<KVPair<String, Rectangle>> iterate = list.iterator();

			while (iterate.hasNext())
			{
				KVPair<String, Rectangle> iterateElement = iterate.next();	// The same as currentElement.

				/*
				 * We do not need the Rectangle object to intersects with itself.
				 */
				if (currentElement != iterateElement)
				{
					/*
					 * Using the built in intersects method to check if two Rectangles are intersects or not.
					 * it returns true or false.
					 * If we need to check weather the first contains the second we would use contains method
					 * and it works the same as intersects method.
					 */
					if (currentElement.getValue().intersects(iterateElement.getValue()))
					{
						intersectedRectangle.add(currentElement);
						intersectedRectangle.add(iterateElement);
					}
				}
			}
		}
		/*
		 * fileLine is a string use to show that it's intersections pairs,
		 * loop in intersectedRectangle ArrayList to get sirst and second 
		 * intersected rectangle, and print them in file and console by
		 * printFileConsole function .
		 */
		String fileLine = "Intersections pairs:";
		printFileConsole(fileLine);
		for (int i = 0; i < intersectedRectangle.size(); i += 2)
		{
			String firstIntersected = rectangleInfo(intersectedRectangle.get(i));
			firstIntersected = firstIntersected.substring(0, firstIntersected.length()-1);
			
			String secondIntersected = rectangleInfo(intersectedRectangle.get(i + 1));
			secondIntersected = secondIntersected.substring(1);
			
			fileLine = firstIntersected + " | " + secondIntersected;
			printFileConsole(fileLine);
		}
	}

    /**
     * Prints out all the rectangles with the specified name in the SkipList.
     * This method will delegate the searching to the SkipList class completely.
     * 
     * @param name
     *            name of the Rectangle to be searched for
     */
    public void search(String name) {
		/*
		* This ArrayList is used to store the result of the search.
		* resultKeySearch object is used for storing the KVPair of 
		* the finding node, if the size of resultKeySearch is zero
		* the rectangle not found, and else that will show the information 
		* of the rectangle found.
		*/
    	ArrayList<KVPair<String, Rectangle>> resultKeySearch = list.search(name);
    	
    	String fileLine;
    	if (resultKeySearch.size() != 0)
    	{
    		fileLine = "Rectangles found:";
    		printFileConsole(fileLine);
    		for (int i = 0; i < resultKeySearch.size(); i++)
    		{
    			fileLine = list.rectangleInfo(resultKeySearch.get(i));
    			printFileConsole(fileLine);
    		}
    	}
    	else
    	{
    		fileLine = "Rectangle not found: " + name;
    		printFileConsole(fileLine);
    	}
    }

    /**
     * Prints out a dump of the SkipList which includes information about the
     * size of the SkipList and shows all of the contents of the SkipList. This
     * will all be delegated to the SkipList.
     * @throws SQLException 
     */
    public void dump() {
        list.dump();
    }
    
    /*
     * We created this method and used it for 
     * printing the dump from our database.
     * 
     * I think if we make this function one from 
     * our commands that we uses in the docktor's 
     * files it will be more Logical.
     */
    public void dumpDatabase() throws SQLException {
    	System.out.println("---------------------------------------------");
        List<KVPair<String, Rectangle>> returnFromDatabase = JDBC.getRectangleData();
        for (int i = 0; i < returnFromDatabase.size(); i++)
        {
        	System.out.println(rectangleInfo(returnFromDatabase.get(i)));
        }
        System.out.println("---------------------------------------------");
    }

    /*
	 * rectangleInfo function use to call rectangleInfo method 
	 * from list object, rectangleInfo method use to show the 
	 * key (the name of rectangle) and value (x, y, w, h) 
	 * of rectangle .
	 */
    public String rectangleInfo(KVPair<String, Rectangle> data) {
    	return list.rectangleInfo(data);
    }

	/*
	* printFileConsole function has a string fileLine and path it
	* to printFileConsole method from list opject, printFileConsole 
	* has a fileLine argument use to show it in the console and 
	* write it in file .
	*/
    public void printFileConsole(String fileLine) {
    	list.printFileConsole(fileLine);
    }
}
