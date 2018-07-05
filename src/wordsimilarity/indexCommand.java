package wordsimilarity;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import opennlp.tools.stemmer.*;

public class indexCommand {

    ArrayList<ArrayList> output;
    File filename;

    public indexCommand(String filename) {
        this.output = new ArrayList();
        this.filename = new File(filename);
    }

    public void reader() throws FileNotFoundException {
        Scanner fileScan = new Scanner(filename).useDelimiter("[!?.]+");
        PorterStemmer stemmer = new PorterStemmer();
        while (fileScan.hasNext()) {
            String[] sent = fileScan.next().replaceAll("\\r\\n|\\r|\\n", " ").replaceAll("[^-A-Za-zÀ-ÿ0-9' ]", "").toLowerCase().split("\\s+");
            ArrayList<String> additive = new ArrayList();
            for (int i = 0; i < sent.length; i++) {
                if (!((new Scanner(new File("stopwords.txt")).useDelimiter("\\Z").next()).contains(sent[i]))) {
                    additive.add(stemmer.stem(sent[i]));
                }
            }
            if (!additive.isEmpty()) {
                output.add(additive);
            }
        }
    }
}
