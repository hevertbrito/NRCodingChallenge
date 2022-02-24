package com.nr.challenge;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;

import org.mockito.Mock;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

public class ParserTest {

	@Test
	public void TestFileParserInvalidFile() {
		
		final String[] files = new String[1];
		
		files[0] =  "./incorrectfile.txt";		
		
		ArrayBlockingQueue<String> q = new ArrayBlockingQueue<>(100);
		
		FileParser parser = new FileParser(q, files);
		
		//validate that the queue is still empty when there is an invalid file being provided
		assertTrue(q.isEmpty());
		
	}
	
	@Test
	public void TestStringParserAndQueueInsertion() {
		
		final String text = "This is just a test this is just a test";
		//final InputStream is = new ByteArrayInputStream(text.getBytes());
		BufferedReader bf = mock(BufferedReader.class);
		
		try {
            when(bf.readLine()).thenReturn(text, (String)null);
        } catch (IOException e) {
            fail();
        }
		ArrayBlockingQueue<String> q = new ArrayBlockingQueue<>(100);
		
		AbstractParser parser = new AbstractParser(q);
		
		parser.readLine(bf);
		
		//valid that queue is not empty
		assertTrue(!q.isEmpty());
		//validate first element in the queue
		assertEquals( "this is just", q.poll());
		
	}
	
	
	@Test
	public void TestUnwantedCharsTrim() {
		
		final String text = "Contraction\tFull Form";
		//final InputStream is = new ByteArrayInputStream(text.getBytes());
		
		ArrayBlockingQueue<String> q = new ArrayBlockingQueue<>(100);
		
		AbstractParser parser = new AbstractParser(q);
		
		List<String> words = parser.trimUnwantedChars(text);
		
		String[] array = words.toArray(new String[0]);
		//validate first element in the queue
		assertEquals( "contraction", array[0]);
		assertEquals( "full", array[1]);
		assertEquals( "form", array[2]);
		
	}
	
	@Test
	public void TestUnwantedCharsContractions() {
		
		final String text = "everybody's";
		//final InputStream is = new ByteArrayInputStream(text.getBytes());
		
		ArrayBlockingQueue<String> q = new ArrayBlockingQueue<>(100);
		
		AbstractParser parser = new AbstractParser(q);
		
		List<String> words = parser.trimUnwantedChars(text);
		
		String[] array = words.toArray(new String[0]);
		//validate first element in the queue
		assertEquals( "everybody's", array[0]);
		
	}
	
	@Test
	public void TestUnwantedCharsContractions2() {
		
		final String text = "'abacate'";
		//final InputStream is = new ByteArrayInputStream(text.getBytes());
		
		ArrayBlockingQueue<String> q = new ArrayBlockingQueue<>(100);
		
		AbstractParser parser = new AbstractParser(q);
		
		List<String> words = parser.trimUnwantedChars(text);
		
		String[] array = words.toArray(new String[0]);
		//validate first element in the queue
		assertEquals( "abacate", array[0]);
		
	}
	
	
}
