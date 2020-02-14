package org.sample.project.wordfrequency;

import java.io.IOException;
import java.util.Scanner;

//Word Frequency client class
public class WordFrequencyCheckerClient {

	
	// main method is a user interaction client for Word Frequency.
	public static void main(String[] args) throws IOException {
		
		WordFrequencyChecker wordFrequency = new WordFrequencyChecker("inputfile");
		
		// Interact with user to perform operations
		Scanner sc = null;
		try {
			while(true) {
	            System.out.println();
	            System.out.println("Operations:");
	            System.out.println("1. Print frequency of all the words");
	            System.out.println("2. Print frequency details of a word");
	            System.out.println("3. Exit");
	            System.out.println("Enter option # to perform operation:");
	            sc = new Scanner(System.in);
	            
	            int i = 0;
	            
	            try {
	            	i = sc.nextInt();
	            } catch(Exception e) {
	            	// Not catching an exception here as we have not defined any case for i = 0
	            }
	            
			    switch(i) {
	                case 1:
	                	wordFrequency.printWordFrequencyChecker();
	                    break; 
	                case 2:
	                    System.out.println("Enter word to get frequency : ");
                    	sc = new Scanner(System.in);
                        String word = sc.next();
                        wordFrequency.printWordFrequencyCheckerDetails(word);
	                    break;
	                case 3:
	                	System.out.println("Existing the Word Frequency Checker Application");
	                	System.exit(0);
	                default:
	                	System.out.println("Invalid option, please choose from above choices");
	                	break;
	            }
	        }
		} finally {
			if(sc!=null) {
				// Closing the Scanner
				sc.close();
			}
		}
	}
}
