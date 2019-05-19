package uk.ac.cam.cl.interaction_design.group1.testing;
import uk.ac.cam.cl.interaction_design.group1.backend.*;

import java.util.Map;
import java.util.List;
import java.util.HashMap;

public class backendTest {

  public static void main(String[] args){

    System.out.println("Searching for text 'Cambridge': ");
    List<Location> search = WeatherApi.searchLocation("Cambridge");
    System.out.println(search);

    //Save the second result
    LocationState.saveLocation(search.get(0));

    //Set the first result as current location
    LocationState.setLocation(search.get(0));


    //Print saved locationState
    System.out.println(LocationState.getSavedLocations());

    //Create a location for Cambridge
    Location cam = new Location();
    cam.name = "Cambridge";
    cam.countryCode = "GB";
    cam.locationId = "2530522";

    //Print weather, graph data and date for 4 days from now
    System.out.println(WeatherApi.getWeatherForDay(4));
    System.out.println(WeatherApi.getGraphData(4));
    System.out.println(WeatherApi.getDate(4));
	}
}
