package com.nr.challenge;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.concurrent.ArrayBlockingQueue;

public class FileParser extends AbstractParser {
	
	private final String[] args;

	public FileParser(ArrayBlockingQueue<String> queue, String[] args) {
		super(queue);
		this.args = args;
		super.run(this::runFileParser);
	}
	

	// This method will read inputs from the files provided by the user, will parse the data and insert every sequence of 3 words into a queue
	public void runFileParser() {				
		for(int i=0; i < args.length; i++ ) {			
			processFiles(args[i].toString());				
		}
		
	}
	
	// This method will read inputs from the files provided by the user, will parse the data and insert every sequence of 3 words into a queue
	public void processFiles(String filename) {		
		try {
			File file = new File(filename);			
			if(file.exists()) {			
				BufferedReader bufReader = new BufferedReader(new FileReader(file));			
				readLine(bufReader); 				
			    bufReader.close();
			} else {
				System.out.printf("File %s does not exist, please provide a valid file", filename);
			}
			
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
		}	
	}
	

}
