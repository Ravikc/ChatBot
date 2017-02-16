package test;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.json.JSONObject;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.json.JSONArray;
import org.json.JSONException;
import org.alicebot.ab.*; 

public class Program
{
	private static Scanner in = new Scanner(System.in);
	private static String url = "https://newsapi.org/v1/articles?source=the-hindu&sortBy=latest&apiKey=f1d0b32b5a4c4a86845c7c7ffbb1014c";
	private static SpellCheck sc = null;
	public static void main(String[] args) throws IOException, JSONException	
	{		
		while(true)
		{
			String question = in.nextLine();
			if(question.contains("weather"))			
					WeatherApi.showWeather("Bangalore");	
				
			else if(question.contains("news"))
			{
				List<String> titleList = new ArrayList<String>();
				List<String> urlList = new ArrayList<String>();
				
				JSONObject json = JsonReader.readJsonFromUrl(url);
				JSONArray array = json.getJSONArray("articles");
				for(int i = 0; i < array.length(); i++)
				{
					titleList.add(array.getJSONObject(i).getString("title"));
					urlList.add(array.getJSONObject(i).getString("url"));
				}
				for(int i = 0; i < titleList.size(); i++)
				{
					System.out.println(titleList.get(i).toString());
					System.out.println(urlList.get(i).toString());
					System.out.println();
				}
			}
			
			else
			{
				PrintStream myFile= new PrintStream("dummy.txt");
				
				
				System.setOut(myFile);
				
				String botname="super";
				String path="c:/ab"; 
				Bot bot = new Bot(botname, path); 
				Chat chatSession = new Chat(bot); 
			
				String request = question;
				String response = chatSession.multisentenceRespond(request); 
				
				System.setOut(System.out);
				System.out.println(response); 
						
			}
				
			
		}
	}
}