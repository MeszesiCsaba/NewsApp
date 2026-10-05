/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.newsapp;

import com.mycompany.newsapp.database.Database;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author mcsab
 */
public class NewsApp {

    public static void main(String[] args) {
        try {
            Connection connection = Database.getConnection();
            
            System.out.println("Database connection succesful!");
            
            connection.close();
        } catch (SQLException e){
            System.out.println("Database connection failed!");
            System.out.println(e.getMessage());
        }
    }
}
