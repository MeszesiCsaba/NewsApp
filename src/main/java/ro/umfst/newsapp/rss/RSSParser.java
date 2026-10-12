/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ro.umfst.newsapp.rss;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Node;
import ro.umfst.newsapp.News;
/**
 *
 * @author mcsab
 */
public class RSSParser {
    
    
    public static String getLanguage(String xmlContent) throws Exception {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        // Prevent XML external entity processing.
        factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl",true);

        DocumentBuilder builder = factory.newDocumentBuilder();

        Document document = builder.parse(new ByteArrayInputStream(xmlContent.getBytes(StandardCharsets.UTF_8)));

        NodeList languages = document.getElementsByTagName("language");

        if (languages.getLength() > 0) {
            String language = languages.item(0).getTextContent().trim();

            if (language.toLowerCase().startsWith("ro")) {
                return "RO";
            }

            if (language.toLowerCase().startsWith("hu")) {
                return "HU";
            }
        }
        return null;
    }
    public static List<News> parseNews(String xmlContent, String source, String language) throws Exception {
        List<News> newsList = new ArrayList<>();
        
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    factory.setFeature(
            "http://apache.org/xml/features/disallow-doctype-decl",
            true
    );

    DocumentBuilder builder = factory.newDocumentBuilder();

    Document document = builder.parse(
            new ByteArrayInputStream(
                    xmlContent.getBytes(StandardCharsets.UTF_8)
            )
    );

    NodeList items = document.getElementsByTagName("item");

    for (int i = 0; i < items.getLength(); i++) {

        Element item = (Element) items.item(i);

        String title = getElementText(item, "title");
        String description = getElementText(item, "description");
        String url = getElementText(item, "link");
        String author = getElementText(item, "author");

        if (author == null || author.isBlank()) {
            author = getElementText(item, "creator");
        }

        String dateText = getElementText(item, "pubDate");
        Timestamp publishDate = null;

        if (dateText != null && !dateText.isBlank()) {
            try {
                publishDate = Timestamp.from(
                        java.time.ZonedDateTime.parse(
                                dateText,
                                java.time.format.DateTimeFormatter.RFC_1123_DATE_TIME
                        ).toInstant()
                );
            } catch (Exception e) {
                // Keep the publication date empty if its format is unsupported.
            }
        }

        if (title == null || title.isBlank()
                || url == null || url.isBlank()) {
            continue;
        }

        Timestamp now = new Timestamp(System.currentTimeMillis());

        News news = new News(
                title,
                description,
                source,
                author,
                source,
                publishDate,
                url,
                now,
                now,
                language,
                null
        );

        newsList.add(news);
    }

    return newsList;
}

    /**
     * Returns the text of an XML element, or null if it is missing.
     */
    private static String getElementText(Element parent, String tagName) {

        NodeList nodes = parent.getElementsByTagName(tagName);

        if (nodes.getLength() == 0) {
            return null;
        }

        String value = nodes.item(0).getTextContent();

        if (value == null || value.isBlank()) {
            return null;
        }

        return value.trim();
        }
    }
