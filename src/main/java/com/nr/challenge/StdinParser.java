package com.nr.challenge;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ArrayBlockingQueue;

public class StdinParser extends AbstractParser {

    public StdinParser(ArrayBlockingQueue<String> queue) {
		super(queue);
		super.run(this::startStdinParser);
	}

	// This method will read inputs from STDIN, will parse the data and insert every sequence of 3 words into a queue
	public void startStdinParser() {
		
		InputStreamReader isReader = new InputStreamReader(System.in);
		BufferedReader bufReader = new BufferedReader(isReader);
	
		try {
			readLine(bufReader);
			bufReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}

	
}


