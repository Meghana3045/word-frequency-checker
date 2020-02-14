package org.sample.project.wordfrequency;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;

/**
 * Purpose of this class is to generate the word frequencies given the input files
 * 
 * @author Meghana
 */
public class WordFrequency {
	
	Map<String,Map<String, Integer>> wordFrequencyMap = new HashMap<>();
	String inputfileName = "inputfile";
	
	public WordFrequency(String inputfile) throws IOException {
		// Load Word frequency in a map
		this.inputfileName = inputfile;
		init();
	}

	/**
	 * init method loads the seeder file(which contains relative location of input files containing words/text) 
	 * by calling loadinputfile method.
	 * For each input file, this method calls the loadWordFrequencyMap to load all the words from each input 
	 * file into the global map wordFrequencyMap.
	 * 
	 * @return void
	 * @throws IOException
	 */
	public void init() throws IOException {
		InputStream inputStream = loadFile(inputfileName);
		if(inputStream == null) {
			throw new FileNotFoundException();
		}
		
		Scanner loadersc = null;
		try {
			loadersc = new Scanner(inputStream, "UTF-8");
			// Add all the file names mentioned in inputfile into filesList
			ArrayList<String> filesList = new ArrayList<String>();
		    while (loadersc.hasNextLine()) {
		        filesList.add(loadersc.nextLine());
		    }
		    // For each file in filesList, load/update the wordFrequencyMap
		    for(String fileName: filesList) {
		    	loadWordFrequencyMap(fileName);
		    }
		} finally {
			// Close the stream and scanner objects to avoid leak
		    if (inputStream != null) {
				inputStream.close();
		    }
		    if (loadersc != null) {
		    	loadersc.close();
		    }
		}
	}
	
	
	 // loadFile method creates an inputStream on provided file and returns inputStream.
	 // @return {@link InputStream}
	 // @param fileName
	 
	public InputStream loadFile(String fileName) {
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(fileName);
		return inputStream;
	}
	
	/**
	 * loadWordFrequencyMap loads all the words from provided file into a wordFrequencyMap
	 * Map Structure : 
	 * <word, < <fileName, wordFrequency>, <fileName, wordFrequency> ,... >
	 * example: <manner, < <testfile1, 5>, <testfile2, 8>, <testfile3, 7> >
	 * which means -
	 * word "manner" appears 5 times in inputfile1, 3 times in inputfile2, 1 time in inputfile3 and 7 times in inputfile4
	 * 
	 * 
	 * 
	 * 
	 * @param fileName
	 * @throws IOException 
	 */
	public void loadWordFrequencyMap(String fileName) throws IOException {
		InputStream inputStream = null;
		Scanner sc = null;
		try {
			// Load wordFrequencyMap for provided input file 'fileName'
			inputStream = loadFile(fileName);
			if(inputStream != null) {
				// Scanner object to read inputStream (from input file)
				sc = new Scanner(inputStream, "UTF-8");
				// wordMapInternal is the value of each word in wordFrequencyMap. 
				// wordMapInternal- Key is inputfilename and value is frequency(of the word in that filename)
				Map<String, Integer> wordMapInternal;
			    while (sc.hasNextLine()) {
			    	// read each line in the file to capture words
			    	String[] words = sc.nextLine().split("[!_.'‘,@?;\" ]");
					/* Logic-
					for each word in the file-
					if word already exist in wordFreqencyMap then-
						if wordMapInternal consists this inputfile entry/key then-
							update the entry in wordMapInternal by incrementing word frequency value
						else
							create a new entry in wordMapInternal for this inputfile with frequency as 1
					else
						create a new entry in wordMapInternal for this inputfile with frequency as 1
						put wordMapInternal in wordFrequencyMap
					end for */
			    	for(String word: words) {
			    		wordMapInternal = new HashMap<>();
			    		if(wordFrequencyMap.containsKey(word)) {
			    			wordMapInternal = wordFrequencyMap.get(word);
			    			if(wordMapInternal.containsKey(fileName)) {
			    				wordMapInternal.put(fileName, wordMapInternal.get(fileName)+1);
			    			}else {
			    				wordMapInternal.put(fileName, 1);
			    			}
			    		}else {
			    			wordMapInternal.put(fileName, 1);
			    		}
			    		wordFrequencyMap.put(word, wordMapInternal);
			    	}
			    }
			}
		} catch(Exception e) {
			// throws IOException
			System.out.println(e);
			throw e;
		} finally {
			// Close the stream and scanner objects to avoid memory leak
		    if (inputStream != null) {
				inputStream.close();
		    }
		    if (sc != null) {
		        sc.close();
		    }
		}
	}
	
	// printWordFrequency method prints all the words with their frequency 
	public void printWordFrequency() {
		if(wordFrequencyMap != null) {
			Set<Entry<String, Map<String, Integer>>> externalEntrySet = wordFrequencyMap.entrySet();
		    for(Entry<String, Map<String, Integer>> externalEntry: externalEntrySet) { 
		    	Map<String, Integer> internalMap = externalEntry.getValue();
		    	Set<Entry<String, Integer>> internalEntrySet = internalMap.entrySet();
		    	int total = 0;
		    	for(Entry<String,Integer> internalEntry: internalEntrySet) {
		    		total = total + internalEntry.getValue();
		    	}
		    	System.out.println(externalEntry.getKey()+" is repated "+total+"\n");
		    }
		}
	}
	
	/**
	 * printWordFrequencyDetails method prints the frequency details of provided word
	 * 
	 * @return void
	 * @param word
	 * @throws  
	 */
	public void printWordFrequencyDetails(String word) {
		if(wordFrequencyMap != null) {
			Map<String, Integer> internalMap = wordFrequencyMap.entrySet().stream()
					.filter(e -> e.getKey().equals(word))
					.map(e -> e.getValue())
					.findAny()
					.orElse(null);
					
			int total = 0;
			if(internalMap != null) {
				Set<Entry<String, Integer>> internalEntrySet = internalMap.entrySet();
		    	System.out.println(word);
		    	for(Entry<String,Integer> internalEntry: internalEntrySet) {
		     		System.out.println("No.of times " + word +" is repeated in the file : "+internalEntry.getKey()+" is "+internalEntry.getValue());
		    		total = total + internalEntry.getValue();
		    	}
			}
	    	System.out.println("total No.of times " + word + " is repated "+total+"\n");
		}
	}
}