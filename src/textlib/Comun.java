package textlib;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Comun {


public String Parseado(String html, String patern) throws IOException{
	
	Document doc = Jsoup.connect("http://elmundo.feedsportal.com/c/32791/f/532779/s/2aa33e56/l/0L0Selmundo0Bes0Celmundo0C20A130C0A40C120Cinternacional0C13657619240Bhtml/story01.htm").get();
	Element content = doc.getElementById(patern);
	String text = content.text(); 
	System.out.println(text);
	
	return text;
}

}