package org.sample.project.wordfrequency;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Scanner;

import org.junit.BeforeClass;
import org.junit.Test;

public class WordFrequencyCheckerTest {
	protected static WordFrequencyChecker wordFrequency;
	protected static String testfileName = "testfile";
	
	@BeforeClass
	public static void loadMap() throws IOException {
		wordFrequency = new WordFrequencyChecker(testfileName);
	}
	
	// Test for main method()
	@Test
	public void testInit() throws IOException {
		assertEquals(Integer.valueOf(6), wordFrequency.wordFrequencyMap.get("line").get("testfile5"));
	}
	
	@Test(expected = FileNotFoundException.class)
	public void testInitIncorrecttestfile() throws Exception{
		WordFrequencyChecker wf = new WordFrequencyChecker("testfileincorrect");
	}
	
	// Test for printWordFrequency()
		@Test
		public void testLoadWordFrequencyCheckerMapIndividualFile() throws IOException {
			WordFrequencyChecker wf = new WordFrequencyChecker("testfile");
			wf.wordFrequencyMap=new HashMap<>();
			wf.loadWordFrequencyCheckerMap("testfile1");
			
			PrintStream out = new PrintStream(new FileOutputStream("test-output.txt"));
			System.setOut(out);
			
			wf.printWordFrequencyChecker();
			InputStream inputStream = new FileInputStream("test-output.txt");
			Scanner sc = new Scanner(inputStream);
			while(sc.hasNext()) {
				if(sc.nextLine().equals("half")) {
					assertEquals("No.of time repated 5", sc.nextLine());
				}
			}
			sc.close();
			out.close();
			File file = new File("test-output.txt");
			file.delete();
		}
	// Test for incorrect file name
	@Test
	public void testLoadWordFrequencyCheckerMapIncorectFile() throws IOException {
		WordFrequencyChecker wf = new WordFrequencyChecker("testfile");
		wf.wordFrequencyMap=new HashMap<>();
		wf.loadWordFrequencyCheckerMap("nonexistingfile");
		assertEquals(true,wf.wordFrequencyMap.isEmpty());
	}
	
	
	// Test for frequency of words check
	@Test
	public void testPrintWordFrequencyChecker() throws FileNotFoundException {
		PrintStream out = new PrintStream(new FileOutputStream("test-output.txt"));
		System.setOut(out);
		
		wordFrequency.printWordFrequencyChecker();
		InputStream inputStream = new FileInputStream("test-output.txt");
		Scanner sc = new Scanner(inputStream);
		while(sc.hasNext()) {
			if(sc.nextLine().equals("line")) {
				assertEquals("No.of times line is reapted is 4", sc.nextLine());
			}
		}
		sc.close();
		out.close();
		File file = new File("test-output.txt");
		file.delete();
	}
	
	//test for no freq in map
	@Test
	public void testPrintWordFrequencyCheckerEmptyWordFreqMap() throws IOException {
		PrintStream out = new PrintStream(new FileOutputStream("test-output.txt"));
		System.setOut(out);
		WordFrequencyChecker wf = new WordFrequencyChecker("testfile");
		wf.wordFrequencyMap = null;
		wf.printWordFrequencyChecker();
		InputStream inputStream = new FileInputStream("test-output.txt");
		Scanner sc = new Scanner(inputStream);
		assertEquals(false,(sc.hasNext()));
		
		sc.close();
		out.close();
		File file = new File("test-output.txt");
		file.delete();
	}
	
	// Test for printWordFrequencyDetails()
	@Test
	public void testPrintWordFrequencyCheckerDetails() throws FileNotFoundException {
		PrintStream out = new PrintStream(new FileOutputStream("test-output.txt"));
		System.setOut(out);
		
		wordFrequency.printWordFrequencyCheckerDetails("half");
		InputStream inputStream = new FileInputStream("test-output.txt");
		Scanner sc = new Scanner(inputStream);
		boolean allAssertionDone = false;
		assertEquals(true, sc.next().contains("half"));
		while(sc.hasNext()) {
			String content = sc.nextLine();
			if(content.contains("testfile3")) {
				assertEquals("frequency in testfile3 -> 4",content);
				allAssertionDone = true;
			}
		}
		assertEquals(true, allAssertionDone);
		sc.close();
		out.close();
		File file = new File("test-output.txt");
		file.delete();
	}
	
	
	@Test
	public void testPrintWordFrequencyCheckerDetailsMapNull() throws IOException {
		PrintStream out = new PrintStream(new FileOutputStream("test-output.txt"));
		System.setOut(out);
		
		WordFrequencyChecker wf = new WordFrequencyChecker("testfile");
		wf.wordFrequencyMap = null;
		wf.printWordFrequencyCheckerDetails("halfff");
		InputStream inputStream = new FileInputStream("test-output.txt");
		Scanner sc = new Scanner(inputStream);
		assertEquals(false, sc.hasNext());
		sc.close();
		out.close();
		File file = new File("test-output.txt");
		file.delete();
	}
	
	@Test
	public void testPrintWordFrequencyCheckerDetailsZero() throws FileNotFoundException {
		PrintStream out = new PrintStream(new FileOutputStream("test-output.txt"));
		System.setOut(out);
		
		wordFrequency.printWordFrequencyCheckerDetails("testword2");
		InputStream inputStream = new FileInputStream("test-output.txt");
		Scanner sc = new Scanner(inputStream);
		boolean allAssertionDone = false;
	
		while(sc.hasNext()) {
			String content = sc.nextLine();
			assertEquals("total frequency -> 0",content);
			allAssertionDone = true;
		}
		assertEquals(true, allAssertionDone);
		sc.close();
		out.close();
		File file = new File("test-output.txt");
		file.delete();
	}
	
	
	
}
