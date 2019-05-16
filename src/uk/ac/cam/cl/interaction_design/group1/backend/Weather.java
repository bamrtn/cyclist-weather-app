package uk.ac.cam.cl.interaction_design.group1.backend;

import java.util.List;
import java.util.ArrayList;
import org.json.*;

public class Weather{

  public static enum RainEnum{
    UNLIKELY, LIGHT_SHOWERS, HEAVY_SHOWERS, THUNDER;
  }

  public static class Alert{
    public String name;
    public String detail;
    public Alert(String n, String d){
      name = n; detail = d;
    }
    public String toString(){
      return "Alert: " + name + " - " + detail;
    }
  }

  public static class GraphData{
    public List<Integer> temperature;
    public List<Integer> windspeed;

    public GraphData(){
      temperature = new ArrayList<Integer>();
      windspeed = new ArrayList<Integer>();
    }

    public String toString(){
      return temperature.toString() +"\n"+ windspeed.toString();
    }
  }

  public int temperature;
  public int windspeed;
  public RainEnum rainLikelihood;
  public List<Alert> alerts;
  public int humidity;
  public String sunrise;
  public String sunset;

  public Weather(){
    alerts = new ArrayList<Alert>();
  }

  public String toString(){
    return
    "Temperature: "+temperature+
    "\n Windspeed: "+windspeed+
    "\n Rain likelihood: "+rainLikelihood+
    "\n Alerts: \n"+alerts.toString()+
    "\n Humidity: "+humidity+
    "\n Sunrise: "+sunrise+
    "\n Sunset: "+sunset;
  }
}
