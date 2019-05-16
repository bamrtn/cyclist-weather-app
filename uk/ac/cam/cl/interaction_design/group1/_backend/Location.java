package uk.ac.cam.cl.interaction_design.group1._backend;

import java.util.Map;
import java.util.HashMap;
import org.json.*;

public class Location{
  public String name;
  public String countryCode;
  public String locationId;
  public String toString(){
    return name+" "+countryCode+" - "+locationId;
  }
  public Location(JSONObject json){
    name = json.get("name").toString();
    countryCode = json.get("countryCode").toString();
    locationId = json.get("locationId").toString();
  }
  public Location(){
    name = "";
    countryCode = "";
    locationId = "";
  }
  public JSONObject toJson(){
    JSONObject result = new JSONObject();
    result.put("name",name);
    result.put("countryCode",countryCode);
    result.put("locationId",locationId);
    return result;
  }
}
