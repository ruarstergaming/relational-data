//imports
import java.util.Scanner;
import java.sql.*;

//Ruaraidh Nicolson, 200014517, March 2021

public class QueryDB {
    public static void main(String[] args) throws Exception {

        
		//Make sure that when running from the command line the user actually provided the file paths to the database file and create statements file
        if (args.length < 2) {
			//Print an error message and quit the program
			System.out.println("Usage: java src/PopulateDB.java <database_file_path> <integer_number_for_query>");
			System.exit(0);
		}

		//Handles the arguments to for use in the querying of the database
        String dbFileName = args[0];
        int queryNo = Integer.parseInt(args[1]);

        if (queryNo < 1 || queryNo > 10) {
			//Print an error message and quit the program
			System.out.println("Error: Query command number invalid, please enter:");
            System.out.println("1: For displaying names of all movies in the database");
            System.out.println("2: For displaying names of actors who starred in a particular movie in the database");
            System.out.println("3: For displaying plots of movies starring a particular actor and a particular director");
            System.out.println("4: For displaying the directors of a movie with a particular actor");
            System.out.println("5: For displaying the title, runtime and plot of movie released on a particular month in a particular year");
			System.out.println("6: For displaying the awards of a particular actor");
            System.out.println("7: For displaying the awards of a particular movie");
            System.out.println("8: For displaying names of actors who starred in a particular movie in the database with user input");
            System.out.println("9: For displaying plots of movies starring a particular actor and a particular director with user input");
            System.out.println("10: For displaying the directors of a movie with a particular actor with user input");
            System.exit(0);
		}

		//Create an object that will query the database and tables in said DB
		QueryDB queryDatabase = new QueryDB();

		//Run the method to run the query passing the databse used and the query index being performed
		queryDatabase.QueryTables(dbFileName, queryNo);

    }

    //Method that wil create the database file name and then give it tables.
    private void QueryTables(String dbFileName, int queryNo) throws Exception {
		
		//Resets the connection in case somehow it wasn't closed in the finally statement
		Connection connection = null;
		
		//Try to connect to the database then run and print the results of the query
		try {

			// Connect to the Database Management System
			String dbUrl = "jdbc:sqlite:" + dbFileName;
			connection = DriverManager.getConnection(dbUrl);
            
            //Open a scanner object to take in user input
            Scanner movieScanner = new Scanner(System.in);
            
            if(queryNo == 1){

                Statement queryStatement = connection.createStatement();

                //Run the query 
                ResultSet resultSet = queryStatement.executeQuery("SELECT Title FROM Movies");

                //While Loop to display the results of the query
                System.out.println("\ncontents of table:");
                while (resultSet.next()) {
    
                    //Store the current result in the 1st column of the query in a string
                    String title = resultSet.getString(1);
                    
                    //Display that string
                    System.out.println("Movie Title: " + title);
                    System.out.println();
                }

                queryStatement.close();
            } 

            else if(queryNo == 2){

                Statement queryStatement = connection.createStatement();

                //Run the query 
                ResultSet resultSet = queryStatement.executeQuery("SELECT FullName FROM Actors, Movies, FilmCast WHERE Title = 'Spider-Man Homecoming' AND Actors.ActorID = FilmCast.ActorID AND Movies.MovieID = FilmCast.MovieID");


                //While Loop to display the results of the query
                System.out.println("\ncontents of table:");
                while (resultSet.next()) {
    
                    //Store the current result in the 1st column of the query in a string
                    String actorName = resultSet.getString(1);
                    
                    //Display the result
                    System.out.println("Actor Name: " + actorName);
                    System.out.println();
                }

                queryStatement.close();

            } 

            else if(queryNo == 3){

                Statement queryStatement = connection.createStatement();

                //Run the query 
                ResultSet resultSet = queryStatement.executeQuery("SELECT Plot FROM Actors, Movies, FilmCast, FilmDirectors WHERE FullName = 'James McAvoy' AND Director = 'David Leitch' AND Actors.ActorID = FilmCast.ActorID AND Movies.MovieID = FilmCast.MovieID AND Movies.MovieID = FilmDirectors.MovieID");

                //While Loop to display the results of the query
                System.out.println("\ncontents of table:");
                while (resultSet.next()) {
    
                    //Store the current result in the 1st column of the query in a string
                    String plot = resultSet.getString(1);
                    
                    //Display the result
                    System.out.println("Plot: " + plot);
                    System.out.println();
                }
                queryStatement.close();
            } 

            else if(queryNo == 4){

                Statement queryStatement = connection.createStatement();

                //Run the query 
                ResultSet resultSet = queryStatement.executeQuery("SELECT Director FROM Actors, Movies, FilmCast, FilmDirectors WHERE  FullName = 'Robert Downey Jr.' AND Actors.ActorID = FilmCast.ActorID AND Movies.MovieID = FilmCast.MovieID AND Movies.MovieID = FilmDirectors.MovieID");

                //While Loop to display the results of the query
                System.out.println("\ncontents of table:");
                while (resultSet.next()) {
    
                    //Store the current result in the 1st column of the query in a string
                    String director = resultSet.getString(1);
                    
                    //Display the result
                    System.out.println("Director Name: " + director);
                    System.out.println();
                }
                queryStatement.close();
            } 

            else if(queryNo == 5){

                Statement queryStatement = connection.createStatement();

                //Run the query 
                ResultSet resultSet = queryStatement.executeQuery("SELECT Title, Plot, RunTime, Ratings FROM Movies WHERE ReleaseDate LIKE '__/07/2017'");

                //While Loop to display the results of the query
                System.out.println("\ncontents of table:");
                while (resultSet.next()) {
    
                    //Store the current result in the 1st column of the query in a string
                    String title = resultSet.getString(1);
                    String plot = resultSet.getString(2);
                    String runTime = resultSet.getString(3);
                    int ratings = resultSet.getInt(4);
                    
                    //Display the results
                    System.out.println("Title: " + title);
                    System.out.println();
                    System.out.println("Plot: " + plot);
                    System.out.println();
                    System.out.println("Run Time: " + runTime);
                    System.out.println();
                    System.out.println("Rating: " + ratings);
                    System.out.println();
                    
                }
                queryStatement.close();
            } 

            else if(queryNo == 6){
                
                Statement queryStatement = connection.createStatement();

                //Run the query 
                ResultSet resultSet = queryStatement.executeQuery("SELECT AwardName FROM Actors, Awards WHERE FullName = 'Heath Ledger' AND Actors.ActorID = Awards.ActorID");

                //While Loop to display the results of the query
                System.out.println("Heath Ledger has been awarded:");
                while (resultSet.next()) {
    
                    //Store the current result in the 1st column of the query in a string
                    String award = resultSet.getString(1);
                    
                    //Display the result
                    System.out.println("Award Name: " + award);
                    System.out.println();
                }
                queryStatement.close();
            }

            else if(queryNo == 7){

                Statement queryStatement = connection.createStatement();

                //Run the query 
                ResultSet resultSet = queryStatement.executeQuery("SELECT AwardName FROM Movies, Awards WHERE Title = 'Joker' AND Movies.MovieID = Awards.MovieID");
                
                //While Loop to display the results of the query
                System.out.println("Joker has been awarded:");
                while (resultSet.next()) {
    
                    //Store the current result in the 1st column of the query in a string
                    String award = resultSet.getString(1);
                    
                    //Display the result
                    System.out.println("Award Name: " + award);
                    System.out.println();
                }
                queryStatement.close();
            }

            
            else if(queryNo == 8){

                //Get user input from the command line
                System.out.println("Please enter the movie title you want the actors names of:");
                String userInputTitle = movieScanner.nextLine();

                //Prepare a statement for the query
                PreparedStatement queryPreparedStatement = connection.prepareStatement("SELECT FullName FROM Actors, Movies, FilmCast WHERE Title = ? AND Actors.ActorID = FilmCast.ActorID AND Movies.MovieID = FilmCast.MovieID");
                
                //Insert the string of the user input into the prepared statement
                queryPreparedStatement.setString(1, userInputTitle);

                //Run the query 
                ResultSet resultSet = queryPreparedStatement.executeQuery();

                //While Loop to display the results of the query
                System.out.println("\ncontents of table:");
                while (resultSet.next()) {
    
                    //Store the current result in the 1st column of the query in a string
                    String actorName = resultSet.getString(1);
                    
                    //Display the result
                    System.out.println("Actor Name: " + actorName);
                    System.out.println();
                }

                

            } 

            else if(queryNo == 9){

                //Get user inputs from the command line
                System.out.println("Please enter the actor from the movies you want to see the plots of:");
                String userInputActor = movieScanner.nextLine();
                System.out.println("Please enter the director who directed the movies you want to see the plots of:");
                String userInputDirector = movieScanner.nextLine();

                //Prepare a statement for the query
                PreparedStatement queryPreparedStatement = connection.prepareStatement("SELECT Plot FROM Actors, Movies, FilmCast, FilmDirectors WHERE FullName = ? AND Director = ? AND Actors.ActorID = FilmCast.ActorID AND Movies.MovieID = FilmCast.MovieID AND Movies.MovieID = FilmDirectors.MovieID" );
                
                //Insert the strings of the user inputs into the prepared statement
                queryPreparedStatement.setString(1, userInputActor);
                queryPreparedStatement.setString(2, userInputDirector);

                //Run the query
                ResultSet resultSet = queryPreparedStatement.executeQuery();

                //While Loop to display the results of the query
                System.out.println("\ncontents of table:");
                while (resultSet.next()) {
    
                    //Store the current result in the 1st column of the query in a string
                    String plot = resultSet.getString(1);
                    
                    //Display the result
                    System.out.println("Plot: " + plot);
                    System.out.println();
                }

            } 

            else if(queryNo == 10){

                //Get user input from the command line
                System.out.println("Please enter the actor who you want to see the directors who directed them:");
                String userInputActor = movieScanner.nextLine();

                //Prepare a statement for the query
                PreparedStatement queryPreparedStatement = connection.prepareStatement("SELECT Director FROM Actors, Movies, FilmCast, FilmDirectors WHERE  FullName = ? AND Actors.ActorID = FilmCast.ActorID AND Movies.MovieID = FilmCast.MovieID AND Movies.MovieID = FilmDirectors.MovieID");
                
                //Insert the string of the user input into the prepared statement
                queryPreparedStatement.setString(1, userInputActor);


                //Run the query
                ResultSet resultSet = queryPreparedStatement.executeQuery();

                //While Loop to display the results of the query
                System.out.println("\ncontents of table:");
                while (resultSet.next()) {
    
                    // Store the current result in the 1st column of the query in a string
                    String director = resultSet.getString(1);
                    
                    //Display the result
                    System.out.println("Director Name: " + director);
                    System.out.println();
                }

            }

            //Close the scanner used for user input
            movieScanner.close();
			
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
