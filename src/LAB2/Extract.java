package LAB2;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import org.jsoup.nodes.Document;
class Extract {

    public static void getWords(File file, Document doc, ArrayList<String> exceptionWords,
                                ArrayList<String> stopWords) {
        try {
            HashMap<String, Integer> words = new HashMap<String, Integer>();
            HashMap<String, Integer> exceptions = new HashMap<String, Integer>();
            String text = doc.body().text();
            PrintWriter wordsFile = new PrintWriter(file.getAbsolutePath() + ".txt", "UTF-8");

            String temp = "";
            for (int i = 0; i < text.length(); i++) {

                char c = text.charAt(i);
                if (Character.isLetter(c) || Character.isDigit(c)) {
                    temp += c;
                } else {
                    if (!temp.equals("")) {
                        if (!stopWords.contains(temp.toLowerCase())) {
                            if (exceptionWords.contains(temp.toLowerCase())) {
                                if (exceptions.get(temp) != null) {
                                    exceptions.put(temp, exceptions.get(temp) + 1);
                                } else {
                                    exceptions.put(temp, 1);
                                }
                            } else {
                                if (words.get(temp) != null) {
                                    words.put(temp, words.get(temp) + 1);
                                } else {
                                    words.put(temp, 1);
                                }
                            }
                        }
                    }
                    temp = "";
                }
                if (i == text.length()) {
                    if (!temp.equals("")) {
                        if (words.get(temp) != null) {
                            words.put(temp, words.get(temp) + 1);
                        } else {
                            if (!stopWords.contains(temp.toLowerCase())) {
                                words.put(temp, 1);
                            }
                        }
                    }
                }
            }

            Set<String> keys = words.keySet();
            for (String key : keys) {
                wordsFile.println(key + " : " + words.get(key));
            }
            wordsFile.println("\nExceptions:");
            keys = exceptions.keySet();
            for (String key : keys) {
                wordsFile.println(key + " : " + exceptions.get(key));
            }
            wordsFile.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}