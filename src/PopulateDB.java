//imports
import java.util.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;

//Ruaraidh Nicolson, 200014517, March 2021

public class PopulateDB {
    public static void main(String[] args) throws Exception {

		//Make sure that when running from the command line the user actually provided the file paths to the database file and create statements file
        if (args.length < 2) {
			//Print an error message and quit the program
			System.out.println("Usage: java src/PopulateDB.java <database_file_path> <database_create_statements_file_path>");
			System.exit(0);
		}

		//Handles the arguments to for use in the file reading and store them
        String dbFileName = args[0];
		String tableFileName = args[1];

		//Create an object that will create the database and tables in said DB
		PopulateDB populateDatabase = new PopulateDB();

		//Run the method to create the database and tables, passing the file names to it.
		populateDatabase.populateTables(dbFileName, tableFileName);
	}

    //Method that wil create the database file name and then give it tables.
    private void populateTables(String dbFileName, String tableFileName) throws Exception {
		
		//Resets the connection in case somehow it wasn't closed in the finally statement
		Connection connection = null;

		//Initialise the table name string
        String tableBeingPopulated = "";
		
		//Try to read in the file of the database schema and create a db file and tables
		try {

			//Declare a path object using the string path name for the schema
			Path tableFilePath = Path.of(tableFileName);
    
			//Checks if the file either does not exist or is not readable
			if (!Files.exists(tableFilePath) || !Files.isReadable(tableFilePath)){
		
				//if so, throw exception
				throw new Exception("file does not exist/cannot read file");
		
			}
		
			//Do a number of if else statements to assign a variable to be used in the insert statements depending on the file name
            if(tableFileName.contains("Movie")){
                tableBeingPopulated = "Movies";
            } 
            else if(tableFileName.contains("Actor")){
                tableBeingPopulated = "Actors";
            }
            else if(tableFileName.contains("Award")){
                tableBeingPopulated = "Awards";
            }
            else if(tableFileName.contains("Cast")){
                tableBeingPopulated = "FilmCast";                
            }
            else if(tableFileName.contains("Director")){
                tableBeingPopulated = "FilmDirectors";                
            }
            else if(tableFileName.contains("Genre")){
                tableBeingPopulated = "FilmGenres";                
            }

			//Read all lines of the file and store it in a list
			List<String> tableData = Files.readAllLines(Path.of(tableFileName));
			Iterator<String> tableDataIterator = tableData.iterator();

			String fieldNames = tableDataIterator.next();

			// Connect to the Database Management System
			String dbUrl = "jdbc:sqlite:" + dbFileName;
			connection = DriverManager.getConnection(dbUrl);

			//Loop for each line in the file
            while(tableDataIterator.hasNext()){

				//Get the current row from the file then create an array for each of the values in the current row
                String currentRow = tableDataIterator.next();
                String[] currentRowData = currentRow.split(",");

				//Start string building an insert query so it can include the data from the file
				String insertQuery = "";
				insertQuery += "INSERT INTO ";
				insertQuery += tableBeingPopulated;
				insertQuery += "(" + fieldNames + ")";
				insertQuery += "VALUES (";

				//Loop to add the data from the file into the insert query
				for(String currentdata : currentRowData){
					insertQuery += currentdata + ", ";
				}

				//Clean up the query so that the last comma wont cause a syntax error
				insertQuery = insertQuery.substring(0, (insertQuery.length()-2));

				//Close the query
				insertQuery += ")";

				//Now to actually run the insert query using the string built query from before
				Statement insertDataStatement = connection.createStatement();
				insertDataStatement.executeUpdate(insertQuery);

				insertDataStatement.close();

            }
			

		} 
		
		//if an exception occurs display the error message
		catch (SQLException e) {
			System.out.println(e.getMessage());
		} 
		
		//Finally no matter if an exception occurred or not close the connection
		finally {
			if (connection != null) connection.close();
		}
    }
}
