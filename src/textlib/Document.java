package textlib;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.SortedMap;
import java.util.StringTokenizer;
import java.util.TreeMap;

/**
 * Class that represents a text document
 * Keeps track of the number of times a word appears in the document, it's term frequency, and 
 * eventually, its inverse document frequency (if used with TfIdf) for finding
 * important keywords in the document
 * 
 * 
 * @author Noelia Perez
 *
 */
public class Document {
	public TreeMap<String, Double[]> words; // n_ij, tf_ij, tf_idf
	int sumof_n_kj;
	double vectorlength;
	LinkedList<String> stopWords;
	LinkedList<String> sentences;

	
	public LinkedList<String> getSentences() {
		return sentences;
	}

	/**
	 * Constructor for document class that is used by the parent TfIdf class
	 * @param br Reader that loaded the text file already, used to read lines from
	 * large documents
	 * @param parent the TfIdf class calling this ctor
	 */
	public Document(String filename) {
		BufferedReader br;
		String line;
		String word;
		StringTokenizer tokens;
		sumof_n_kj = 0;
		vectorlength = 0;
		Double[] tempdata;
		words = new TreeMap<String, Double[]>();
		stopWords = new LinkedList<String>();
		buildStopWords();
	
		try {
			br = new BufferedReader(new FileReader(filename));
			line = br.readLine();
			while (line != null) {
				tokens = new StringTokenizer(line, ":; \"\',.[]{}()!?-/");
				while(tokens.hasMoreTokens()) {
					word = tokens.nextToken().toLowerCase();
					word.trim();
					
					if(isStopWord(word) || word.length() < 3) continue;
					//if (word.length() < 2) continue;
					if (words.get(word) == null) {
						tempdata = new Double[]{1.0,0.0};
						words.put(word, tempdata);
					}
					else {
						tempdata = words.get(word);
						tempdata[0]++;
						words.put(word,tempdata);
					}
					sumof_n_kj++;
				}
				line = br.readLine();

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Iterate through the words to fill their tf's
		for (Iterator<String> it = words.keySet().iterator(); it.hasNext(); ) {
			word = it.next();
			tempdata = words.get(word);
			tempdata[1] = tempdata[0] / (float) sumof_n_kj;
			words.put(word,tempdata);
		}

	}
	
	public void buildStopWords(){
		
		BufferedReader br;
		String line;
		
		try {
			br = new BufferedReader(new FileReader("C:/Users/USUARIO/Documents/GitHub/Proyecto/stopWords.txt"));
			line = br.readLine();
			while (line != null) {
				stopWords.add(line);
				line = br.readLine();

			}
			
			
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		}
	
	public LinkedList<String> getStopWords() {
		return stopWords;
	}
	
	public boolean isStopWord(String word){
		for (String w : getStopWords()){


			if (w.equals(word)) 
				return true;
			
		}
		return false;
	}
	

	/**
	 * Gives the most important numWords words
	 * @param numWords Number of words to return
	 * @return String array of words
	 */
	public String[] bestWordList(int numWords) {
		SortedMap<String, Double[]> sortedWords = new TreeMap<String, Double[]>(new Document.ValueComparer(words));
		sortedWords.putAll(words);
		int counter = 0;
		String[] bestwords = new String[numWords];
		for (Iterator<String> it = sortedWords.keySet().iterator(); it.hasNext() && (counter < numWords); counter++) {
			bestwords[counter] = it.next();

		}
		return bestwords;
	}
	
	/**
	 * Override for bestWordList with default number of words of 10
	 * @return String array of best words
	 */
	public String[] bestWordList() {
		return bestWordList(20);
	}
	
	/** inner class to do sorting of the map **/
	private static class ValueComparer implements Comparator<String> {
		private TreeMap<String, Double[]>  _data = null;
		public ValueComparer (TreeMap<String, Double[]> data){
			super();
			_data = data;
		}

         public int compare(String o1, String o2) {
        	 double e1 = ((Double[]) _data.get(o1))[1];
        	// System.out.println("--------->" + e1);
             double e2 = ((Double[]) _data.get(o2))[1];
        	// System.out.println("--------->" + e2);

             if (e1 > e2) return -1;
             if (e1 == e2) return 0;
             if (e1 < e2) return 1;
             return 0;
         }
	}
	
	//////////////////////////////////////////////// 
	public void obtenerFrases(String filename){
		String line;
		BufferedReader br;
		StringTokenizer tokens;
		String word;
		StringTokenizer tokens2;
		String onebyone;
		
		try {
			sentences = new LinkedList<String>();
			br = new BufferedReader(new FileReader("C:/Users/USUARIO/Documents/GitHub/Proyecto/Documento/hola.txt"));
			line = br.readLine();

			while (line != null) {
				tokens = new StringTokenizer(line, ".?");
				while(tokens.hasMoreTokens()) {
					word = tokens.nextToken().toLowerCase();
					word.trim();
					
					tokens2= new StringTokenizer(word, " ");
					while (tokens2.hasMoreTokens()){
						onebyone = tokens2.nextToken().toLowerCase();
						onebyone.trim();
					
						for (String w : this.bestWordList()){
							if (onebyone.equals(w)){
								if (!sentences.contains(word)){
									sentences.add(word);
									System.out.println("-------> " + word);
								}
					}}}
				}
				line = br.readLine();

			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		
		}
	
	public static void main(String[] args){

		Document tf = new Document("C:/Users/USUARIO/Documents/GitHub/Proyecto/Documento/hola.txt");
		String word;
		Double[] corpusdata;
		String[] bwords;
		
		
		for (Iterator<String> it = tf.words.keySet().iterator(); it.hasNext(); ) {
			word = it.next();
			corpusdata = tf.words.get(word);
			System.out.println(word + " " + corpusdata[0] + " " + corpusdata[1]);
		}	
	
		
		System.out.println("\n");

		System.out.println("hola.txt");
		System.out.println("------------------------------------------");
		bwords = tf.bestWordList(5);
		for (int i = 0; i < 5; i++) {
			System.out.print(bwords[i] + " ");
		}
		System.out.println("\n");

		//////////////////////////////

		tf.obtenerFrases("C:/Users/USUARIO/Documents/GitHub/Proyecto/Documento/hola.txt");
		System.out.println(tf.getSentences());
	
	
	
}}