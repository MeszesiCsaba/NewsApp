/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ro.umfst.newsapp.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.File;

/**
 *
 * @author mcsab
 */

//This class handles the connection to the SQLite database.
public class Database {
    //Name and location of our SQLite database file.
    private static final String URL = "jdbc:sqlite:newsapp.db";
    
    /**
     * Creates a connection to the SQLite database.
     * 
     * @return the database connection
     * @throws SQLException if the connection cannot be created 
     */
    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(URL);
    }
    public static boolean databaseExists(){
        File database = new File("newsapp.db");
        return database.exists();
    } 
    public static void createTables() throws SQLException {
         String newsSQL = "CREATE TABLE IF NOT EXISTS news ("
            + "title TEXT NOT NULL,"
            + "description TEXT,"
            + "source TEXT NOT NULL,"
            + "author TEXT,"
            + "publisher TEXT,"
            + "publish_date TIMESTAMP,"
            + "url TEXT NOT NULL,"
            + "creation_date TIMESTAMP NOT NULL,"
            + "last_modified_date TIMESTAMP NOT NULL,"
            + "language TEXT NOT NULL,"
            + "cover_image TEXT,"
            + "PRIMARY KEY (source, url)"
            + ")";

    String eventsSQL = "CREATE TABLE IF NOT EXISTS events ("
            + "title TEXT NOT NULL,"
            + "description TEXT,"
            + "source TEXT NOT NULL,"
            + "author TEXT,"
            + "publisher TEXT,"
            + "publish_date TIMESTAMP,"
            + "url TEXT NOT NULL,"
            + "creation_date TIMESTAMP NOT NULL,"
            + "last_modified_date TIMESTAMP NOT NULL,"
            + "language TEXT NOT NULL,"
            + "cover_image TEXT,"
            + "event_datetime TIMESTAMP NOT NULL,"
            + "location TEXT NOT NULL,"
            + "PRIMARY KEY (title, event_datetime, location)"
            + ")";

    try (Connection connection = getConnection();
         java.sql.Statement statement = connection.createStatement()) {

        statement.execute(newsSQL);
        statement.execute(eventsSQL);
        }
    }
}
