package LAB1;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.HashMap;

class Extract {
    Document connect()
    {
        Document Doc = null;
        try {
            Doc = Jsoup.connect("https://en.wikipedia.org/wiki/Romanian_Wikipedia").get();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("connection failed!");
        }
        return Doc;
    }

    void Parser(Document doc) throws FileNotFoundException, UnsupportedEncodingException {
        String title;
        title = doc.title();

        Elements keywords = doc.getElementsByTag("META").select("[name=keywords]");
        Elements description = doc.getElementsByTag("META").select("[name=description]");
        Elements robots = doc.getElementsByTag("META").select("[name=robots]");
        String text = doc.body().text();
        Elements links = doc.select("a[href]");

        PrintWriter writer = new PrintWriter("outExtract.txt", "UTF-8");
        writer.println("title: " + title);
        writer.println("keywords: "+keywords.attr("content"));
        writer.println("\n");
        writer.println("description: "+description.attr("content"));
        writer.println("\n");
        writer.println("robots: "+robots.attr("content"));
        writer.println("\n");
        writer.println("text: "+ text );
        writer.println("\n");
        writer.println("links "+ links );
    }

    public HashMap<String, Integer> getHash(String fileName) throws IOException
    {
        HashMap<String, Integer> output = new HashMap<String, Integer>();

        FileReader inputStream = new FileReader(fileName);
        StringBuilder sb = new StringBuilder();
        int c;
        while ((c = inputStream.read()) != -1)
        {
            if (!Character.isLetterOrDigit((char)c))
            {
                String newWord = sb.toString();
                if (output.containsKey(newWord))
                {
                    output.put(newWord, output.get(newWord) + 1);
                }
                else
                {
                    output.put(newWord, 1);
                }
                sb.setLength(0);
            }
            else
            {
                sb.append((char)c);
            }
        }
        output.remove("");
        inputStream.close();
        return output;
    }
}