package it.cnr.ilc.cophi.alignment.dictionary;


import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.spell.SpellChecker;
import org.apache.lucene.search.spell.SuggestMode;
import org.apache.lucene.search.spell.SuggestWord;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.NIOFSDirectory;

/**
 *
 * @author federico[DOT]boschetti[DOT]73[AT]gmail[DOT]com
 */
public class SimilarWordFinder {
    //final static String testWord="καταβάλλω";
	//final NIOFSDirectory spellIndexDirectory=new NIOFSDirectory(new File("/home/federico/shit/lucene_index_grc"));

	final static String testWord="dio";
    final NIOFSDirectory spellIndexDirectory=new NIOFSDirectory(new File("C:/tmp/aligner/dics/lucene_index_ita"));
    SpellChecker spellChecker;
    IndexReader indexReader;
    
    public SimilarWordFinder() throws Exception{
        init();
    }
    
    public void init() throws Exception{
        indexReader=IndexReader.open(spellIndexDirectory);
        spellChecker = new SpellChecker(spellIndexDirectory);
    }
    
    public void printSimilarWords(String word) throws Exception{
        String[] ress;
        ress=spellChecker.suggestSimilar(testWord,3,0.6f);
        for(String res:ress){
            System.out.println(res);
        }
    }
    
    public Set<String> getSimilarWords(String srcWord) throws Exception{
    	System.err.println("getSimilarWords");
    	Set<String> similarWords = new TreeSet<String>();
    	
    	String[] resultWorlds = spellChecker.suggestSimilar(srcWord, 100, 0.4f);
    	
    	similarWords.addAll(Arrays.asList(resultWorlds));
    	
    	return similarWords;
    }
    
    public static void main(String[] args) throws Exception{
    	System.err.println("inizio");
        SimilarWordFinder similarWordFinder=new SimilarWordFinder();
        similarWordFinder.printSimilarWords(testWord);
    }
        
}
