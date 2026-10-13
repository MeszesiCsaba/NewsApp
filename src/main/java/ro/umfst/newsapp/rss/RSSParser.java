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
        factory.setNamespaceAware(true);
        
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
        
        
        String author = null;
        
        NodeList authors = item.getElementsByTagNameNS("*", "author");

        if (authors.getLength() > 0) {
            author = authors.item(0).getTextContent().trim();
        }

        // If author is missing, try creator, including dc:creator.
        if (author == null || author.isBlank()) {
            NodeList creators = item.getElementsByTagNameNS("*", "creator");

            if (creators.getLength() > 0) {
                author = creators.item(0).getTextContent().trim();
            }
        }

        // Keep null if no author was found.
        if (author != null && author.isBlank()) {
            author = null;
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
        String coverImage = getImageUrl(item, url);
        
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
                coverImage
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

         value = value.trim();

        // Remove HTML tags from the text.
        value = value.replaceAll("<[^>]*>", " ");

        // Replace common HTML entities with readable characters.
        value = value.replace("&nbsp;", " ");
        value = value.replace("&#160;", " ");
        value = value.replace("&amp;", "&");
        value = value.replace("&quot;", "\"");
        value = value.replace("&#39;", "'");
        value = value.replace("&lt;", "<");
        value = value.replace("&gt;", ">");
        value = value.replace("&#8230;", "…");
        value = value.replace("&#8217;", "’");
        value = value.replace("&#8211;", "–");
        value = value.replace("&#8212;", "—");
        value = value.replace("&apos;", "'");

        // Remove unnecessary spaces and line breaks.
        value = value.replaceAll("\\s+", " ").trim();

        return value.isEmpty() ? null : value;
        }
    
    private static String getImageUrl(Element item, String articleUrl) {
        String imageUrl = null;

        // 1. Try media:content and media:thumbnail.
        String[] imageTags = {"content", "thumbnail"};

        for (String tag : imageTags) {
            NodeList nodes = item.getElementsByTagNameNS("*", tag);

            for (int i = 0; i < nodes.getLength(); i++) {
                Element element = (Element) nodes.item(i);
                String candidate = element.getAttribute("url");

                if (candidate != null && !candidate.isBlank()) {
                    imageUrl = candidate.trim();
                    break;
                }
            }

            if (imageUrl != null) {
                break;
            }
        }

        // 2. Try enclosure, commonly used for media attachments.
        if (imageUrl == null) {
            NodeList enclosures =
                    item.getElementsByTagName("enclosure");

            for (int i = 0; i < enclosures.getLength(); i++) {
                Element enclosure = (Element) enclosures.item(i);
                String type = enclosure.getAttribute("type");
                String candidate = enclosure.getAttribute("url");

                if (candidate != null && !candidate.isBlank()
                        && type.toLowerCase().startsWith("image/")) {
                    imageUrl = candidate.trim();
                    break;
                }
            }
        }

        // 3. Resolve relative image URLs, if necessary.
        if (imageUrl != null) {
            try {
                return new java.net.URL(
                        new java.net.URL(articleUrl), imageUrl
                ).toString();
            } catch (Exception ex) {
                return imageUrl;
            }
        }

        return null;
    }

    }
