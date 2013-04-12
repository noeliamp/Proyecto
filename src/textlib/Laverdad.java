package textlib;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Laverdad {

public String ElpaisParser(String html) throws IOException{
	
	Document doc = Jsoup.connect(html).get();
	Element content = doc.getElementById("text");
	String text = content.text(); 
	
	return text;
}

}