package uk.ac.cam.cl.interaction_design.group1.backend;

import org.json.*;
import java.util.Map;
import java.util.HashMap;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

public class LocationState{

  private static LocationState locationState = null;
  private List<Location> savedLocations;
  private Location currentLocation;

  public LocationState(){
    savedLocations = new ArrayList<Location>();
    currentLocation = new Location();
    try{
      String locationsString = new String ( Files.readAllBytes( Paths.get("cache/savedLocations.json") ) );
      for (Object location : new JSONArray(locationsString)){
        savedLocations.add(new Location((JSONObject) location));
      }
      String locationString = new String ( Files.readAllBytes( Paths.get("cache/currentLocation.json") ) );
      currentLocation = new Location(new JSONObject(locationString));
    }catch(Exception e){}
  }

  public static List<Location> getSavedLocations(){
    LocationState ls = getLocationState();
    return ls.savedLocations;
  }

  public static Location getCurrentLocation(){
    LocationState ls = getLocationState();
    return ls.currentLocation;
  }

  public static void saveLocation(Location l){
    LocationState ls = getLocationState();
    ls.savedLocations.add(l);

    JSONArray toSave = new JSONArray();
    for(Location i : ls.savedLocations){
      toSave.put(i.toJson());
    }
    try{
      PrintWriter out = new PrintWriter("cache/savedLocations.json");
      out.println(toSave.toString());
      out.close();
    }catch(Exception e){}
  }

  public static void removeSavedLocation(Location l){
    LocationState ls = getLocationState();
    ls.savedLocations.remove(l);

    JSONArray toSave = new JSONArray();
    for(Location i : ls.savedLocations){
      toSave.put(i.toJson());
    }
    try{
      PrintWriter out = new PrintWriter("cache/savedLocations.json");
      out.println(toSave.toString());
      out.close();
    }catch(Exception e){}
  }

  public static void setLocation(Location l){
    LocationState ls = getLocationState();
    ls.currentLocation = l;
    try{
      PrintWriter out = new PrintWriter("cache/currentLocation.json");
      out.println(l.toJson().toString());
      out.close();
    }catch(Exception e){}
  }

  public static LocationState getLocationState(){
    if (locationState == null) locationState = new LocationState();
    return locationState;
  }
}
