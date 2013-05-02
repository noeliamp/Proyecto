using System;
using System.Collections.Generic;
using System.Linq;
using System.IO;
using System.Text.RegularExpressions;
using System.Collections;
using System.Text;

namespace Codigo
{
  

    public class Documento
    {
      
        private SortedList<string, double[]> words; // n_ij, tf_ij, tf_idf
        private SortedList<string, double[]> wordsF; // n_ij, tf_ij, tf_idf
        private SortedList<double, string> sentenceLema;
        private int sumof_n_kj;
        private LinkedList<string> stopWords;
        private LinkedList<string> sentences;
        private LinkedList<string> titleWords;
        private LinkedList<string> importantWords;
        private LinkedList<string> linkingWords;
        private List<string> allSentences;
        private LinkedList<string> finalistas;
        private List<string> mostradas;

        public Documento(String filename)
        {
            string line;
            string word2;
            string[] tokens;
            sumof_n_kj = 0;
           
            double[] tempdata;
            words = new SortedList<string, double[]>();
            wordsF = new SortedList<string, double[]>();
            stopWords = new LinkedList<string>();
            importantWords = new LinkedList<string>();
            titleWords = new LinkedList<string>();
            linkingWords = new LinkedList<string>();
            sentenceLema = new SortedList<double, string>();
            allSentences = new List<string>();
            sentences = new LinkedList<string>();

            buildStopWords();
            buildImportantWords();
            buildLinkingWords();



            try
            {

                using (StreamReader br = new StreamReader(filename))
                {
                    line = br.ReadLine();
                    string pattern = @" |,|:|/|\\|\'|.\\s";
                    while (line != null)
                    {
                        
                        tokens = Regex.Split(line, pattern);

                        //tokens = line.Split(':', ';', '"', '\'', ',', ' ','\\.\\s', '[', ']', '{', '}', '(', ')', '¡', '!', '?', '-', '/', '-', '>', '<','«', '»');
                        foreach (string word in tokens)
                        {
                            word2 = word;
                            if (isStopWord(word) || word.Length < 3) continue;
                            if (!wordsF.ContainsKey(word) || wordsF[word] == null)
                            {
                                tempdata = new double[] { 1.0, 0.0 };
                                wordsF.Add(word, tempdata);

                            }
                            else
                            {
                                tempdata = wordsF[word];
                                tempdata[0]++;
                                wordsF[word] = tempdata;
                                
                            }
                            sumof_n_kj++;
                        }
                        line = br.ReadLine();

                    }
                }
            }
            catch (IOException e)
            {
                Console.WriteLine(e.StackTrace);
            }

            // Iterate through the words to fill their tf's
            for (IEnumerator<string> it = wordsF.Keys.GetEnumerator(); it.MoveNext(); )
            {
                word2 = it.Current;

                tempdata = wordsF[word2];
                tempdata[1] = tempdata[0] / (float)sumof_n_kj;
                words[word2] = tempdata;

            }

        }


     

        public void buildStopWords()
        {
            string line;
            try
            {
                using (StreamReader br = new StreamReader("C:/Users/USUARIO/Documents/GitHub/Proyecto/stopWords.txt", Encoding.UTF7))
                {
                    line = br.ReadLine();
                   
                    while (line != null)
                    {
                        stopWords.AddLast(line);
                        line = br.ReadLine();

                    }
                }
            }
            catch (IOException e)
            {
                Console.WriteLine(e.StackTrace);
            }
        }

        public void buildImportantWords()
        {

            string line;

            try
            {
                using (StreamReader br = new StreamReader("C:/Users/USUARIO/Documents/GitHub/Proyecto/importantWords.txt", Encoding.UTF7))
                {
                    line = br.ReadLine();

                    while (line != null)
                    {
                        importantWords.AddLast(line);
                        line = br.ReadLine();

                    }

                }
            }
            catch (IOException e)
            {
                Console.WriteLine(e.StackTrace);
            }

        }

        public void buildLinkingWords()
        {
            string line;

            try
            {
                using (StreamReader br = new StreamReader("C:/Users/USUARIO/Documents/GitHub/Proyecto/linkingWords.txt", Encoding.UTF7))
                {
                    line = br.ReadLine();
                while (line != null)
                {
                    linkingWords.AddLast(line);
                    line = br.ReadLine();
                }

            }}
            catch (IOException e)
            {
                Console.WriteLine(e.StackTrace);
            }

        }

     
        public LinkedList<string> getStopWords()
        {
            return stopWords;
        }
        public LinkedList<string> getTitleWords()
        {
            return titleWords;
        }

        public LinkedList<string> getImportantWords()
        {
            return importantWords;
        }
        public LinkedList<string> getLinkingWords()
        {
            return linkingWords;
        }

        public LinkedList<string> getSentences()
        {
            LinkedList<string> copia = new LinkedList<string>(sentences);
            return copia;
        }

        public LinkedList<string> getFinalistas()
        {
            return finalistas;
        }

        public List<string> getMostradas()
        {
            return mostradas;
        }

        public SortedList<double, string> getSentenceLema()
        {
            return sentenceLema;
        }

        public List<String> getAllSentences()
        {
            List<String> copia = new List<String>(allSentences);
            return copia;
        }

        public bool isStopWord(string word){
		foreach (string w in getStopWords()){
			if (w.Equals(word)) 
				return true;
		}
		return false;
	}


        public bool isTitleWord(string word){
		foreach (string w in getTitleWords()){
			if (w.Equals(word)) 
				return true;		
		}
		return false;
	}

        public bool isImportantWord(string word){
		foreach (string w in getImportantWords()){
			if (w.Equals(word)) 
				return true;		
		}
		return false;
	}

        public bool isLinkingWord(string word){
		foreach (string w in getLinkingWords()){
			if (w.Equals(word)) 
				return true;		
		}
		return false;
	}


        public string[] bestWordList(int numWords)
        {
            SortedList<string, double[]> sortedWords = new SortedList<String, Double[]>(new Documento.ValueComparer(words));

         foreach(string s in words.Keys){
             if(!sortedWords.ContainsKey(s))
             sortedWords.Add(s,words[s]);
         }

            
            int counter = 0;
            string[] bestwords = new string[numWords];
            for (IEnumerator<string> it = sortedWords.Keys.GetEnumerator(); it.MoveNext() && (counter < numWords); counter++)
            {
                bestwords[counter] = it.Current;
            }
            return bestwords;
        }

        public string[] bestWordList()
        {
            return bestWordList(20);
        }

        public class ValueComparer : IComparer<string>
        {
		    public SortedList<string, double[]>  _data = null;

            public ValueComparer(SortedList<string, double[]> data)
                : base()
            {
			    _data = data;
		    }

             public int Compare(string o1, string o2) {
        	     double e1 = ((double[]) _data[o1])[1];
                 //Console.WriteLine("e1***********" + e1);

                 double e2 = ((double[]) _data[o2])[1];
                 //Console.WriteLine("e2***********" + e2);


                 if (e1 > e2) return -1;
                 if (e1 == e2) return 0;
                 if (e1 < e2) return 1;
                 return 0;
             }
	    }



        public void postProcesado(){
		    string[] allTokens;
		    string allWord;
		    int second;
		    string newSentence;

		    foreach (string s in getMostradas()){
			    allTokens = s.Split(':',';','\"','\'',',','.','[',']','{','}','(',')','!','?','-','/');


				    allWord= allTokens.First();

				    if (linkingWords.Contains(allWord)){
                        second = allSentences.IndexOf(s);
					    newSentence = allSentences[second-1];

					    if(!mostradas.Contains(newSentence)){
						    second = mostradas.IndexOf(s);
						    mostradas.Insert(second, newSentence);
					    }

				    }
		    }
	    }

        public void Porcentaje(int porcentaje){
		
		double num;
		double resultado;
		finalistas = new LinkedList<String>();
		mostradas = new List<String>();

		num =  getSentenceLema().Count;
        //Console.WriteLine("TOTAL SENTENCELEMAA----" + num);
		resultado = (num * porcentaje)/100;
       // Console.WriteLine("res----" + resultado);

    

		for (int i = 1 ; i <= resultado+1 ; i++){
			finalistas.AddLast(sentenceLema.Values.Last());
            sentenceLema.Remove(sentenceLema.Keys.Last());
		}
		
		foreach (string m in getSentences()){
			foreach (string f in getFinalistas()){
				if (m.Equals(f) && !mostradas.Contains(m)){
					mostradas.Add(m);
				}
			}
		}
	}
        ///////////////////////////////////

     public void obtenerFrases(string filename){

		string line;


		string[] tokens;
		string word;
		string[] tokens2;
		string onebyone;
		double lema;
		double[] temporal;
		int numWords;
		

		try {
			
            using (StreamReader br = new StreamReader(filename))
                {
                    line = br.ReadLine();

		
			while (line != null) {
                tokens = Regex.Split(line, @"\\.\\s");
				foreach (string t in tokens){
                    word = t;

                    if (!allSentences.Contains(word) && word.Length > 2)
                    {
                        allSentences.Add(word);
                     //   Console.WriteLine("line____" + t);

                    //    Console.WriteLine(word.Length + " cuantas van-------- " + allSentences.Count);
                       
                    }
                    lema = 0;
					tokens2= word.Split(' ');
					numWords = tokens2.Count();



                  
                    if(numWords > 1){
                      //  Console.WriteLine(" --numero palabras--- " + numWords);
                  int i = 1;
                    foreach (string r in tokens2)
                    {
                        
                        onebyone = r;
                        //Console.WriteLine(i + " palabra " + r);
                        
                        i++;
                        if (words.ContainsKey(onebyone))
                        {
                            temporal = words[onebyone];
                            if (temporal != null)
                                lema = lema + temporal[0];
                        }

                     
                        foreach (string w in this.bestWordList())
                        {
                            if (onebyone.Equals(w) || importantWords.Contains(onebyone) || titleWords.Contains(onebyone))
                            {
                                if (!sentences.Contains(word) && numWords > 4)
                                {
                                    sentences.AddLast(word);
                                    lema++;
                                }
                            }
                        }

                    }}

                  //  Console.WriteLine("---" + lema);
                    lema = lema / numWords;

                    if (!sentenceLema.ContainsKey(lema))
                    {

                        sentenceLema.Add(lema, word);
                    }

                    //Console.WriteLine(" LEMASS ----->>>>> " + word + "---" + lema );
  //                  Console.WriteLine("\n");

				}
				line = br.ReadLine();

			}

		}}
		catch (IOException e) {
            Console.WriteLine(e.StackTrace);
        }

	//	System.out.println("lematotal -> " + lemaTotal);

		}









     public IEnumerator GetEnumerator()
     {
         throw new NotImplementedException();
     }

    }
}
