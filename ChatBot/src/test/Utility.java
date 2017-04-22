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
	private static boolean isConnectedToInternet(Interface ui)
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
			ui.displayText("The internet and I arent't talking right now.");
	      return false;
	    }
		return true;
	}
	
	public static void showWeather(String city, Interface ui)
	{
		//check if Internet is working
		if(!Utility.isConnectedToInternet(ui))
			return;
		
		try
		{
			IWeatherDataService dataService = WeatherDataServiceFactory.getWeatherDataService(WeatherDataServiceFactory.service.OPEN_WEATHER_MAP);	
			
			WeatherData data = dataService.getWeatherData(new Location(city, "IN"));
			
			ui.displayText("Current Temperature: " + data.getTemperature().getValue() + " °C"	);
			ui.displayText("Wind Speed: " + data.getWind().getSpeed().getValue() + " m/s");
			ui.displayText(data.getClouds().getValue());
		}
		
		catch (WeatherDataServiceException e)
		{
			System.out.println(e);
		}
	}
	
	public static void showNews(Interface ui)
	{
		if(!Utility.isConnectedToInternet(ui))
			return;
		
		String apiKey = "f1d0b32b5a4c4a86845c7c7ffbb1014c";
		String source = "the-hindu";
		
		String url = "https://newsapi.org/v1/articles?source=" + source + "&sortBy=latest&apiKey=" + apiKey;
		
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
			ui.displayText(titleList.get(i).toString());
			ui.displayText(urlList.get(i).toString());
			
		}
	}
}

