package clueGame;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

@SuppressWarnings("serial")
public class BadConfigFormatException extends Exception{

	public BadConfigFormatException() {
		super("Configure files not found or not readable");
		try {
			PrintWriter out = new PrintWriter("logfile.txt");
			out.println("Configure files not found or not readable");
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public BadConfigFormatException(String message) {
		super(message);
		try {
			PrintWriter out = new PrintWriter("logfile.txt");
			out.println(message);
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
