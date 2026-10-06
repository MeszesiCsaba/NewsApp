/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ro.umfst.newsapp;
import java.time.LocalDate;
/**
 *
 * @author mcsab
 */
public class News {
    private int id;
    private String title;
    private String description;
    private String source;
    private String author;
    private LocalDate publishdate;
    private String URL;
    private LocalDate creationdate;
    private LocalDate lastedit;
    private String language;
    private String coverimage;
    
    public News(int id,
            String title,
            String description,
            String source,
            String author,
            LocalDate publishdate,
            String URL,
            LocalDate creationdate,
            LocalDate lastedit,
            String language,
            String coverimage){
        setId(id);
        setTitle(title);
        setDescription(description);
        setSource(source);
        setAuthor(author);
        setPublishDate(publishdate);
        setURL(URL);
        setCreationDate(creationdate);
        setLastEdit(lastedit);
        setLanguage(language);
        setCoverImage(coverimage);
    }
    
    //Getterek
    public int getId(){
        return id;
    }
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
    public LocalDate getPublishDate(){
        return publishdate;
    }
    public String getURL(){
        return URL;
    }
    public LocalDate getCreationDate(){
        return creationdate;
    }
    public LocalDate getLastEdit(){
        return lastedit;
    }
    public String getLanguage(){
        return language;
    }
    public String getCoverImage(){
        return coverimage;
    }
    //Setterek
    public void setId(int id){
        if(id <=0){
            throw new IllegalArgumentException("Az ID nagyobb kell legyen 0-nal!");
        }
        this.id = id;
    }
    public void setTitle(String title){
        if(title == null || title.isBlank()){
            throw new IllegalArgumentException("A cim nem lehet ures!");
        }
        this.title = title;
    }
    public void setDescription(String description){
        if(description == null || description.isBlank()){
            throw new IllegalArgumentException("A leiras nem lehet ures!");
        }
        this.description = description;
    }
    public void setSource(String source){
        if(source == null || source.isBlank()){
            throw new IllegalArgumentException("A forras nem lehet ures!");
        }
        this.source = source;
    }
    public void setAuthor(String author){
        if(author == null || author.isBlank()){
            throw new IllegalArgumentException("A szerzo nem lehet ures!");
        }
        this.author = author;
    }
    public void setPublishDate(LocalDate publishdate){
        if(publishdate == null){
            throw new IllegalArgumentException("A kiadasi datum nem lehet ures");
        }
        this.publishdate = publishdate;
    }
    public void setURL(String URL){
        if(URL == null || URL.isBlank()){
            throw new IllegalArgumentException("A URL nem lehet ures");
        }
        this.URL = URL;
    }
    public void setCreationDate(LocalDate creationdate){
        if(creationdate == null){
            throw new IllegalArgumentException("A letrehozasi datum nem lehet ures!");
        }
        this.creationdate = creationdate;
    }
    public void setLastEdit(LocalDate lastedit){
        if(lastedit == null){
            throw new IllegalArgumentException("Az utolso modositasi datum nem lehet ures!");
    }
        this.lastedit = lastedit;
    }
    public void setLanguage(String language){
        if((language.equals("RO")) ||(language.equals("HU")) ){
            this.language = language;
        }else {
        throw new IllegalArgumentException("A nyelv csak RO vagy HU lehet");
        }
    }    
    public void setCoverImage(String coverimage){
        if(coverimage == null || coverimage.isBlank()){
            throw new IllegalArgumentException("A boritokep nem lehet ures");
        }
        this.coverimage = coverimage;
    }
}
