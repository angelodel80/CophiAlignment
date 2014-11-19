/**
 * 
 */
package it.cnr.ilc.cophi.alignment;

import it.cnr.ilc.cophi.alignment.dictionary.SimilarWordFinder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import uk.ac.shef.wit.simmetrics.SimpleExample;

/**
 * @author Angelo Del Grosso
 *
 */
public class CloseMatcher {

	private Map<String,Set<String>> dictionary;

	/**
	 * 
	 */
	private CloseMatcher() {
		dictionary = new HashMap<String, Set<String>>();
	}
	
	public static CloseMatcher getInstance(){
		return new CloseMatcher();
	}

	public int match(String world, String[] text, String[] params){
		int numMatches = 0;
		for (String wt : text) {		
			SimpleExample.main(new String[] {world,wt});
		}
		return numMatches;
	}
	
	public int defaultMatch(String world) throws Exception{
		int numMatches = 0;
		SimilarWordFinder swf = new SimilarWordFinder();
		Set<String> similarWords = swf.getSimilarWords(world);
		dictionary.put(world, similarWords);
		numMatches = similarWords.size();
		return numMatches;
	}

	/**
	 * @return the dictionary
	 */
	public Map<String, Set<String>> getDictionary() {
		return dictionary;
	}

	/**
	 * @param dictionary the dictionary to set
	 */



}
