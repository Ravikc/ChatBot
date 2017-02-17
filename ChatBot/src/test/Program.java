package test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;
import org.alicebot.ab.*; 

public class Program
{
	private static Scanner in = new Scanner(System.in);
		
	public static void main(String[] args)	
	{		
		while(true)
		{
			String question = in.nextLine();
			if(question.contains("weather"))			
					Utility.showWeather("Bangalore");	
				
			else if(question.contains("news"))
			{
				Utility.showNews();
			}
			
			else
			{
				ByteArrayOutputStream dump = new ByteArrayOutputStream();
				PrintStream ps = new PrintStream(dump);
				PrintStream old = System.out;
				
				System.setOut(ps);
				
				String botname="test";
				String path="c:/ab"; 
				Bot bot = new Bot(botname, path); 
				Chat chatSession = new Chat(bot); 
			
				String request = question;
				String response = chatSession.multisentenceRespond(request); 
			
				System.out.flush();
				System.setOut(old);
				
				System.out.println(response); 
						
			}			
		}
	}
}