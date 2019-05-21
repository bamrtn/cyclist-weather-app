package uk.ac.cam.cl.interaction_design.group1.backend;

import java.util.Map;
import java.util.HashMap;
import org.json.*;

public class Location{
  public String name;
  public String countryCode;
  public String locationId;
  public String toString(){ //For debug
    return name+" "+countryCode+" - "+locationId;
  }

  //Load location from JSON object
  public Location(JSONObject json){
    name = json.get("name").toString();
    countryCode = json.get("countryCode").toString();
    locationId = json.get("locationId").toString();
  }

  //New, empty location
  public Location(){
    name = "";
    countryCode = "";
    locationId = "";
  }

  //Convert location to JSON object for saving
  public JSONObject toJson(){
    JSONObject result = new JSONObject();
    result.put("name",name);
    result.put("countryCode",countryCode);
    result.put("locationId",locationId);
    return result;
  }
}
