package textlib;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

public class Principal {


	
	public static void main(String[] args) throws IOException {
		
		String s;
		BufferedWriter out;
	
		
//		System.out.println("Periódico LA VERDAD: \n");
//		Consumer feed1 = new Consumer("http://www.laverdad.es/murcia/rss/feeds/ultima.xml");
//		feed1.print();
//		System.out.println("-------------------------------------------------------------------");
//

	
		
//		System.out.println("Periódico EL PAÍS: \n");
//		Consumer feed3 = new Consumer("http://ep00.epimg.net/rss/elpais/portada.xml");
//		feed3.print();
//		System.out.println("-------------------------------------------------------------------");

	
		
		
		System.out.println("Periódico EL MUNDO: \n");
		Consumer feed2 = new Consumer("http://elmundo.feedsportal.com/elmundo/rss/portada.xml");
		feed2.print();
		System.out.println("-------------------------------------------------------------------");

		
		
		
		// for (Iterator<String> it =  feed2.getEntries().keySet().iterator(); it.hasNext(); ) {
			Comun parser = new Comun();
			
			out = new BufferedWriter(new FileWriter("C:/Users/USUARIO/Desktop/prueba1.txt", true));   
		  
			//System.out.println(feed2.entries.get(it.next()) + " " + it.next());
			
			System.out.println("keyset----------- " + feed2.entries.firstKey() + "----- " + feed2.entries.get(feed2.entries.firstKey()));

		
			s = parser.Parseado(feed2.entries.firstKey() ,"tamano");
			out.write(s);
			out.close();
			
			
		//}
		
		/////////////////////////////////////////////////////// document ////////
			
			Document tf = new Document("C:/Users/USUARIO/Desktop/prueba1.txt");
			String word;
			Double[] corpusdata;
			String[] bwords;


			for (Iterator<String> it = tf.words.keySet().iterator(); it.hasNext(); ) {
				word = it.next();
				corpusdata = tf.words.get(word);
				System.out.println(word + " " + corpusdata[0] + " " + corpusdata[1]);
			}	


			System.out.println("\n");

			System.out.println("PALABRAS IMPORTANTES");
			System.out.println("------------------------------------------");
			bwords = tf.bestWordList(5);
			for (int i = 0; i < 5; i++) {
				System.out.print(bwords[i] + " ");
			}
			System.out.println("\n");

			//////////////////////////////

			tf.obtenerFrases("C:/Users/USUARIO/Desktop/prueba1.txt");
			tf.normalizarLema();

			System.out.println(tf.getSentences());
			System.out.println(tf.getSentences().size());
			tf.postProcesado();
			System.out.println(tf.getSentences());
			System.out.println(tf.getSentences().size());
			System.out.println("Total---> " + tf.allSentences.size());
			


		
	}

}
