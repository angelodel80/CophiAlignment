/**
 * 
 */
package it.cnr.ilc.cophi.alignment;

import java.util.Map;
import java.util.Set;

import it.cnr.ilc.cophi.alignment.dictionary.SimilarWordFinder;

/**
 * @author Angelo Del Grosso
 *
 */
public class AlignerTest {
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		
//		testPhonetic();
//		testSimilar();
//		testFindSimilarWords("oliva");
		testCloseMatcher(new String[] {"dio","olimpo"});
		
	}


	
	
	private static void testCloseMatcher(String[] strings) throws Exception {
		// TODO Auto-generated method stub
		CloseMatcher matcher = CloseMatcher.getInstance();
		
		for (String string : strings) {
			matcher.defaultMatch(string);	
		}
		
		Map<String, Set<String>> dic = matcher.getDictionary();
		
		for (String s : strings) {
			
			System.out.println(dic.get(s));
			
		}
	}



	private static void testFindSimilarWords(String word) throws Exception {
		// TODO Auto-generated method stub
		SimilarWordFinder swf = new SimilarWordFinder();
		Set<String> similarWords = swf.getSimilarWords(word);
		
		for (String w : similarWords) {
			
			System.out.println(w);
			
		}
		
	}




	private static void testSimilar() {
		// TODO Auto-generated method stub
		CloseMatcher cm = CloseMatcher.getInstance();
		cm.match("casa", new String[]{"cosa","caso","pluto","casa"}, new String[]{} );
	}




	private static void testPhonetic() {
		String[] worlds = 
			{"*peloponnhsos",
		       "anqrwpofagos",
		       "gewgrafίa",
		       "aggelos",
		       "egkwmion",
		       "qeologίa",
		       "*kalliόph",
		       "*cenofwn",
		       "acίwma",
		       "*cάnqos",
		       "lura",
		       "filosofίa",
		       "xorόs",
		       "yalthrion",
		       "gύyos",
		       "*yuxh",
		       "aiqήr",
		       "eidwlon",
		       "gunaikeion",
		       "*oidίpous",
		       "poihths",
		       "*pausanίas",
		       "naύarxos",
		       "*eumenhs",
		       "*euandros",
		       "mousa",
		       "arpuia",
		       "*ptolemaios",
		       "optikόs",
		       "hrws",
		       "rόmbos",
		       "*zeus",
		       "*dios",
		       "*hrh",
		       "lisseto",
		       "oneiropolon",
		       "ekatombhs",
		       "peribάllw",
		       "nektar",
		       "ornis",
		       "w(\\s2" };
		
		GrPhoneTransformer gr = new GrPhoneTransformer();	
		
		for (String w : worlds) {
			System.err.println(gr.transform(w));
		}

		
	}

}
