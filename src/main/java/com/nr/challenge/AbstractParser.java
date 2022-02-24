package com.nr.challenge;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AbstractParser {

	protected ArrayBlockingQueue<String> queue;
	private Thread thread;
	final static String regex = "(?:[\\s\\p{Punct}]*(\\b[\\w'’‘]+\\b)[\\s\\p{Punct}]*)";
    final static Pattern pattern = Pattern.compile(regex, Pattern.UNICODE_CHARACTER_CLASS);
	
	public AbstractParser(ArrayBlockingQueue<String> queue) {
		this.queue = queue;
	}
		
	public void run(Runnable target) {
		this.thread = new Thread(target, "ParserThread");
        thread.start();
	}
	
	public boolean isRunning() {
		return thread.getState() != Thread.State.TERMINATED;
	}
	
	//trim unwanted special chars and punctuation
    public List<String> trimUnwantedChars( String stringToTrim) {	
    	
    	List<String> finalString = new ArrayList<>();
    	Matcher matcher = pattern.matcher(stringToTrim);
	    	
	    while(matcher.find()) {
	    	String match = matcher.group(1);
	    	finalString.add(match.toLowerCase());
	    }
	    
    	return finalString;
		
	}
	
    /*It breaks the strings into an array and splits the words into sequences of 3. Once we have sequences of 3 words those are inserted in the queue.
    In the scenario where we did not reach the end of the file yet, we return the 2 last words on a line to concatenate with the next line.*/
	public void wordSequenceInsert(String[] wordsSequence, ArrayBlockingQueue<String> queue) {
		
		if( wordsSequence[0] == null || wordsSequence[1] == null) return;
		

        try {
			queue.put(String.join( " ", wordsSequence));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	//It will read each line of the input (file or SDTIN) parse/trim and insert in a queue
	protected void readLine(BufferedReader bufReader) {
		List<String> trimmedString;
		String inputStr = "";
		String[] words = new String[3];
		
		while (inputStr != null) {		
			try{
				inputStr=bufReader.readLine(); 
				if(inputStr != null) {		
					trimmedString = trimUnwantedChars(inputStr);
					for( String s : trimmedString)
					{
						words[0] = words[1];
						words[1] = words[2];
						words[2] = s;
						wordSequenceInsert(words, queue);
					}
					
				} 
			}catch (Exception e) {
				System.out.println(e.getStackTrace());
			}
		}
	}
}
