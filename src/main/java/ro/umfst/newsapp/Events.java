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
public class Events extends News{
    private LocalDate eventdate;
    private String location;
    public Events(int id,
            String title, 
            String description, 
            String source, 
            String author, 
            LocalDate publishdate, 
            String URL, 
            LocalDate creationdate,
            LocalDate lastedit,
            String language,
            String coverimage,
            LocalDate eventdate,
            String location){
        super(id, 
             title,
            description, 
             source, 
             author,
             publishdate,
             URL,
             creationdate,
             lastedit,
             language,
             coverimage);
        setEventDate(eventdate);
        setLocation(location);
    }
    public LocalDate getEventDate(){
        return eventdate;
}
    public String getLocation(){
        return location;
    }
    
    public void setEventDate(LocalDate eventdate){
        this.eventdate = eventdate;
    }
    public void setLocation(String location){
        this.location = location;
    }
}
