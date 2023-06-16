import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/*
 * fileManager class, we created this file for dealing with files.
 * This file contains only one function called WriteInFile.
 * We choose to make this function in a separate class, because of
 * the principle of single responsibility.
 */

public class FileManager
{
	/*
	 * We created this boolean object because we need 
	 * to delete every thing in the file if we use the 
	 * FileWriter object for the first time, but if we
	 * used it a gain it will continue writing without 
	 * deleting the content of the file.
	 */
	Boolean isFirst;
	FileManager()
	{
		isFirst = true;
	}
	public void WriteInFile(String line)
	{
		String filePath = "output.txt";
		try {

	        File file = new File(filePath);
	        FileWriter fw;

	        // if file does not exists, then create it
	        if (!file.exists())
	        {
	            file.createNewFile();
	        }
	        if (isFirst == true)	// Delete the content of the file.
	        {
	        	fw = new FileWriter(file.getAbsoluteFile());
	        	isFirst = false;
	        }
	        else					// Continue writing.
	        {
	        	fw = new FileWriter(file.getAbsoluteFile(), true);
	        }
	        BufferedWriter bw = new BufferedWriter(fw);
	        bw.write(line + "\n");
	        bw.close();

	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
}
