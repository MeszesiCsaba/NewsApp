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
public class News extends ContentItem{
    
    private String source;
    private String url;
    
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
            super(
            title,
            description,
            author,
            publisher,
            publishdate,
            creationdate,
            lastedit,
            language,
            coverimage);
            
            setSource(source);
            setURL(url);
            }
    
    
    //Getterek

    public String getSource(){
        return source;
    }
    public String getURL(){
        return url;
    }
   
    
    //Setterek
    
    
    public void setSource(String source){
        if(source == null || source.isBlank()){
            throw new IllegalArgumentException("A forras nem lehet ures!");
        }
        this.source = source;
    }
   
    public void setURL(String url){
        if(url == null || url.isBlank()){
            throw new IllegalArgumentException("A URL nem lehet ures");
        }
        this.url = url;
    }
    
    
}
