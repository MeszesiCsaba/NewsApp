/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ro.umfst.newsapp;


import com.google.gson.Gson;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mcsab
 */
public class RSSConfigReader {
    private static class RSSConfig{
        private List<RSSSource> rssSources;
    }
    
    public static List<RSSSource> readSources() throws IOException{
        
        Gson gson = new Gson();
        
        try(FileReader reader = new FileReader("res/rss.json")){
            RSSConfig config = gson.fromJson(reader, RSSConfig.class);
            
            if(config == null || config.rssSources == null){
                return new ArrayList<>();
            }
            
            return config.rssSources;
        }
    }
}
