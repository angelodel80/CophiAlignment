/**
 * 
 */
package it.cnr.ilc.cophi.alignment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Angelo Del Grosso
 *
 */
public class NWAligner {
	private double gapPenality;
	private GrPhoneTransformer phonetransformer;
	private Map<String,Set<String>> dictionary;
	
	public final static String gapChar = "^";
	
	/**
	 * 
	 */
	public NWAligner() {
		// TODO Auto-generated constructor stub
	}
	
	
	public List<NWRecord> PerformAlignment(String[] chunksSrc, String[] chunksTrg){
		List<NWRecord> alignments = new ArrayList<NWRecord>();
		
		int n = chunksSrc.length;
		int m = chunksTrg.length;
		
		double[][] simMatrix = new double[n][m];
		double[][] pathMatrix = new double[n+1][m+1];
		
		fillSimMatrix(simMatrix,chunksSrc,chunksTrg);
		fillPathMatrix(pathMatrix,simMatrix, chunksSrc, chunksTrg);
		
		alignments = makeAlignment(simMatrix, pathMatrix, chunksSrc, chunksTrg);
		
		return alignments;
	}

	private List<NWRecord> makeAlignment(double[][] simMatrix,
			double[][] pathMatrix, String[] chunksSrc, String[] chunksTrg) {
		// TODO Auto-generated method stub
		
		List<NWRecord> alignResults = new ArrayList<NWRecord>();
		List<String> tmpMatchSrc = new ArrayList<String>();
		List<String> tmpMatchTrg = new ArrayList<String>();
		
		int i = chunksSrc.length;
		int j = chunksTrg.length;
		
		while(i > 0 && j > 0){
			double score = pathMatrix[i][j];
			double scoreDiagInv = pathMatrix[i-1][j-1];
			double scoreLeft = pathMatrix[i-1][j];
			
			if(score==scoreDiagInv + simMatrix[i-1][j-1]){
				tmpMatchSrc.add(chunksSrc[i-1]);
				tmpMatchTrg.add(chunksTrg[j-1]);
				i = i-1;
				j = j-1;
			}
			else if (score == scoreLeft + getGapPenality()) {
				tmpMatchSrc.add(chunksSrc[i-1]);
				tmpMatchTrg.add(gapChar);
				i = i-1;
			}
			else {
				tmpMatchSrc.add(gapChar);
				tmpMatchTrg.add(chunksTrg[j-1]);
				j = j-1;
			}
				
			
		}
		
		while(i>0){
			tmpMatchSrc.add(chunksSrc[i-1]);
			tmpMatchTrg.add(gapChar);
			i = i-1;
		}
		while(j>0){
			
			tmpMatchSrc.add(gapChar);
			tmpMatchTrg.add(chunksTrg[j-1]);
			j = j-1;
			
		}
		
		return alignResults;
	}


	private void fillPathMatrix(double[][] pathMatrix, double[][] simMatrix, String[] chunksSrc, String[] chunksTrg) {
		pathMatrix[0][0] = 0.0;
		for(int i=1; i <= chunksSrc.length; i++){
			pathMatrix[i][0] = i*getGapPenality();
		}
		for(int j=1; j <= chunksTrg.length; j++){
			pathMatrix[0][j] = j*getGapPenality();
		}
		
		
		for(int i=1; i <= chunksSrc.length; i++){
			for(int j=1; j <= chunksTrg.length; j++){
				
				double scoreDown = pathMatrix[i-1][j] + getGapPenality();
				double scoreRight = pathMatrix[i][j-1] + getGapPenality();
				double scoreDiag = pathMatrix[i-1][j-1] + simMatrix[i-1][j-1];
				double bestScore = Math.max(Math.max(scoreDown, scoreRight), scoreDiag);
				pathMatrix[i][j] = bestScore;
				
			}
			
		}
		
	}


	private void fillSimMatrix(double[][] simMatrix, String[] chunksSrg, String[] chunksTrg) {
		// TODO Auto-generated method stub
		for(int i = 0; i < chunksSrg.length; i++)
			for(int j = 0; j< chunksTrg.length; i++){
				simMatrix[i][j] = simScore(chunksSrg[i],chunksTrg[j]);
			}
		
	}


	private double simScore(String src, String trg) {
		// TODO Auto-generated method stub
		double score = 0.0;
		int i = 0;
		List<String> possibleTradux = new ArrayList<String>();
		List<String> findTradux = new ArrayList<String>();
		
		String[] srcToks = src.split("\\s");
		
		for (String tok : srcToks) {
			srcToks[i] = phonetransformer.transform(tok);
			if(dictionary.containsKey(srcToks[i])){
				possibleTradux.addAll(dictionary.get(srcToks[i]));
			}
			i = i+1;
		}
		
		for (String trad : possibleTradux) {
			if (trg.contains(" "+trad+" ")){
				findTradux.add(trad);
				trg.replaceFirst("\\Q"+trad+" \\E", ""); 
			}
		}
		
		score = findTradux.size(); 
		
		return score;
	}


	/**
	 * @param args
	 */

	/**
	 * @return the gapPenality
	 */
	public double getGapPenality() {
		return gapPenality;
	}


	/**
	 * @param gapPenality the gapPenality to set
	 */
	public void setGapPenality(double gapPenality) {
		this.gapPenality = gapPenality;
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
	public void setDictionary(Map<String, Set<String>> dictionary) {
		this.dictionary = dictionary;
	}


	/**
	 * @return the phonetransformer
	 */
	public GrPhoneTransformer getPhonetransformer() {
		return phonetransformer;
	}


	/**
	 * @param phonetransformer the phonetransformer to set
	 */
	public void setPhonetransformer(GrPhoneTransformer phonetransformer) {
		this.phonetransformer = phonetransformer;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		NWAligner aligner = new NWAligner();
		aligner.setPhonetransformer(new GrPhoneTransformer());
		aligner.setGapPenality(0.0);
		
		

	}


}
