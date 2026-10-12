/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ro.umfst.newsapp.database;

import java.sql.Timestamp;
import java.util.List;
import ro.umfst.newsapp.News;
/**
 *
 * @author mcsab
 */
public class DatabaseTest {

    public static void main(String[] args) {
        try {
            // Create a test news article.
            Timestamp now = new Timestamp(System.currentTimeMillis());

            News news = new News(
                    "Test news",
                    "This is a test description.",
                    "Test source",
                    "Test author",
                    "Test publisher",
                    now,
                    "https://example.com/test-news",
                    now,
                    now,
                    "HU",
                    null
            );

            // Save the news article.
            DatabaseManager.addNews(news);
            System.out.println("News saved successfully.");

            // Read all news articles from the database.
            List<News> newsList = DatabaseManager.getAllNews();

            System.out.println("Number of news articles: " + newsList.size());

            for (News item : newsList) {
                System.out.println("Title: " + item.getTitle());
                System.out.println("Source: " + item.getSource());
                System.out.println("URL: " + item.getURL());
            }

        } catch (Exception e) {
            System.out.println("Database test failed:");
            e.printStackTrace();
        }
    }
}