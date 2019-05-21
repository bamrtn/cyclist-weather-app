package uk.ac.cam.cl.interaction_design.group1.backend;

import org.json.*;
import java.util.Map;
import java.util.HashMap;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;

public class Cache{

  //Convert JSON Objects to maps from strings to strings
  private static Map<String, String> toMap(JSONObject object) throws JSONException {
      Map<String, String> map = new HashMap<String, String>();

      Iterator<String> keysItr = object.keys();
      while(keysItr.hasNext()) {
          String key = keysItr.next();
          String value = object.get(key).toString();

          map.put(key, value);
      }
      return map;
  }

  private static Cache cache = null;
  private Map<String,String> cachedData;

  //Load cache from file
  public Cache(){
    cachedData = new HashMap<String,String>();
    try{
      String jsonString = "";
      jsonString = new String ( Files.readAllBytes( Paths.get("cache/cache.json") ) );
      cachedData = toMap(new JSONObject(jsonString));
    }catch(Exception e){}
  }

  public boolean containsKey(String key){
    return cachedData.containsKey(key);
  }

  //Put URL and response into cache and save to file
  public void put(String url, String response){
    cachedData.put(url,response);
    try{
      PrintWriter out = new PrintWriter("cache/cache.json");
      out.println((new JSONObject(cachedData)).toString());
      out.close();
    }catch(Exception e){}
  }

  public String get(String key){
    return cachedData.get(key);
  }

  //Singleton
  public static Cache getCache(){
    if (cache == null) cache = new Cache();
    return cache;
  }
}
