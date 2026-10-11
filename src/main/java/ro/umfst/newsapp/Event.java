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
public class Event extends News{
    private Timestamp eventdate;
    private String location;
    public Event(
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
            String coverimage,
            Timestamp eventdate,
            String location){
        super( 
             title,
            description, 
             source, 
             author,
             publisher,
             publishdate,
             url,
             creationdate,
             lastedit,
             language,
             coverimage);
        setEventDate(eventdate);
        setLocation(location);
    }
    public Timestamp getEventDate(){
        return eventdate;
}
    public String getLocation(){
        return location;
    }
    
    public void setEventDate(Timestamp eventdate){
        if(eventdate == null){
            throw new IllegalArgumentException("Az esemeny idopontja nem lehet nulla");
        }
        
        this.eventdate = eventdate;
    }
    public void setLocation(String location){
        if(location == null || location.isBlank()){
            throw new IllegalArgumentException("A hely nem lehet ures.");
        }
        
        this.location = location;
    }
}
