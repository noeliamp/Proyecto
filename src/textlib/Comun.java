package textlib;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Comun {


public String Parseado(String dir, String patern) throws IOException{
	
	Document doc = Jsoup.connect(dir).get();
	Element content = doc.getElementById(patern);
	
	if (content.children().first().tagName() == "p"){
	
		for (Element child : content.children()){
			if (child.tagName() != "p")
			child.remove();
			
		}
	}
		
	String text = content.text() ; 	
	return text;
}

}