/**
 * 
 */
package it.cnr.ilc.cophi.alignment;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;


/**
 * @author Angelo Del Grosso
 *
 */
final public class GrPhoneTransformer {

	Map<String,String> rules;

	public GrPhoneTransformer() {
		rules = initialize();
	}
	
	
	
	public String transform(String world){
		Set<Entry<String,String>> mapEntries = rules.entrySet();
		
		for(Entry<String,String> e : mapEntries){
			//System.err.println(e.getKey());
			world = world.replaceAll(e.getKey(), e.getValue());
		}
		
		world = postprocessing(world);
		
		return world;
	}
	

	private String postprocessing(String world) {
		// TODO Auto-generated method stub
		boolean cond;
		cond = 	"".equals(world)
				|| "zeus".equals(world)
				|| world.endsWith("a") 
				|| world.endsWith("e") 
				|| world.endsWith("i") 
				|| world.endsWith("o")
				|| world.endsWith("u")
				|| world.endsWith("r")
				|| world.endsWith("S");
				
		if(!cond)
		 return world.substring(0, world.length()-1) ;
		else
			return world;
	}



	private Map<String, String> initialize() {
		Map<String, String> temp = new LinkedHashMap<String, String>();
		temp.put("ί", "i");
		temp.put("ΐ", "i");
		temp.put("ό", "o");
		temp.put("ά", "u");
		temp.put("ύ", "i");
		temp.put("ή", "e");
		temp.put("έ", "e");
		temp.put("ώ", "w");
		temp.put("h", "e");
		temp.put("w", "o"); // controllare perchè su originale c'è un p!= u'w(\\s2 ':  #salvaSIMS else: p='wS'
		temp.put("g(?=[gkcx])", "n");
		temp.put("q","t");
		temp.put("\\*ca","Xa");   // per lo Xanto
		temp.put("(^c)|(\\*c)","S");
		temp.put("c","ss"); //#x in grafia dotta#
		temp.put("k","c"); //#k in grafia dotta#
		temp.put("x(?=[aei])","ch");
		temp.put("x(?![aei])","c");
		temp.put("\\*y","Ps"); //#funzionale solo prima di 'pulizia'
		temp.put("^y","s");
		temp.put("y","ss"); //#ps in grafia dotta#
		temp.put("ai","e");
		temp.put("ei(?=[aeiou])","e"); 
		temp.put("ei","i");
		temp.put("oi(?=e)","o");
		temp.put("oi","e"); //#in rari casi, i : es. κοιμητήριον cimitero
		temp.put("au(?=[aeiou])|aύ(?=[aeiou])","av");
		temp.put("eu(?=[aeiou])","ev");
		temp.put("ui","i");
		temp.put("(?<![aeou])u","i"); //#!ATTENZIONE alla posizione di questa regola
		temp.put("ou","u");
		temp.put("(^pt)|(\\*pt)","t");
		temp.put("pt","tt");
		temp.put("ti(?=[aeiu])","zi");
		temp.put(";","?");
		temp.put("\\*",""); //#sempre ultima
		return temp;
	}





}
