/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ro.umfst.newsapp.database;

import ro.umfst.newsapp.News;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;

/**
 *
 * @author mcsab
 */
public class DatabaseManager {
    //Saves a news article in the database.
    
    public static void addNews(News news) throws SQLException {

        String sql = "INSERT INTO news ("
                + "title, description, source, author, publisher, "
                + "publish_date, url, creation_date, last_modified_date, "
                + "language, cover_image"
                + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, news.getTitle());
            statement.setString(2, news.getDescription());
            statement.setString(3, news.getSource());
            statement.setString(4, news.getAuthor());
            statement.setString(5, news.getPublisher());
            statement.setTimestamp(6, news.getPublishDate());
            statement.setString(7, news.getURL());
            statement.setTimestamp(8, news.getCreationDate());
            statement.setTimestamp(9, news.getLastEdit());
            statement.setString(10, news.getLanguage());
            statement.setString(11, news.getCoverImage());

            statement.executeUpdate();
        }
    }
    
    public static News getNews(String source, String url) throws SQLException {

        String sql = "SELECT * FROM news WHERE source = ? AND url = ?";

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, source);
            statement.setString(2, url);

            try (ResultSet result = statement.executeQuery()) {

                if (result.next()) {
                    return new News(
                            result.getString("title"),
                            result.getString("description"),
                            result.getString("source"),
                            result.getString("author"),
                            result.getString("publisher"),
                            result.getTimestamp("publish_date"),
                            result.getString("url"),
                            result.getTimestamp("creation_date"),
                            result.getTimestamp("last_modified_date"),
                            result.getString("language"),
                            result.getString("cover_image")
                    );
                }
            }
        }

        return null;
    }
    
    public static List<News> getAllNews() throws SQLException{
        
        List<News> newsList = new ArrayList<>();
        
        String sql = "SELECT * FROM news";
        
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()){
            
            while (result.next()){
                News news = new News(
                        result.getString("title"),
                    result.getString("description"),
                    result.getString("source"),
                    result.getString("author"),
                    result.getString("publisher"),
                    result.getTimestamp("publish_date"),
                    result.getString("url"),
                    result.getTimestamp("creation_date"),
                    result.getTimestamp("last_modified_date"),
                    result.getString("language"),
                    result.getString("cover_image")
            );

            newsList.add(news);
            }
        }
    return newsList;
}
    public static void updateNews(News news) throws SQLException {

        String sql = "UPDATE news SET "
                + "title = ?, "
                + "description = ?, "
                + "author = ?, "
                + "publisher = ?, "
                + "publish_date = ?, "
                + "last_modified_date = ?, "
                + "language = ?, "
                + "cover_image = ? "
                + "WHERE source = ? AND url = ?";

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, news.getTitle());
            statement.setString(2, news.getDescription());
            statement.setString(3, news.getAuthor());
            statement.setString(4, news.getPublisher());
            statement.setTimestamp(5, news.getPublishDate());
            statement.setTimestamp(6, news.getLastEdit());
            statement.setString(7, news.getLanguage());
            statement.setString(8, news.getCoverImage());

            statement.setString(9, news.getSource());
            statement.setString(10, news.getURL());

            statement.executeUpdate();
        }
}
    
    public static void deleteNews(String source, String url) throws SQLException {

        String sql = "DELETE FROM news WHERE source = ? AND url = ?";

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, source);
            statement.setString(2, url);

            statement.executeUpdate();
        }
}
    
}