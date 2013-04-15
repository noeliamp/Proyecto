package textlib;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.StringTokenizer;

public class Principal {


	
	public static void main(String[] args) throws IOException {
		
		String s;
		BufferedWriter out;
		
		
		
		//// ----------Periodico el mundo -------------/////
		
		
		
//		System.out.println("Periódico El Mundo: \n");
//		Consumer feed1 = new Consumer("http://elmundo.feedsportal.com/elmundo/rss/portada.xml");
//		feed1.print();
//		System.out.println("-------------------------------------------------------------------");
//
//
//		Comun parser = new Comun();
//		
//
//		
//		out = new BufferedWriter(new FileWriter("C:/Users/USUARIO/Desktop/prueba1.txt", true));   
//		s = parser.Parseado(feed1.entries.get(feed1.entries.firstKey()) ,"tamano");
//		
//		
//
//		System.out.println("DIRECCION " + feed1.entries.get(feed1.entries.firstKey()));
//		
//		System.out.println("TEXTO DE LA NOTICIA: " + s);
//		out.write(s);
//		out.close();
	
		
		//// ----------Periodico el pais -------------/////

//		System.out.println("Periódico EL PAÍS: \n");
//		Consumer feed3 = new Consumer("http://ep00.epimg.net/rss/elpais/portada.xml");
//		feed3.print();
//		System.out.println("-------------------------------------------------------------------");
//
//		Comun parser = new Comun();
//		
//
//		
//		out = new BufferedWriter(new FileWriter("C:/Users/USUARIO/Desktop/prueba1.txt", true));   
//		s = parser.Parseado(feed3.entries.get(feed3.entries.firstKey()) ,"cuerpo_noticia");
//		
//		
//
//		System.out.println("DIRECCION " + feed3.entries.get(feed3.entries.firstKey()));
//		
//		System.out.println("TEXTO DE LA NOTICIA: " + s);
//		out.write(s);
//		out.close();
//		
		
		//// ----------Periodico la verdad -------------/////

		System.out.println("Periódico La Verdad: \n");
		Consumer feed2 = new Consumer("http://www.laverdad.es/murcia/portada.xml");
		feed2.print();
		System.out.println("-------------------------------------------------------------------");

		
		
		
		// for (Iterator<String> it =  feed2.getEntries().keySet().iterator(); it.hasNext(); ) {
			Comun parser = new Comun();
			

			
			out = new BufferedWriter(new FileWriter("C:/Users/USUARIO/Desktop/prueba1.txt", true));   
			s = parser.Parseado(feed2.entries.get(feed2.entries.firstKey()) ,"ccronica");
			
			

			System.out.println("DIRECCION " + feed2.entries.get(feed2.entries.firstKey()));
			
			System.out.println("TEXTO DE LA NOTICIA: " + s);
			out.write(s);
			out.close();
			
		//}
		
		////////////////////////////// Resumen Document  ////////////////////////
			
			Document tf = new Document("C:/Users/USUARIO/Desktop/prueba1.txt");
			String word;
			Double[] corpusdata;
			String[] bwords;
			StringTokenizer tokens;

			
			tokens = new StringTokenizer(feed2.entries.firstKey(), ":; \"\',.[]{}()!?-/");
			while(tokens.hasMoreTokens()) {
				word = tokens.nextToken().toLowerCase();
				word.trim();
				tf.titleWords.add(word);
			}

			////////////////// Muestra cada palabra con su TF /////////////////////////////
//			for (Iterator<String> it = tf.words.keySet().iterator(); it.hasNext(); ) {
//				word = it.next();
//				corpusdata = tf.words.get(word);
//				System.out.println(word + " " + corpusdata[0] + " " + corpusdata[1]);
//			}	
//
//			System.out.println("\n");

			
			//////////////// Muestra las palabras con mayor TF //////////////////////
			
			System.out.println("PALABRAS IMPORTANTES");
			System.out.println("------------------------------------------");
			bwords = tf.bestWordList(5);
			for (int i = 0; i < 5; i++) {
				System.out.print(bwords[i] + " ");
			}
			System.out.println("\n");

			////////////////////////////

			tf.obtenerFrases("C:/Users/USUARIO/Desktop/prueba1.txt");

			tf.Porcentaje(40);
			
			System.out.println(tf.getSentences());
			System.out.println(tf.getSentences().size());
			tf.postProcesado();
			System.out.println(tf.getSentences());
			System.out.println(tf.getSentences().size());
			System.out.println("Total---> " + tf.allSentences.size());
			
			// borrar el fichero de salida
			
			FileOutputStream fso = new FileOutputStream("C:/Users/USUARIO/Desktop/prueba1.txt"); 
			fso.close(); 
		
	}

}
