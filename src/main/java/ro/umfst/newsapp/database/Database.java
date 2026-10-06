/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ro.umfst.newsapp.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
        
}
