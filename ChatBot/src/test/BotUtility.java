package test;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import java.util.Scanner;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.alicebot.ab.*;

public class BotUtility
{
	private static Scanner in = new Scanner(System.in);
	String botname="test";
	String path="c:/ab"; 
	Bot bot;
	Chat chatSession;
	PrintStream dump, old;
	
	
	private String clean(String question) {
		char[] special = "!@#$%^&*()_,".toCharArray();
		for(int i = 0; i < special.length; i++)
		{
			if(question.indexOf(special[i]) >= 0)
				question = question.replace(special[i], ' ');
		}
	return question;
	}
	
	
	public String Talk(String question, String[] weatherBuzzWords, Interface ui) throws IOException, InterruptedException
	{
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		dump = new PrintStream(outputStream);
		old = System.out;
		
		//redirect library output to the dump stream
		System.setOut(dump);
		
		
		 bot = new Bot(botname, path); 
		 chatSession = new Chat(bot); 	
			
		while(true)
		{
			for(String word : weatherBuzzWords)
			{
				if(question.contains("news"))
				{
					changeOutputToConsole();
					return question;
				}
				
				if(question.contains(word))
				{
					changeOutputToConsole();
					return question;
				}
			}	
			
				
			String response = chatSession.multisentenceRespond(question); 
			
//			Changing print streams
			changeOutputToConsole();
			
			if(response.equals("I have no answer for that."))
				dealWithNoResponse(question, ui);
			else
				ui.displayText(response); 
			
			question = ui.displayText();
			question = clean(question);
			
//			Changing print streams
			System.setOut(dump);
		}	
	}
	
	
	
	private void dealWithNoResponse(String question, Interface ui) throws IOException, InterruptedException
	{
		Random r = new Random();
		int random = r.nextInt(2);
		switch(random)
		{
			case 0: askRandomQuestion(ui);
				break;
			
			case 1: begForAnswer(question, ui);
				break;
		}	
	}
	
	//TODO
	private void askRandomQuestion(Interface ui) throws IOException, InterruptedException
	{
		int numberOfLines = countLines("C:\\ab\\bots\\test\\questions.txt");
		int random = new Random().nextInt(numberOfLines);
		String line = Files.readAllLines(Paths.get("C:\\ab\\bots\\test\\questions.txt")).get(random);
		ui.displayText(line);
		ui.displayText();	
	}
	
	private void begForAnswer(String question, Interface ui) throws IOException, InterruptedException
	{
		ui.displayText("Looks like I dont have an answer for that question");
		ui.displayText("Can you help me out here?");
		String UserResponse = ui.displayText();
		if(UserResponse.contains("yes"))
		{
			ui.displayText("Okay, tell me the answer to that question.");
			String finalAnswer = ui.displayText();
			addToDatabase(question, finalAnswer);	
			ui.displayText("Thanks for letting me know");
		}
		else
			ui.displayText("I shouldn't have asked :/");
	}
	
	private void addToDatabase(String question, String answer)
	{		
		String toBeWritten =  "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "\r\n" + "<aiml>" + "\r\n";		
		BufferedReader br;
		
		try 
		{
		    br = new BufferedReader(new FileReader("C:\\ab\\bots\\test\\aiml\\learn.aiml"));
		    try
		    {
		        String line;
		        while ( (line = br.readLine()) != null )
		        {
		            if((!line.contains("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")) && (!line.contains("<aiml>")) && (!line.contains("</aiml>")))
		            {
		            	toBeWritten += line;
		            	toBeWritten += "\r\n";
		            }
		        }
		    }
		    catch (IOException e) 
		    {
		       System.out.println(e);
		    }
		}
		catch (FileNotFoundException e) 
		{
		    System.out.println(e);		   
		}	
		
		
		toBeWritten += "<category>\r\n\t" + "<pattern>" + question + "</pattern>\r\n\t" + "<template>" + answer + "</template>\r\n" + "</category>" + "\r\n"; 
		toBeWritten += "</aiml>";
	
		try 
		{
			PrintWriter output = new PrintWriter("C:\\ab\\bots\\test\\aiml\\learn.aiml");
			output.println(toBeWritten);
			output.close();
		}	 
		catch (FileNotFoundException e)
		{
			System.out.println(e);			
		}
		
//		Now make a CSV file
		answer = makeCsvCompatible(answer);
		toBeWritten = "0, " + question + ", *, *, " + answer + ", learn.aiml\r\n"; 
		try
		{
		    Files.write(Paths.get("C:\\ab\\bots\\test\\aimlif\\learn.aiml.csv"), toBeWritten.getBytes(), StandardOpenOption.APPEND);
		}
		catch (IOException e) 
		{
		  System.out.println(e);
		}
		
		 System.setOut(dump);
		 bot = new Bot(botname, path); 
		 chatSession = new Chat(bot); 
		 changeOutputToConsole();		 
	}	
	
	private String makeCsvCompatible(String answer)
	{
		if(answer.indexOf(",") >= 0)
			answer = answer.replace(",", "#Comma ");
		if(answer.indexOf(", ") >= 0)
			answer = answer.replace(", ", "#Comma ");
		return answer;
	}
	
	private void changeOutputToConsole() 
	{		
		 System.out.flush();
		 System.setOut(old);
	}
	
	public static int countLines(String filename) throws IOException {
	    InputStream is = new BufferedInputStream(new FileInputStream(filename));
	    try {
	        byte[] c = new byte[1024];
	        int count = 0;
	        int readChars = 0;
	        boolean empty = true;
	        while ((readChars = is.read(c)) != -1) {
	            empty = false;
	            for (int i = 0; i < readChars; ++i) {
	                if (c[i] == '\n') {
	                    ++count;
	                }
	            }
	        }
	        return (count == 0 && !empty) ? 1 : count;
	    } finally {
	        is.close();
	    }
	}
}
