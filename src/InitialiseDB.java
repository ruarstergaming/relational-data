//imports
import java.util.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;

//Ruaraidh Nicolson, 200014517, March 2021

public class InitialiseDB {
    
    public static void main(String[] args) throws Exception {

		//Make sure that when running from the command line the user actually provided the file paths to the database file and create statements file
        if (args.length < 2) {
			//Print an error message and quit the program
			System.out.println("Usage: java src/InitialiseDB.java <database_file_path> <database_create_statements_file_path>");
			System.exit(0);
		}

		//Handles the arguments to for use in the file reading and store them
        String dbFileName = args[0];
		String schemaFileName = args[1];

		//Create an object that will create the database and tables in said DB
		InitialiseDB createDatabase = new InitialiseDB();

		//Run the method to create the database and tables, passing the file names to it.
		createDatabase.createTable(dbFileName, schemaFileName);
	}

	//Method that wil create the database file name and then give it tables.
    private void createTable(String dbFileName, String schemaFileName) throws Exception {
		
		//Resets the connection in case somehow it wasn't closed in the finally statement
		Connection connection = null;
		
		//Try to read in the file of the database schema and create a db file and tables
		try {

			//Declare a path object using the string path name for the schema
			Path schemaFilePath = Path.of(schemaFileName);
    
			//Checks if the file either does not exist or is not readable
			if (!Files.exists(schemaFilePath) || !Files.isReadable(schemaFilePath)){
		
				//if so, throw exception
				throw new Exception("file does not exist/cannot read file");
		
			}
		
			//Read all lines of the file and store it in a list
			List<String> createStatements = Files.readAllLines(Path.of(schemaFileName));
			Iterator<String> createFileIterator = createStatements.iterator();


			// Connect to the Database Management System
			String dbUrl = "jdbc:sqlite:" + dbFileName;
			connection = DriverManager.getConnection(dbUrl);
            
			//Declare statements to create the tables using the list and an iterator
			//It also drops the tables if they already exist then recreates them
            Statement movieCreateStatement = connection.createStatement();
			movieCreateStatement.executeUpdate("DROP TABLE IF EXISTS Movies");
			movieCreateStatement.executeUpdate(createFileIterator.next());
			movieCreateStatement.close();

			Statement actorsCreateStatement = connection.createStatement();
			actorsCreateStatement.executeUpdate("DROP TABLE IF EXISTS Actors");
			actorsCreateStatement.executeUpdate(createFileIterator.next());
			actorsCreateStatement.close();

			Statement awardsCreateStatement = connection.createStatement();
			awardsCreateStatement.executeUpdate("DROP TABLE IF EXISTS Awards");
			awardsCreateStatement.executeUpdate(createFileIterator.next());
			awardsCreateStatement.close();

			Statement castCreateStatement = connection.createStatement();
			castCreateStatement.executeUpdate("DROP TABLE IF EXISTS FilmCast");
			castCreateStatement.executeUpdate(createFileIterator.next());
			castCreateStatement.close();

			Statement genreCreateStatement = connection.createStatement();
			genreCreateStatement.executeUpdate("DROP TABLE IF EXISTS FilmGenres");
			genreCreateStatement.executeUpdate(createFileIterator.next());
			genreCreateStatement.close();

			Statement directorCreateStatement = connection.createStatement();
			directorCreateStatement.executeUpdate("DROP TABLE IF EXISTS FilmDirectors");
			directorCreateStatement.executeUpdate(createFileIterator.next());
			directorCreateStatement.close();

			//If no exceptions occured during the creation 
			//then the tables have been created correctly and print out success message
			System.out.println("ok");	
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
