package textlib;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

/**
* Consumer class from RSS/Atom feed type.
*
* @author LuAuF
*/

public class Consumer {

SyndFeed feed;
TreeMap<String, String> entries;

/**
* Class constructor
*
* @param url: url path to consume
*/
public Consumer(String url) {
super();
try {
URL feedUrl = new URL(url);

SyndFeedInput input = new SyndFeedInput();
feed = input.build(new XmlReader(feedUrl));
entries = new TreeMap<String,String>();

} catch (Exception ex) {
ex.printStackTrace();
System.out.println("ERROR: "+ex.getMessage());
}
}

/**
* print method
* Scroll down the list of entries and displays the feed title, author and description
*/
void print () {


//feeds list
List entradas = new ArrayList();
entradas = feed.getEntries();

//list iterator
Iterator it = entradas.iterator();

while (it.hasNext()) {
SyndEntry entrada = (SyndEntry) it.next();

entries.put(entrada.getTitle(),entrada.getLink());

System.out.println("Titulo……: " + entrada.getTitle() );
System.out.println("Link……: " + entrada.getLink() );
System.out.println("\n");

}
}

public TreeMap<String, String> getEntries() {
	return entries;
}

}