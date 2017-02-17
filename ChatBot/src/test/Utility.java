package test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import il.ac.hit.finalproject.classes.IWeatherDataService;
import il.ac.hit.finalproject.classes.Location;
import il.ac.hit.finalproject.classes.WeatherData;
import il.ac.hit.finalproject.classes.WeatherDataServiceFactory;
import il.ac.hit.finalproject.exceptions.WeatherDataServiceException;

public class Utility 
{
	private static boolean isConnectedToInternet()
	{
		try 
		{
	        final URL url = new URL("http://www.google.com");
	        final URLConnection conn = url.openConnection();
	        conn.connect();
	       
	    } catch (MalformedURLException e)
		{
	        throw new RuntimeException(e);
	    } 
		catch (IOException e)
		{
	      System.out.println("The internet and I arent't talking right now.");
	      return false;
	    }
		return true;
	}
	
	public static void showWeather(String city)
	{
		//check if Internet is working
		if(!Utility.isConnectedToInternet())
			return;
		
		try
		{
			IWeatherDataService dataService = WeatherDataServiceFactory.getWeatherDataService(WeatherDataServiceFactory.service.OPEN_WEATHER_MAP);	
			
			WeatherData data = dataService.getWeatherData(new Location(city, "IN"));
						
			System.out.println("Current Temperature: " + data.getTemperature().getValue() + " °C"	);
			System.out.println("Wind Speed: " + data.getWind().getSpeed().getValue() + " m/s");
			System.out.println(data.getClouds().getValue());
		}
		
		catch (WeatherDataServiceException e)
		{
			System.out.println(e);
		}
	}
	
	public static void showNews()
	{
		if(!Utility.isConnectedToInternet())
			return;
		
		String ApiKey = "f1d0b32b5a4c4a86845c7c7ffbb1014c";
		
		String url = "https://newsapi.org/v1/articles?source=the-hindu&sortBy=latest&apiKey=" + ApiKey;
		
		List<String> titleList = new ArrayList<String>();
		List<String> urlList = new ArrayList<String>();
		try
		{
			JSONObject json = JsonReader.readJsonFromUrl(url);
			JSONArray array = json.getJSONArray("articles");
			for(int i = 0; i < array.length(); i++)
			{
				titleList.add(array.getJSONObject(i).getString("title"));
				urlList.add(array.getJSONObject(i).getString("url"));
			}
		}catch(Exception e)
		{
			System.out.println(e.toString());
		}
		
		for(int i = 0; i < titleList.size(); i++)
		{
			System.out.println(titleList.get(i).toString());
			System.out.println(urlList.get(i).toString());
			System.out.println();
		}
	}
}
