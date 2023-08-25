package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;

public class Crawler {
    static HashSet<String> urlSet;
    static int MaxDepth = 2;
    Crawler() {
        urlSet = new HashSet<String>();
    }

    public void getPageTextAndLinks(String url, int depth){
        if(urlSet.contains(url)) {
            return;
        }
        if(depth>MaxDepth) {
            return;
        }
        if(urlSet.add(url)) {
            System.out.println(url);
        }
        depth++;

        try {
            //Parsing HTML object to JAVA Object
            Document document = Jsoup.connect(url).timeout(5000).get(); // this will able to connect the url less than 5000 milli sec (or) else it will move on to next link

            //Indexer work starts here
            Indexer indexer = new Indexer(document, url);
            System.out.println(document.title());
            Elements availableLinksOnPage = document.select("a[href]");
            for (Element currentLink : availableLinksOnPage) {
                getPageTextAndLinks(currentLink.attr("abs:href"), depth);
            }
        }
        catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }
    public static void main(String[] args) {
        Crawler crawler = new Crawler();
        crawler.getPageTextAndLinks("https://www.javatpoint.com/", 1);
    }
}