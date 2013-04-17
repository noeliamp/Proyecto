package textlib;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;

public class Principal {


	
	public static void main(String[] args) throws IOException {
		
		String s;
		BufferedWriter out;
		BufferedWriter resumen;

		
	
		
		//// ----------Periodico el pais -------------/////

		System.out.println("Periódico EL PAÍS: \n");
		Consumer feed = new Consumer("http://ep00.epimg.net/rss/elpais/portada.xml");
		feed.print();
		System.out.println("-------------------------------------------------------------------");

		Comun parser = new Comun();
		

		
		out = new BufferedWriter(new FileWriter("C:/Users/USUARIO/Desktop/prueba1.txt", true));   
		s = parser.Parseado(feed.entries.get(feed.entries.firstKey()) ,"cuerpo_noticia");
		
		

		System.out.println("DIRECCION " + feed.entries.get(feed.entries.firstKey()));
		
		System.out.println("TEXTO DE LA NOTICIA: " + s);
		out.write(s);
		out.close();
		
		
		//// ----------Periodico la verdad -------------/////

//		System.out.println("Periódico La Verdad: \n");
//		Consumer feed = new Consumer("http://www.laverdad.es/murcia/portada.xml");
//		feed.print();
//		System.out.println("-------------------------------------------------------------------");
//
//		Comun parser = new Comun();
//			
//		out = new BufferedWriter(new FileWriter("C:/Users/USUARIO/Desktop/prueba1.txt", true));   
//		s = parser.Parseado(feed.entries.get(feed.entries.firstKey()) ,"ccronica");
//			
//		System.out.println("DIRECCION " + feed.entries.get(feed.entries.firstKey()));
//		System.out.println("TEXTO DE LA NOTICIA: " + s);
//			
//		out.write(s);
//		out.close();
			
		
		////////////////////////////// Resumen Document  ////////////////////////
			
			Document tf = new Document("C:/Users/USUARIO/Desktop/prueba1.txt");
			String word;
//			Double[] corpusdata;
//			String[] bwords;
			StringTokenizer tokens;

			
			tokens = new StringTokenizer(feed.entries.firstKey(), ":; \"\',.[]{}()!?/", true);
			while(tokens.hasMoreTokens()) {
				word = tokens.nextToken().toLowerCase();
				word.trim();
				if (tf.isStopWord(word)) continue;
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
			
//			System.out.println("PALABRAS IMPORTANTES");
			System.out.println("------------------------------------------");
//			bwords = tf.bestWordList(5);
//			for (int i = 0; i < 5; i++) {
//				System.out.print(bwords[i] + " ");
//			}
//			System.out.println("\n");

			////////////////////////////

			tf.obtenerFrases("C:/Users/USUARIO/Desktop/prueba1.txt");

			tf.Porcentaje(40);
			System.out.println("\n");
			
			System.out.println("----RESUMEN DE LA NOTICIA----: " + feed.entries.firstKey() + "\n");
			System.out.println("Tamaño del resumen antes de procesar: " + tf.getMostradas().size() + "\n");

			
			for (String punto : tf.getMostradas()){
				System.out.println(punto + ".");
			}
			System.out.println("\n");

			tf.postProcesado();
			System.out.println("Tamaño del resumen despues de procesar: " +tf.getMostradas().size() + "\n");

			for (String punto1 : tf.getMostradas()){
				System.out.println(punto1 + ".");
			}
			System.out.println("\n");

			
			System.out.println("Tamaño Total de la noticia ---> " + tf.allSentences.size());
			
			// borrar el fichero de salida del que se toma la noticia
			
			FileOutputStream fso = new FileOutputStream("C:/Users/USUARIO/Desktop/prueba1.txt"); 
			fso.close(); 
			
			// borrar el fichero de salida del resumen antes de escribir nuevo resumen
			
			FileOutputStream fso1 = new FileOutputStream("C:/Users/USUARIO/Desktop/prueba2.txt"); 
			fso1.close(); 
			
			// escribir el resumen en el fichero
			
			resumen = new BufferedWriter(new FileWriter("C:/Users/USUARIO/Desktop/prueba2.txt", true));
			for (String linea : tf.getMostradas()){
				resumen.write(linea + ".");
			}
			resumen.close();
	}
			

}
