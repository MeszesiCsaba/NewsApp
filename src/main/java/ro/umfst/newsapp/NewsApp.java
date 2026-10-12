/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package ro.umfst.newsapp;

import ro.umfst.newsapp.ui.MainWindow;
import javax.swing.SwingUtilities;

import ro.umfst.newsapp.rss.RSSConfigReader;
import ro.umfst.newsapp.rss.RSSSource;
import ro.umfst.newsapp.rss.RSSHttpClient;
import ro.umfst.newsapp.rss.RSSParser;

import ro.umfst.newsapp.database.DatabaseManager;
import ro.umfst.newsapp.database.Database;

import java.sql.SQLException;
import java.util.List;
import java.io.IOException;

/**
 *
 * @author mcsab
 */
public class NewsApp {

    public static void main(String[] args) {
        try {
            Database.createTables();
            System.out.println("Database tables created successfully.");
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
            return;
        }

        // Start the graphical user interface.
        SwingUtilities.invokeLater(() -> {
            MainWindow window = new MainWindow();
            window.setVisible(true);
        });
    }
}

