import java.awt.Rectangle;
import java.sql.SQLException;

/**
 * The purpose of this class is to parse a text file into its appropriate, line
 * by line commands for the format specified in the project spec.
 * 
 * @author CS Staff
 * 
 * @version 2021-08-23
 */
@SuppressWarnings("rawtypes")
public class CommandProcessor {

	// the database object to manipulate the
	// commands that the command processor
	// feeds to it
	private Database data;

	/**
	 * The constructor for the command processor requires a database instance to
	 * exist, so the only constructor takes a database class object to feed
	 * commands to.
	 * 
	 * @param dataIn
	 *               the database object to manipulate
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public CommandProcessor() throws ClassNotFoundException, SQLException {
		data = new Database();
	}

	/**
	 * This method identifies keywords in the line and calls methods in the
	 * database as required. Each line command will be specified by one of the
	 * keywords to perform the actions within the database required. These
	 * actions are performed on specified objects and include insert, remove,
	 * regionsearch, search, intersections, and dump. If the command in the file
	 * line is not
	 * one of these, an appropriate message will be written in the console. This
	 * processor method is called for each line in the file. Note that the
	 * methods called will themselves write to the console, this method does
	 * not, only calling methods that do.
	 * 
	 * @param line
	 *             a single line from the text file
	 * @throws SQLException 
	 */
	@SuppressWarnings("unchecked")
	public int processor(String line) throws SQLException	// This function receives command line from Rectangle1.
	{
		/*
		 * The command line is split and stored in an array.
		 */
		String[] query = line.split("\\s+"); // spaces, tabs
		String queryName = query[0]; // The function name.
		
		String fileLine; // To store the line to print in the File and Console.
		
		/*
		 * switch case to specify what the intended function is?
		 * The switch returns a value when a function is read from a command line and 
		 * if the operation is successful to use these values in the testing.
		 * 
		 * If the function is "insert" the switch will return (1).
		 * If the function is "remove" (remove by name) the switch will return (2).
		 * If the function is "remove" (remove by values) the switch will return (3).
		 * If the function is "regionsearch" the switch will return (4).
		 * If the function is "search" the switch will return (5).
		 * If the function is "intersections" the switch will return (6).
		 * If the function is "dump" the switch will return (7).
		 * 
		 * If the process is not successful, the switch will return (0).
		 * 
		 * If the function name is incorrect, the switch will return (-1).
		 */
		switch (queryName) // The function name.
		{
			case "insert": // If the function read from the line is (insert).
			{
				/*
				 * We convert dimensions values from string to integer and pass it to rectangle object.
				 * 
				 * query[1] is the name of the rectangle.
				 * query[2] is Dimension X
				 * query[3] is Dimension Y
				 * query[4] is Dimension W
				 * query[5] is Dimension H
				 */
				Rectangle rectangleDimensions = new Rectangle(Integer.parseInt(query[2]), Integer.parseInt(query[3]),
						Integer.parseInt(query[4]), Integer.parseInt(query[5]));
				KVPair firstPair = new KVPair(query[1], rectangleDimensions);
				/*
				 * Pass the Name of the rectangle and Dimensions of the rectangle to the isInserted function to 
				 * check the name and dimensions of the rectangle and return true if they meet the conditions and false otherwise.
				 * 
				 * If isInserted return true we will insert the rectangle.
				 * Is isInserted return false we will reject the rectangle.
				 */
				if (isInserted(query[1], rectangleDimensions)) 
				{
					data.insert(firstPair);
					fileLine = "Rectangle inserted: " + data.rectangleInfo(firstPair);
					data.printFileConsole(fileLine);
					return 1;
				}
				else
				{
					fileLine = "Rectangle rejected: " + data.rectangleInfo(firstPair);
					data.printFileConsole(fileLine);
					return 0;
				}
			}
			case "remove": // If the function read from the line is (remove).
			{
				if (query.length == 2)	// If the length of the array (query) is equal 2, then the function is (remove by name).
				{
					if (data.remove(query[1]))
						return 2;
					else
						return 0;
				}
				else	// If the length of the array (query) greater than 2, then the function is (remove by values).
				{
					if (data.remove(Integer.parseInt(query[1]), Integer.parseInt(query[2]), Integer.parseInt(query[3]), Integer.parseInt(query[4])))
						return 3;
					else
						return 0;
				}
			}
			case "regionsearch": // If the function read from the line is (regionsearch).
			{
				/*
				 * Pass the Width and the Height of the rectangle to make sure they are greater than 0.
				 */
				if (isRegionsearch(Integer.parseInt(query[3]), Integer.parseInt(query[4])))
				{
					data.regionsearch(Integer.parseInt(query[1]), Integer.parseInt(query[2]),
							Integer.parseInt(query[3]), Integer.parseInt(query[4]));
					return 4;
				}
				else
				{
					fileLine = "Rectangle rejected: (" + Integer.parseInt(query[1]) + ", " +
							Integer.parseInt(query[2]) + ", " + Integer.parseInt(query[3]) + ", "
							+ Integer.parseInt(query[4]) + ")";
					data.printFileConsole(fileLine);
					return 0;
				}
			}
			case "search": // If the function read from the line is (search).
			{
				data.search(query[1]);
				return 5;
			}
			case "intersections": // If the function read from the line is (intersections).
			{
				data.intersections();
				return 6;
			}
			case "dump": // If the function read from the line is (dump).
			{
				data.dump();
				return 7;
			}
			// We hope that it will be one of the docktor's command that in the file.
			// case "dumpDatabase";
			// {
			// 	data.dumpDatabase();
			// 	return 8;
			// }
			default: // The function name is incorrect.
			{
				fileLine = queryName + " Is not including in the database.";
				data.printFileConsole(fileLine);
				return -1;
			}
		}
	}

	/*
	 * Function that takes the values of two functions(rectangleName and rectangleValues) and returns true or false.
	 */
	public Boolean isInserted(String rectangleName, Rectangle rectangleValues) 
	{
		return checkInsertedName(rectangleName) && checkInsertedDimensions(rectangleValues);
	}

	/*
	 * Function that checks the name of the rectangle and makes sure that it matches the rules.
	 */
	private Boolean checkInsertedName(String rectangleName)
	{
		if (Character.isAlphabetic(rectangleName.charAt(0))) // The first letter of the name is alphabetic.
		{
			for (int i = 1; i < rectangleName.length(); i++) // The rest of the letters of the name are alphabetic, Digit, or '_'.
			{
				if (Character.isAlphabetic(rectangleName.charAt(i)) ||
						Character.isDigit(rectangleName.charAt(i)) ||
						rectangleName.charAt(i) == '_') {
					continue;
				} 
				else
				{
					return false;
				}
			}
			return true;
		} 
		else
		{
			return false;
		}
	}

	/*
	 * Function to check the dimensions of the rectangle and make sure that it is inside the "world box".
	 */
	private Boolean checkInsertedDimensions(Rectangle rectangleValues)
	{
		int x = (int) rectangleValues.getX();
		int y = (int) rectangleValues.getY();

		int w = (int) rectangleValues.getWidth();
		int h = (int) rectangleValues.getHeight();

		if (x >= 0 && y >= 0 && w > 0 && h > 0) {
			if (rectangleValues.getMaxX() <= 1024 && rectangleValues.getMaxY() <= 1024)
				return true;
		}
		return false;
	}

	// Region search check the dimensions.
	public Boolean isRegionsearch(int w, int h)
	{
		if (w > 0 && h > 0) {
			return true;
		}
		return false;
	}
}
