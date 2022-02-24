 package com.nr.challenge;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main {
	
	public static final AtomicBoolean running = new AtomicBoolean(true);	
	public static final int QUEUE_SIZE = 1000000;
	
	
	public void start(String[] args) {
		
		ArrayBlockingQueue<String> sequenceQueue = new ArrayBlockingQueue<>(QUEUE_SIZE);
		
		startProcessWords(sequenceQueue);
		
		AbstractParser parser; 
		
		if (args.length > 0) {
			System.out.println("The count will be done over the files provided: \n");
			parser = new FileParser(sequenceQueue, args);
			
		}
		else {
			System.out.println("The count will be done over the STDIN input: \n");
			parser = new StdinParser(sequenceQueue);
		}
		
		//monitor threads and queue which are parsing the sequence of words
		while(parser.isRunning() || !sequenceQueue.isEmpty()) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		stop();
		
	}
	
	public static void stop() {
        running.set(false);
    }

	
	
	/*This is starting a thread that will be listening into the queue where the 3 sequence words are being inserted.
	It will check each sequence and add to a hashmap accordingly*/
	private void startProcessWords(ArrayBlockingQueue<String> queue) {
		
		try {
            ProcessWords sequenceOfwords = new ProcessWords(queue);
            sequenceOfwords.start();
        } catch (IOException e) {
            System.out.println("Could not initialize ProcessWords");
            e.printStackTrace();
            System.exit(3);
        }

	}

	public static void main(String[] args) {
		
		new Main().start(args);		
		
	}

}

