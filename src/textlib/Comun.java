package textlib;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Comun {


public String Parseado(String dir, String patern) throws IOException{
	
	Document doc = Jsoup.connect(dir).get();
	Element content = doc.getElementById(patern);
	String text = content.text(); 	
	return text;
}

}