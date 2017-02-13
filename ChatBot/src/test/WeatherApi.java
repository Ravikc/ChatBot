package test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import il.ac.hit.finalproject.classes.IWeatherDataService;
import il.ac.hit.finalproject.classes.Location;
import il.ac.hit.finalproject.classes.WeatherData;
import il.ac.hit.finalproject.classes.WeatherDataServiceFactory;
import il.ac.hit.finalproject.exceptions.WeatherDataServiceException;

public class WeatherApi
{
	public static void showWeather(String city)
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
	      return;
	    }		
		
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
	
}
