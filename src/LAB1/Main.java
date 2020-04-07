package LAB1;

import org.jsoup.nodes.Document;
import java.io.*;
import java.util.*;

public class Main {

    public static void main(String [] args) throws IOException {

       // Lab 1
        Extract myParser = new Extract();
        Document myDoc = myParser.connect();
        myParser.Parser(myDoc);

        HashMap<String, Integer> words = myParser.getHash("in.txt");
        PrintWriter writer = new PrintWriter("outHashMap.txt", "UTF-8");
        for (HashMap.Entry<String, Integer> word : words.entrySet())
        {
            writer.println(word.getKey() + " -> " + word.getValue());
        }
        writer.close();
        //test for branch 2
    }
}
