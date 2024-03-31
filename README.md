First navigate to the src folder: cd src
Then to compile run:
javac -cp ./lib/sqlite-jdbc-3.34.0.jar InitialiseDB.java
javac -cp ./lib/sqlite-jdbc-3.34.0.jar PopulateDB.java
javac -cp ./lib/sqlite-jdbc-3.34.0.jar QueryDB.java

To run the program:
First to create the database run: java -cp "./lib/sqlite-jdbc-3.34.0.jar;." InitialiseDB P3Database ../Files/CS1003P2RelationalSchema.txt
Then to populate it run:
java -cp "../lib/sqlite-jdbc-3.34.0.jar;." PopulateDB P3Database ../Files/MoviesData.txt
java -cp "../lib/sqlite-jdbc-3.34.0.jar;." PopulateDB P3Database ../Files/ActorsData.txt
java -cp "../lib/sqlite-jdbc-3.34.0.jar;." PopulateDB P3Database ../Files/CastData.txt
java -cp "../lib/sqlite-jdbc-3.34.0.jar;." PopulateDB P3Database ../Files/AwardData.txt
java -cp "../lib/sqlite-jdbc-3.34.0.jar;." PopulateDB P3Database ../Files/DirectorsData.txt
java -cp "../lib/sqlite-jdbc-3.34.0.jar;." PopulateDB P3Database ../Files/GenresData.txt

(NOTE: Be sure to run the movie data and actor data first to maintain referential integrity with connected tables)

Finally run the queryies with this:
java -cp "../lib/sqlite-jdbc-3.34.0.jar;." QueryDB P3Database 1

Just replace the 1 at the end with another number depending on the query being run.
NOTE: for query 8-10, when prompted, enter:
8. Spider-Man Homecoming
9. Tom Holland
    Jon Watts
10. Morgan Freeman