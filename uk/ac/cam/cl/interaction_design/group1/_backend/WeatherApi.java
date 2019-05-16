package uk.ac.cam.cl.interaction_design.group1._backend;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.util.Calendar;

import org.json.*;

public class WeatherApi {

  private static JSONObject get(JSONObject o,String s){
    return (JSONObject) o.get(s);
  }

  private static String getString(JSONObject o,String s){
    return o.get(s).toString();
  }

  public static List<Location> searchLocation(String name){
    List<Location> result = new ArrayList<Location>();
    String responseString = Request.getRequest(
      "http://dataservice.accuweather.com/locations/v1/cities/search",
      new HashMap<String,String>() {{
        put("q",name);
      }}
    );
    JSONArray response = new JSONArray(responseString);
    for (Object object : response){
      JSONObject obj = (JSONObject) object;
      Location location = new Location();
      location.name = getString(obj,"EnglishName");
      location.countryCode = getString(get(obj,"Country"),"ID");
      location.locationId = getString(obj,"Key");
      result.add(location);
    }
    return result;
  }

  public static Weather getWeatherForDay(int day){
    String responseString = Request.getRequest(
      "http://dataservice.accuweather.com/forecasts/v1/daily/5day/"+LocationState.getCurrentLocation().locationId,
      new HashMap<String,String>() {{
        put("details","true");
        put("metric","true");
      }}
    );
    JSONObject forecast = (JSONObject) ((JSONArray) (new JSONObject(responseString)).get("DailyForecasts")).get(day);
    Weather result = new Weather();

    result.temperature = (int) ((Double.parseDouble(
        getString(get(get(forecast,"Temperature"),"Minimum"),"Value")
      ) + Double.parseDouble(
        getString(get(get(forecast,"Temperature"),"Maximum"),"Value")
      ))/2.0);

    result.windspeed = (int) Double.parseDouble(getString(get(get(get(forecast,"Day"),"Wind"),"Speed"),"Value"));

    int icon = Integer.parseInt(getString(get(forecast,"Day"),"Icon"));
    if (icon < 8 || (icon > 18 && icon <24)) result.rainLikelihood = Weather.RainEnum.UNLIKELY; else
    if (icon < 15) result.rainLikelihood = Weather.RainEnum.LIGHT_SHOWERS; else
    if (icon == 18) result.rainLikelihood = Weather.RainEnum.HEAVY_SHOWERS; else
                  result.rainLikelihood = Weather.RainEnum.THUNDER;

    if ( Double.parseDouble(getString(get(get(forecast,"Day"),"Rain"),"Value")) > 0.0 )
      result.alerts.add(new Weather.Alert("Rain expected!","Bring a coat!"));
    if ( Double.parseDouble(getString(get(get(forecast,"Day"),"Snow"),"Value")) > 0.0 )
      result.alerts.add(new Weather.Alert("Snow expected!","Cycle with caution!"));
    if ( Double.parseDouble(getString(get(get(forecast,"Day"),"Ice"),"Value")) > 0.0 )
      result.alerts.add(new Weather.Alert("Ice expected!","Please don't cycle!"));

    result.humidity = (int) (Double.parseDouble(getString(get(forecast,"Day"),"HoursOfPrecipitation")) / 0.12);

    result.sunrise = getString(get(forecast,"Sun"),"Rise").substring(11,16);
    result.sunset = getString(get(forecast,"Sun"),"Set").substring(11,16);

    return result;
  }

  public static Weather.GraphData getGraphData(int day){
    Weather w = getWeatherForDay(day);
    Random rand = new Random(w.temperature+w.windspeed+w.humidity);
    Weather.GraphData result = new Weather.GraphData();

    for (int i = 0; i<24; i++) result.temperature.add(w.temperature-5+rand.nextInt(10));
    for (int i = 0; i<24; i++) result.windspeed.add(w.windspeed-7+rand.nextInt(14));

    return result;
  }

  public static String getDate(int day){
    String[] monthName = {"January", "February",
                "March", "April", "May", "June", "July",
                "August", "September", "October", "November",
                "December"};

    Calendar cal = Calendar.getInstance();
    cal.add(Calendar.DATE, day);
    return monthName[cal.get(Calendar.MONTH)] + " " + cal.get(Calendar.DATE);
  }

}
