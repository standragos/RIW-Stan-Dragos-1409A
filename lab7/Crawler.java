import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.LinkedList;

class Crawler {

    private LinkedList<String> queue = new LinkedList<>();
    private LinkedList<String> apparitions = new LinkedList<>();

    void Crawl() throws IOException {

        HttpClient httpClient = new HttpClient();
        int savedPages = 0;
        queue.add("/crawl/");
        String host = "riweb.tibeica.com";

        Document doc = Jsoup.connect("http://" + host + queue.peek()).get();
        Elements links = doc.select("a[href]"); //get all href elements from the above link

        httpClient.saveHtml(host, queue.peek());
        queue.pop(); // remove crawl from queue

        for (Element link : links){
            //System.out.println(link.attr("href"));
            //String removedHtml = link.attr("href").replace(".html", "");
            // make set out of the apparitions queue so links dont repeat
            if(!apparitions.contains(link.attr("href"))){
                apparitions.add(link.attr("href")); // add links to list of all hrefs
                System.out.println("added " + link.attr("href") + " to the apparitions q");
                queue.add("/crawl/" + link.attr("href")); // add to queue all hrefs from crawl
            }
        }

        while(!queue.isEmpty() && savedPages < 300)
        {
            httpClient.saveHtml(host, queue.peek());

            savedPages++;

            try{
                doc = Jsoup.connect("http://" + host + queue.peek()).get();
            }
            catch (Exception ex)
            {
                System.out.print(ex);
            }

            queue.pop();
            links = doc.select("a[href]");

            for (Element link : links){
                //System.out.println(link.attr("href"));
                //String removedHtml = link.attr("href").replace(".html", "");

                if(!(apparitions.contains(link.attr("href")))) {
                    queue.add("/crawl/" + link.attr("href"));
                    apparitions.add(link.attr("href"));
                    System.out.println("added " + link.attr("href") + " to the apparitions q in WHILE");
                }
            }
        }
        System.out.println("Numarul de pagini salvate: " + savedPages);
    }
}
