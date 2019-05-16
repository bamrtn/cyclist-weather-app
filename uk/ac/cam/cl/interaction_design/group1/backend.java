package uk.ac.cam.cl.interaction_design.group1;
import uk.ac.cam.cl.interaction_design.group1._backend.*;

import java.util.Map;
import java.util.HashMap;

public class backend {

  public static void main(String[] args){
    //System.out.println(WeatherApi.searchLocation("Cambridge"));
		//System.out.println("Hello world!");

    Location cam = new Location();
    cam.name = "Cambridge";
    cam.countryCode = "GB";
    cam.locationId = "2530522";

    //System.out.println(LocationState.getSavedLocations());

    //LocationState.saveLocation(cam);
    //System.out.println(LocationState.getCurrentLocation());
    LocationState.setLocation(cam);

    System.out.println(WeatherApi.getWeatherForDay(4));
    System.out.println(WeatherApi.getGraphData(4));
    System.out.println(WeatherApi.getDate(4));
	}
}
