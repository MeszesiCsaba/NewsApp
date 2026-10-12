/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ro.umfst.newsapp.rss;

import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 *
 * @author mcsab
 */
public class RSSHttpClient {
    
    public static int getStatusCode(String urlAddress) {

        HttpURLConnection connection = null;

        try {
            URL url = new URL(urlAddress);

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.setInstanceFollowRedirects(false);

            int statusCode = connection.getResponseCode();

            return statusCode;

        } catch (Exception e) {
            System.out.println("HTTP request failed: " + e.getMessage());
            return -1;

        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
    
    
    /**
     * Downloads the content of an RSS feed.
     */
    public static String getContent(String urlAddress) throws Exception {

        URL url = new URL(urlAddress);
        HttpURLConnection connection =
                (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        connection.setInstanceFollowRedirects(true);

        try {
            int statusCode = connection.getResponseCode();

            if (statusCode != 200) {
                throw new Exception("HTTP error: " + statusCode);
            }

            StringBuilder content = new StringBuilder();

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            connection.getInputStream(),
                            StandardCharsets.UTF_8))) {

                String line;

                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }
            }

            return content.toString();

        } finally {
            connection.disconnect();
        }
    }

}
