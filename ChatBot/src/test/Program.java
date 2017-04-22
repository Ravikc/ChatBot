package test;
import java.io.IOException;
import java.util.Scanner; 

public class Program
{
	private static Scanner in = new Scanner(System.in);
		
	public static void main(String[] args) throws InterruptedException, IOException	
	{
		String[] weatherBuzzWords = {"weather", "cloud", "cloudy", "sun", "sunny", "wind", "windy", "cold", "hot", "freezing", "snow", "rain", "raining", "rainy", "drizzle", "fog", "foggy", "temperature"};
		Interface ui = new Interface();
		String question = "";
		while(true)
		{
			if(question == "")
				question = ui.displayText();
							
			question = question.trim().toLowerCase();
			
			for(String word : weatherBuzzWords)
				if(question.contains(word))
				{		
					Thread.sleep(10);
					ui.displayText("Let me check\n");
					Thread.sleep(1);
					Utility.showWeather("Bangalore", ui);
					question = "";
					break;
				}				
				else if(question.contains("news"))
				{
					Thread.sleep(10);
					ui.displayText("Let me check\n");
					Thread.sleep(1);
					Utility.showNews(ui);
					question = "";
					break;
				}
			
			else
			{
				BotUtility bot = new BotUtility();
				question = bot.Talk(question, weatherBuzzWords, ui);
			}			
		}
	}
}