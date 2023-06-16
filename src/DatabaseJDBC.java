import java.sql.*;
import java.util.*;
import java.awt.Rectangle;

public class DatabaseJDBC
{
	private Connection connection;		// To establish a connection.
	DatabaseJDBC() throws ClassNotFoundException, SQLException
	{
		// Loads MySQL JDBC -That we added to our project paths- driver class into memory.
        Class.forName("com.mysql.cj.jdbc.Driver");
        // DriverManager class is used to create a Connection object and method getConnection returns that object.
        //getConnection takes the local hosts, root, and password.
        this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Algorithm_Project_One", "root", "Asd9*966");
	}
    // get data from database as list.
    public List<KVPair<String, Rectangle>> getRectangleData() throws SQLException
    {
    	// Saving all rectangles and their named retrieved from the database in this list.
        List<KVPair<String, Rectangle>> rectangles = new ArrayList<>();
        String query = "SELECT * FROM rectangles";
        // Statement statement = connection.createStatement();
        // ResultSet resultSet = statement.executeQuery(query);
        ResultSet resultSet = connection.createStatement().executeQuery(query);		// creating the ResultSet object.
        while (resultSet.next())
        {
        	// Getting the data from the resultSet object.
            String rectangleName = resultSet.getString("rectangleName");
            int x = resultSet.getInt("x");
            int y = resultSet.getInt("y");
            int w = resultSet.getInt("w");
            int h = resultSet.getInt("h");
            // Creating a KVPair object with no name and Inserting it to the list.
            rectangles.add(new KVPair<String, Rectangle>(rectangleName, new Rectangle(x, y, w, h)));
        }
        return rectangles;
    }
    public void insertRectangleData(KVPair<String, Rectangle> pair) throws SQLException
    {
        String query = "insert into rectangles (rectangleName, x, y, w, h) values (?, ?, ?, ?, ?)";
        /*
         * prepareStatement() method that takes the query string
         * retrieving the PreparedStatement object.
         */
        PreparedStatement statement = connection.prepareStatement(query);
        
        Rectangle rec = pair.getValue();
        statement.setString(1, pair.getKey());
        statement.setInt(2, (int)rec.getX());
        statement.setInt(3, (int)rec.getY());
        statement.setInt(4, (int)rec.getWidth());
        statement.setInt(5, (int)rec.getHeight());
        statement.executeUpdate();
    }
}
