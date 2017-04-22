package test;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.DefaultCaret;

public  class Interface {

		public  JTextField userInput;
		public  JTextArea display;
		public  JLabel jlab;
		public  BufferedWriter bw;
		Voice speaker;
		
		boolean flag = false;
		Interface()
		{
			speaker = new Voice("kevin16");	
			
			//create a new JFrame container
			JFrame mainscreen= new JFrame("A simple chatbot");
			
			mainscreen.setLayout(new FlowLayout());
			
			mainscreen.setSize(600,400);

			mainscreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			mainscreen.setLocationRelativeTo(null);
			
			mainscreen.setVisible(true);
			
			JPanel displayText=new JPanel();
			display=new JTextArea(15,35);
			display.setEditable(false);
			JScrollPane scroll=new JScrollPane(display);
			DefaultCaret caret = (DefaultCaret)display.getCaret();
			caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
			
			displayText.add(scroll);
			mainscreen.add(displayText);
			
			JPanel input=new JPanel();
			JLabel enterText=new JLabel("Enter your Text here : ", JLabel.LEFT);
			userInput=new JTextField(25);
			input.add(enterText);
			input.add(userInput);
			userInput.setText("");
			
			userInput.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					flag = true;
				//	displayText();
					
				}
			});
			
				mainscreen.add(displayText);
				mainscreen.add(input);
		}
		
		public void displayText(String text)
		{					
			display.append("Bot: " + text + "\n");
			speaker.say(text);
			userInput.setText(null);
		}
		
		String displayText() throws IOException, InterruptedException
		{
			String input;
			
			while(flag == false) { Thread.sleep(10); }
			
			
			input = userInput.getText();
			display.append("User: " + input + "\n");
			userInput.setText(null);
			
			flag = false;
			return input;
			
			
		}


public void actionPerformed(ActionEvent arg0) {
	// TODO Auto-generated method stub
	
}
	}



