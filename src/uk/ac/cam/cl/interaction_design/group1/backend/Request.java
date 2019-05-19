package uk.ac.cam.cl.interaction_design.group1.backend;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class Request {

  private static final String ApiKey = "?apikey=Le6a2BtymiMzZ2ZB9Sk8g7AWY2AF8ARK";

  private static String getUrl(String url){
    Cache cache = Cache.getCache();
    if (cache.containsKey(url)){
      //System.out.println("Cached!");
      return cache.get(url);
    }

    String response = "";
              if (true) return ""; // COMMENT THIS OUT FOR LIVE REQESTS
    try{
      URL obj = new URL(url);
      HttpURLConnection con = (HttpURLConnection) obj.openConnection();
  		con.setRequestMethod("GET");
  		int responseCode = con.getResponseCode();

  		BufferedReader in = new BufferedReader(
  		        new InputStreamReader(con.getInputStream()));
  		String inputLine;
  		while ((inputLine = in.readLine()) != null) {
  			response += inputLine;
  		}
  		in.close();

    }catch(Exception e){}

    cache.put(url,response);
    return response;
  }

  public static String getRequest(String url, Map<String,String> params){
    String suffix = "";
    for (Map.Entry<String, String> param : params.entrySet()){
      suffix += "&"+param.getKey()+"="+param.getValue();
    }
    return getUrl(url+ApiKey+suffix);
  }
}
