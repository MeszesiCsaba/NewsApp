/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ro.umfst.newsapp.database;

import ro.umfst.newsapp.News;
import ro.umfst.newsapp.Event;
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
    
    //Events 
    /*
    public static void addEvent(Event event) throws SQLException {

        String sql = "INSERT INTO events ("
                + "title, description, source, author, publisher, "
                + "publish_date, url, creation_date, last_modified_date, "
                + "language, cover_image, event_datetime, location"
                + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, event.getTitle());
            statement.setString(2, event.getDescription());
            statement.setString(3, event.getSource());
            statement.setString(4, event.getAuthor());
            statement.setString(5, event.getPublisher());
            statement.setTimestamp(6, event.getPublishDate());
            statement.setString(7, event.getURL());
            statement.setTimestamp(8, event.getCreationDate());
            statement.setTimestamp(9, event.getLastEdit());
            statement.setString(10, event.getLanguage());
            statement.setString(11, event.getCoverImage());
            statement.setTimestamp(12, event.getEventDate());
            statement.setString(13, event.getLocation());

            statement.executeUpdate();
        }
    }
    public static List<Event> getAllEvents() throws SQLException {
        List<Event> eventList = new ArrayList<>();
        String sql = "SELECT * FROM events";

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {

            while (result.next()) {
                Event event = new Event(
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
                        result.getString("cover_image"),
                        result.getTimestamp("event_datetime"),
                        result.getString("location")
                );

                eventList.add(event);
            }
        }

        return eventList;
    }
    
    public static Event getEvent(String title, Timestamp eventDate, String location) throws SQLException {

        String sql = "SELECT * FROM events "
                + "WHERE title = ? AND event_datetime = ? AND location = ?";

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, title);
            statement.setTimestamp(2, eventDate);
            statement.setString(3, location);

            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return new Event(
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
                            result.getString("cover_image"),
                            result.getTimestamp("event_datetime"),
                            result.getString("location")
                    );
                }
            }
        }

        return null;
    }
    
    public static void updateEvent(Event event, String oldTitle, Timestamp oldEventDate, String oldLocation) throws SQLException {

        String sql = "UPDATE events SET "
                + "title = ?, description = ?, source = ?, author = ?, "
                + "publisher = ?, publish_date = ?, url = ?, "
                + "last_modified_date = ?, language = ?, cover_image = ?, "
                + "event_datetime = ?, location = ? "
                + "WHERE title = ? AND event_datetime = ? AND location = ?";

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, event.getTitle());
            statement.setString(2, event.getDescription());
            statement.setString(3, event.getSource());
            statement.setString(4, event.getAuthor());
            statement.setString(5, event.getPublisher());
            statement.setTimestamp(6, event.getPublishDate());
            statement.setString(7, event.getURL());
            statement.setTimestamp(8, event.getLastEdit());
            statement.setString(9, event.getLanguage());
            statement.setString(10, event.getCoverImage());
            statement.setTimestamp(11, event.getEventDate());
            statement.setString(12, event.getLocation());

            // Find the event using its original primary key.
            statement.setString(13, oldTitle);
            statement.setTimestamp(14, oldEventDate);
            statement.setString(15, oldLocation);

            statement.executeUpdate();
        }
    }
    
    public static void deleteEvent(String title, Timestamp eventDate, String location) throws SQLException {

        String sql = "DELETE FROM events "
                + "WHERE title = ? AND event_datetime = ? AND location = ?";

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, title);
            statement.setTimestamp(2, eventDate);
            statement.setString(3, location);

            statement.executeUpdate();
        }
    }
    */
    /**
     * Deletes all news articles from the database.
     */
    public static void clearNews() throws SQLException {
        String sql = "DELETE FROM news";

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.executeUpdate();
        }
    }

}
