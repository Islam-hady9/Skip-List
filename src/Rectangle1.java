import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
// import java.util.ArrayList;		// We used them while testing.
// import java.awt.Rectangle;		// We used them while testing.
import java.util.Scanner;

// On my honor:
//
// - I have not used source code obtained from another student,
// or any other unauthorized source, either modified or
// unmodified.
//
// - All source code and documentation used in my program is
// either my original work, or was derived by me from the
// source code published in the textbook for this course.
//
// - I have not discussed coding details about this project with
// anyone other than my partner (in the case of a joint
// submission), instructor, ACM/UPE tutors or the TAs assigned
// to this course. I understand that I may discuss the concepts
// of this program with other students, and that another student
// may help me debug my program so long as neither of us writes
// anything during the discussion or modifies any computer file
// during the discussion. I have violated neither the spirit nor
// letter of this restriction.

/**
 * The class containing the main method, the entry point of the application. It
 * will take a command line file argument which include the commands to be read
 * and creates the appropriate SkipList object and outputs the correct results
 * to the console as specified in the file.
 *
 * @author (Your name here)
 * 
 * @version (Date here)
 */
public class Rectangle1 // The base class
{
	/**
	 * The entry point of the application.
	 *
	 * @param args
	 *             The name of the command file passed in as a command line
	 *             argument.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	/*
	 * When this annotation is used with the parameter "unchecked"
	 * the compiler will not generate warnings related to unchecked
	 * operations and warnings.
	 * We used it to ignore unnecessary or incorrect warnings.
	 * @SuppressWarnings({ "unchecked", "rawtypes" })
	 */
	public static void main(String[] args) throws ClassNotFoundException, SQLException
	{
//		 // First to be inserted.
//		 Rectangle firstRectangle = new Rectangle(-1, -20, 3, 4);
//		 KVPair firstPair = new KVPair("r_r", firstRectangle);
//
//		 // Second to be inserted.
//		 Rectangle secondRectangle = new Rectangle(7 ,-8, 1, 3);
//		 KVPair secondPair = new KVPair("rec", secondRectangle);
//
//		 Rectangle thirdRectangle = new Rectangle(1, 1, 0, 0);
//		 KVPair thirdPair = new KVPair("virtual_rec0", thirdRectangle);
//		 
//		 Rectangle fourRectangle = new Rectangle(108, 136, 55, 103);
//		 KVPair fourPair = new KVPair("r12", fourRectangle);
//
//		 sl.displayList();
//
//		 Database obj = new Database();
//		 obj.insert(firstPair);
//		 obj.insert(secondPair);
//		 obj.insert(thirdPair);
//		 obj.insert(fourPair);
//		 obj.dump();
//		 
//		 obj.remove("r12");
//		 obj.dump();
//		 obj.intersections();

//		 KVPair re = sl.removeByValue(secondRectangle);
//		 if (re != null) {
//		 	System.out.println("here");
//		 	System.out.println(re.toString());
//		 	sl.displayList();
//		 }
//		 sl.displayList();

		// sl.dump();

		// sl.remove(firstPair.getKey());
		// sl.displayList();

		// // Search
		// ArrayList<KVPair<String, Rectangle>> AL = sl.search("Ahmed");
		// for (int i = 0; i < AL.size(); i++)
		// {
		// 	System.out.println(AL.get(i).getKey());
		// }
		// System.out.println("size of searching = " + AL.size());
		// sl.displayList();

		// the file object
		File file = null;
		// Attempts to open the file and scan through it
		try {
			// takes the first command line argument and opens that file
			file = new File(args[0]);

			// creates a scanner object
			Scanner scanner = new Scanner(file);

			// creates a command processor object
			CommandProcessor cmdProc = new CommandProcessor();
			// reads the entire file and processes the commands
			// line by line
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				// determines if the file has more lines to read
				if (!line.trim().isEmpty()) {
					cmdProc.processor(line.trim());
				}
			}
			// closes the scanner
			scanner.close();
		}
		// catches the exception if the file cannot be found
		// and outputs the correct information to the console
		catch (FileNotFoundException e) {
			System.out.println("Invalid file");
			/* 
			 * List of the method calls
			 * when an exception occurs
			 * print it's messages.
			*/
			e.printStackTrace();
		}
		// Scanner scanner = new Scanner(System.in);
		// String line = scanner.nextLine();
		// System.out.println(line.trim());

		// String line = scanner.nextLine();
		// line = line.trim();
		// String[] arr = line.split("\\s+"); // spaces, tabs
		// for (int i = 0; i < arr.length; i++)
		// System.out.println(arr[i]);
	}
}
