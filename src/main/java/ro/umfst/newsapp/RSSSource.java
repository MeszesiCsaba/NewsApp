/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ro.umfst.newsapp;

/**
 *
 * @author mcsab
 */
public class RSSSource {
    
    private String name;
    private String url;
    private String language;
    
    public RSSSource(String name, String url, String language){
        this.name = name;
        this.url = url;
        this.language = language;
    }
    
    public String getName(){
        return name;
    }
    
    public String getURL(){
        return url;
    }
    
    public String getLanguage(){
        return language;
    }
    
    @Override 
    public String toString(){
        return name + " - " + url + " (" + language + ")";
    }
}
