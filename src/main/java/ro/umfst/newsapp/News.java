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
public class News {
    private String title;
    private String description;
    private String source;
    private String author;
    private String publisher;
    private Timestamp publishdate;
    private String url;
    private Timestamp creationdate;
    private Timestamp lastedit;
    private String language;
    private String coverimage;
    
    public News(
            String title,
            String description,
            String source,
            String author,
            String publisher,
            Timestamp publishdate,
            String url,
            Timestamp creationdate,
            Timestamp lastedit,
            String language,
            String coverimage){
        setTitle(title);
        setDescription(description);
        setSource(source);
        setAuthor(author);
        setPublisher(publisher);
        setPublishDate(publishdate);
        setURL(url);
        setCreationDate(creationdate);
        setLastEdit(lastedit);
        setLanguage(language);
        setCoverImage(coverimage);
    }
    
    //Getterek

    public String getTitle(){
        return title;
    }
    public String getDescription(){
        return description;
    }
    public String getSource(){
        return source;
    }
    public String getAuthor(){
        return author;
    }
    public String getPublisher(){
        return publisher;
    }
    public Timestamp getPublishDate(){
        return publishdate;
    }
    public String getURL(){
        return url;
    }
    public Timestamp getCreationDate(){
        return creationdate;
    }
    public Timestamp getLastEdit(){
        return lastedit;
    }
    public String getLanguage(){
        return language;
    }
    public String getCoverImage(){
        return coverimage;
    }
    //Setterek
    
    public void setTitle(String title){
        if(title == null || title.isBlank()){
            throw new IllegalArgumentException("A cim nem lehet ures!");
        }
        this.title = title;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public void setSource(String source){
        if(source == null || source.isBlank()){
            throw new IllegalArgumentException("A forras nem lehet ures!");
        }
        this.source = source;
    }
    public void setAuthor(String author){
        this.author = author;
    }
    public void setPublisher(String publisher){
        this.publisher = publisher;
    }
    public void setPublishDate(Timestamp publishdate){
        this.publishdate = publishdate;
    }
    public void setURL(String url){
        if(url == null || url.isBlank()){
            throw new IllegalArgumentException("A URL nem lehet ures");
        }
        this.url = url;
    }
    public void setCreationDate(Timestamp creationdate){
        if(creationdate == null){
            throw new IllegalArgumentException("A letrehozasi datum nem lehet ures!");
        }
        this.creationdate = creationdate;
    }
    public void setLastEdit(Timestamp lastedit){
        if(lastedit == null){
            throw new IllegalArgumentException("Az utolso modositasi datum nem lehet ures!");
    }
        this.lastedit = lastedit;
    }
    public void setLanguage(String language){
        if("RO".equals(language) || "HU".equals(language)){
            this.language = language;
        }else{
            throw new IllegalArgumentException("A nyelv csak RO vagy HU lehet.");
        }
    }
    public void setCoverImage(String coverimage){
        if(coverimage == null || coverimage.isBlank()){
            this.coverimage = "res/images/default.jpg";
        }else{
            this.coverimage = coverimage;    
        }
        
    }
}
