package com.nr.challenge;

import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.stream.Collectors;

public class ProcessWords {
	
	private final ArrayBlockingQueue<String> queue;
	
	// Create HashMap to store word and it's frequency
	private final HashMap<String, Integer> hs = new HashMap<String, Integer>();

	public ProcessWords(ArrayBlockingQueue<String> queue) throws IOException {
        this.queue = queue;   
    }
     
	
	
	public void start() {
        Thread thread = new Thread(this::createMapSequenceOfWords, "ProcessWords");
        thread.start();
    }

	
	// Function returns word with highest frequency
    public void createMapSequenceOfWords()
    {       
        // Iterate through array of words
        try {
        	
        	 while(Main.running.get()) {
        		 String sequenceOfWords =  queue.poll();        		 
        		 // If a sequence of 3 words already exist in HashMap then increase it's count by 1
                 if(sequenceOfWords != null) {
                	 //System.out.println( "Reading this string from the queue " + sequenceOfWords);
                	 if (hs.containsKey(sequenceOfWords)) {          
                         hs.put(sequenceOfWords, hs.get(sequenceOfWords) + 1);
                     }
                     // Otherwise the new sequence to HashMap
                     else {
                         hs.put(sequenceOfWords, 1);     
                     }      
                	 //System.out.printf(" Key %s \t Value %d\n", sequenceOfWords, hs.get(sequenceOfWords));
                 }
        		 
        	 }       	 
           
        } catch (Exception e) {
        	e.printStackTrace();
            System.exit(3);
		}

        findMainSequences();
       
        
    }
    
    //it will go through the Hash Map generated and list the top 100 strings and the amount of times they show up in the text provided
    public void findMainSequences()
    {
    	Map<String,Integer> topSequence =
        	    hs.entrySet().stream()
        	       .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
        	       .limit(100)
        	       .collect(Collectors.toMap(
        	          Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        
        topSequence.entrySet().forEach(entry -> {
        	System.out.println(entry.getKey() + " - " + entry.getValue() + "\n");
        });
        
    }
	
}
