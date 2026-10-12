/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package ro.umfst.newsapp;


import ro.umfst.newsapp.database.DatabaseManager;
import ro.umfst.newsapp.database.Database;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.io.IOException;

/**
 *
 * @author mcsab
 */
public class NewsApp {

    public static void main(String[] args) {
        
        /*
        try {
            Connection connection = Database.getConnection();
            
            System.out.println("Database connection succesful!");
            
            connection.close();
        } catch (SQLException e){
            System.out.println("Database connection failed!");
            System.out.println(e.getMessage());
        }
*/      
        
        try {
            Database.createTables();
            System.out.println("Database tables created successfully.");
            
            List<News> newsList = DatabaseManager.getAllNews();

            if (newsList.isEmpty()) {
                System.out.println("The database has no news.");
            } else {
                System.out.println("The database already contains news.");
                System.out.println("Number of news articles: " + newsList.size());
            }
            
            List<RSSSource> sources = RSSConfigReader.readSources();

            System.out.println("RSS sources loaded: " + sources.size());

            
            
            System.out.println("\nChecking RSS source status codes:");
            
            

            for (RSSSource source : sources) {
                int statusCode = RSSHttpClient.getStatusCode(source.getURL());

                System.out.println(
                        source.getName() + " - HTTP status: " + statusCode
                );
            }
            
            if (!sources.isEmpty()) {
                RSSSource source = sources.get(0);

                try {
                    String content = RSSHttpClient.getContent(source.getURL());

                    System.out.println("RSS download successful.");
                    System.out.println(content.substring(
                            0, Math.min(500, content.length())));
                    
                    String language = RSSParser.getLanguage(content);
                    System.out.println("Feed language: " + language);
                    
                    List<News> parsedNews = RSSParser.parseNews(
                    content,
                    source.getName(),
                    language
            );

            System.out.println("Parsed news articles: " + parsedNews.size());

            if (!parsedNews.isEmpty()) {
                System.out.println("First title: " + parsedNews.get(0).getTitle());
}
                } catch (Exception e) {
                    System.out.println("RSS download failed: " + e.getMessage());
                }
            }


            for (RSSSource source : sources) {
                System.out.println(source);
            }
        } catch (SQLException | IOException e) {
            System.out.println("Application error: " + e.getMessage());
        }
    }
}
