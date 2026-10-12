/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ro.umfst.newsapp;
import java.sql.Timestamp;


/**
 *
 * @author mcsab
 */
public abstract class ContentItem {
    private String title;
    private String description;
    private String author;
    private String publisher;
    private Timestamp publishdate;
    private Timestamp creationdate;
    private Timestamp lastedit;
    private String language;
    private String coverimage;

    public ContentItem(
            String title,
            String description,
            String author,
            String publisher,
            Timestamp publishdate,
            Timestamp creationdate,
            Timestamp lastedit,
            String language,
            String coverimage) {

        setTitle(title);
        setDescription(description);
        setAuthor(author);
        setPublisher(publisher);
        setPublishDate(publishdate);
        setCreationDate(creationdate);
        setLastEdit(lastedit);
        setLanguage(language);
        setCoverImage(coverimage);
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getAuthor() {
        return author;
    }

    public String getPublisher() {
        return publisher;
    }

    public Timestamp getPublishDate() {
        return publishdate;
    }

    public Timestamp getCreationDate() {
        return creationdate;
    }

    public Timestamp getLastEdit() {
        return lastedit;
    }

    public String getLanguage() {
        return language;
    }

    public String getCoverImage() {
        return coverimage;
    }

    public void setTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException(
                    "The title cannot be empty!");
        }
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setPublishDate(Timestamp publishdate) {
        this.publishdate = publishdate;
    }

    public void setCreationDate(Timestamp creationdate) {
        if (creationdate == null) {
            throw new IllegalArgumentException(
                    "The creation date cannot be null!");
        }
        this.creationdate = creationdate;
    }

    public void setLastEdit(Timestamp lastedit) {
        if (lastedit == null) {
            throw new IllegalArgumentException(
                    "The last modification date cannot be null!");
        }
        this.lastedit = lastedit;
    }

    public void setLanguage(String language) {
        if ("RO".equals(language) || "HU".equals(language)) {
            this.language = language;
        } else {
            throw new IllegalArgumentException(
                    "Language must be RO or HU.");
        }
    }

    public void setCoverImage(String coverimage) {
        if (coverimage == null || coverimage.isBlank()) {
            this.coverimage = "res/images/default.jpg";
        } else {
            this.coverimage = coverimage;
        }
    }
}
