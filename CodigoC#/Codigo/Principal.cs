using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Codigo
{
    class Principal
    {

        static void Main(string[] args)
        {
            string s;
            RssManager lector = new RssManager();

           // Console.WriteLine("Periódico La Verdad");
           // LinkedList<RssFeedItem> lista = lector.ReadFeed("http://www.laverdad.es/murcia/portada.xml");

            Console.WriteLine("Periódico El País");
            LinkedList<RssFeedItem> lista = lector.ReadFeed("http://ep00.epimg.net/rss/elpais/portada.xml");

            Console.WriteLine("----------------------------------------------------------");

            Comun parser = new Comun();

            using (StreamWriter writer = new StreamWriter("C:/Users/USUARIO/Desktop/prueba1.txt"))
            {

                s = parser.Parseado(lista.First().Link, "cuerpo_noticia");

                Console.WriteLine("DIRECCION " + lista.First().Link);
                Console.WriteLine("TEXTO DE LA NOTICIA: " + s);

                writer.Write(s);
                writer.Close();
            }

            ///////


            Documento tf = new Documento("C:/Users/USUARIO/Desktop/prueba1.txt");
            string word;
            //			Double[] corpusdata;
            //			String[] bwords;
            string[] tokens;


            tokens = lista.First().Title.Split(':', ';', ' ', '\"', '\'', ',', '.', '[', ']', '{', '}', '(', ')', '!', '?', '/');
            foreach (string t in tokens)
            {
                word = t.ToLower();
                word.Trim();
                if (tf.isStopWord(word)) continue;
                tf.getTitleWords().AddLast(word);
            }
            Console.WriteLine("----------------------------------------------------------");


            //////////////// Muestra las palabras con mayor TF //////////////////////

            Console.WriteLine("PALABRAS IMPORTANTES");
            Console.WriteLine("------------------------------------------");
            string[] bwords = tf.bestWordList(5);
            for (int i = 0; i < 5; i++)
            {
                Console.WriteLine(bwords[i]);
            }
            Console.WriteLine("\n");

			////////////////////////////

            

            tf.obtenerFrases("C:/Users/USUARIO/Desktop/prueba1.txt");
         
            tf.Porcentaje(60);
            Console.WriteLine("\n");

            Console.WriteLine("----RESUMEN DE LA NOTICIA----: " + lista.First().Title + "\n");
            Console.WriteLine("Tamaño del resumen antes de procesar: " + tf.getMostradas().Count + "\n");


            foreach (string punto in tf.getMostradas())
            {
                Console.WriteLine(punto + ".");
            }
            Console.WriteLine("\n");


            tf.postProcesado();
            Console.WriteLine("Tamaño del resumen despues de procesar: " + tf.getMostradas().Count + "\n");

            foreach (string punto1 in tf.getMostradas())
            {
                Console.WriteLine(punto1 + ".");
            }
            Console.WriteLine("\n");


            Console.WriteLine("Tamaño Total de la noticia ---> " + tf.getAllSentences().Count);

            // borrar el fichero de salida del que se toma la noticia

            //FileOutputStream fso = new FileOutputStream("C:/Users/USUARIO/Desktop/prueba1.txt");
            //fso.close(); 

            // borrar el fichero de salida del resumen antes de escribir nuevo resumen

            //FileOutputStream fso1 = new FileOutputStream("C:/Users/USUARIO/Desktop/prueba2.txt");
            //fso1.close(); 

            // escribir el resumen en el fichero

            using (StreamWriter resumen = new StreamWriter("C:/Users/USUARIO/Desktop/prueba2.txt"))
            {
                foreach (string linea in tf.getMostradas())
                {
                    resumen.Write(linea + ".");
                }
                resumen.Close();
            }
        }

     


    }
}
