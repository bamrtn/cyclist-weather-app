package uk.ac.cam.cl.interaction_design.group1;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class backend {

  public static void testHttp(){
    try{
    URL obj = new URL("http://dataservice.accuweather.com/locations/v1/cities/search?apikey=Le6a2BtymiMzZ2ZB9Sk8g7AWY2AF8ARK&q=Cambridge");
    HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		//add request header

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : ");
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		//print result
		System.out.println(response.toString());
  }catch(Exception e){}
  }

  public static void main(String[] args){
    testHttp();
		System.out.println("Hello world!");
	}
}
