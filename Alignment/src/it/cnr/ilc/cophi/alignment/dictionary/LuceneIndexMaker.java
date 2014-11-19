package it.cnr.ilc.cophi.alignment.dictionary;

import java.io.File;
import org.apache.lucene.analysis.WhitespaceAnalyzer;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.spell.PlainTextDictionary;
import org.apache.lucene.search.spell.SpellChecker;
import org.apache.lucene.search.spell.SuggestMode;
import org.apache.lucene.store.NIOFSDirectory;
import org.apache.lucene.store.NoLockFactory;
import org.apache.lucene.util.Version;

/**
 *
 * @author federico[DOT]boschetti[DOT]73[AT]gmail[DOT]com
 */
public class LuceneIndexMaker {
    //final static File fileDict=new File("/home/federico/shit/grc.dic");
    //final static File dirDict=new File("/home/federico/shit/lucene_index_grc");

	final static File fileDict=new File("C:/tmp/aligner/dics/nomi_itali.txt");
    final static File dirDict=new File("C:/tmp/aligner/dics/lucene_index_ita");

    IndexWriterConfig iwc;
    SpellChecker spellchecker;
    
    public LuceneIndexMaker() throws Exception{
        init();
    }
    
    public void init() throws Exception{
        NIOFSDirectory spellIndexDirectory =new NIOFSDirectory(dirDict, NoLockFactory.getNoLockFactory());
        spellchecker = new SpellChecker(spellIndexDirectory);
        iwc=new IndexWriterConfig(Version.LUCENE_36,new WhitespaceAnalyzer(Version.LUCENE_36));

    }
    
    public void makeIndex() throws Exception{
        spellchecker.indexDictionary(new PlainTextDictionary(fileDict),iwc,true);        
    }
    
    public static void main(String args[]) throws Exception{
        LuceneIndexMaker luceneIndexMaker=new LuceneIndexMaker();
        luceneIndexMaker.makeIndex();
    }
}
