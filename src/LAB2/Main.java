package LAB2;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
public class Main {
    static ArrayList<String> stopWords = new ArrayList<String>();
    static ArrayList<String> exceptionWords = new ArrayList<String>();

    private static String getFileExtension(File file) {
        String fileName = file.getName();
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        else
            return "";
    }

    private static void scanDir(String path) {
        Document doc;
        Path dir = Paths.get(path);
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for (Path file : stream) {
                File f = file.toFile();
                if (f.isDirectory()) {
                    scanDir(f.getPath());
                } else {
                    File input = new File(f.getPath());
                    if (getFileExtension(input).equals("html")) {
                        System.out.println(f.getName());
                        doc = Jsoup.parse(input, "UTF-8", "");
                        Extract.getWords(input, doc, exceptionWords, stopWords);
                    }
                }
            }
        } catch (IOException | DirectoryIteratorException x) {
            System.err.println(x);
        }
    }

    public static void getExceptionWords() {
        File file = new File("exceptii.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                exceptionWords.add(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void getStopWords() {
        File file = new File("cuvinteStopate.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                stopWords.add(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String [] args) throws IOException {
       // Lab 2
        getExceptionWords();
        getStopWords();
        scanDir("input");
    }
}
