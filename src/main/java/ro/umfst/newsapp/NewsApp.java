/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package ro.umfst.newsapp;


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
            
            List<News> newsList = DatabaseManager.getAllNews();

            if (newsList.isEmpty()) {
                System.out.println("The database has no news.");
            } else {
                System.out.println("The database already contains news.");
                System.out.println("Number of news articles: " + newsList.size());
            }
            
            List<RSSSource> sources = RSSConfigReader.readSources();

            System.out.println("RSS sources loaded: " + sources.size()); 
            

            int savedCount = 0;
            int skippedCount = 0;

            for (RSSSource source : sources) {
                try {
                    System.out.println("Downloading: " + source.getName());

                    String content = RSSHttpClient.getContent(source.getURL());
                    String language = RSSParser.getLanguage(content);

                    if (language == null) {
                        System.out.println("Language not found. Skipping feed.");
                        continue;
                    }

                    List<News> parsedNews = RSSParser.parseNews(
                            content,
                            source.getName(),
                            language
                    );

                    for (News news : parsedNews) {
                        News existingNews = DatabaseManager.getNews(
                                news.getSource(),
                                news.getURL()
                        );

                        if (existingNews == null) {
                            DatabaseManager.addNews(news);
                            savedCount++;
                        } else {
                            skippedCount++;
                        }
                    }

                    System.out.println(
                            "Processed " + parsedNews.size()
                            + " news articles from " + source.getName()
                    );

                } catch (Exception e) {
                    System.out.println(
                            "Could not process " + source.getName()
                            + ": " + e.getMessage()
                    );
                }
            }

            System.out.println("New articles saved: " + savedCount);
            System.out.println("Existing articles skipped: " + skippedCount);
            
        } catch (SQLException | IOException e) {
            System.out.println("Application error: " + e.getMessage());
        }
    }
}
