package wordsimilarity;

import wordsimilarity.CosineSimilarity;
import wordsimilarity.EuclideanDistance;
import wordsimilarity.EuclideanNorm;
import wordsimilarity.KMeans;
import wordsimilarity.SimilarityMeasure;
import wordsimilarity.TopJ;
import wordsimilarity.Vector;
import wordsimilarity.indexCommand;
import static wordsimilarity.TopJ.entriesSortedByValues;
import java.io.*;
import opennlp.tools.stemmer.PorterStemmer;

public class Main {

    private static void printMenu() {
        System.out.println("Supported commands:");
        System.out.println("help - Print the supported commands");
        System.out.println("quit - Quit this program");
        System.out.println("index FILE - Read in and index the file given by FILE");
        System.out.println("sentences - Prints the list of sentences of the index");
        System.out.println("Num sentences - Prints the length of the list of sentences of the index");
        System.out.println("vectors - Prints words of the index and the # of times other words occured in the same sentence");
        System.out.println("topj - Prints the top J similar words to the input word");
        System.out.println("measure <cosine, euc, or eucnorm> - sets similarity measure");
    }

    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        indexCommand current = null;
        Vector currentVector = null;
        SimilarityMeasure sim = new CosineSimilarity();
        KMeans finale = null;

        while (true) {
            System.out.print("> ");
            String command = input.readLine();
            if (command.equals("help") || command.equals("h")) {
                printMenu();
            } else if (command.equals("quit")) {
                System.exit(0);
            } else if (command.length() > 5 && command.substring(0, 6).equals("index ")) {
                current = new indexCommand(command.substring(6));
                current.reader();
                if (currentVector == null) {
                    currentVector = new Vector(current.output);
                } else {
                    currentVector.updateVector(new Vector(current.output));
                }
                System.out.println("Indexing " + command.substring(6));
            } else if (command.equals("sentences")) {
                System.out.println(current.output);
            } else if (command.equals("Num sentences")) {
                System.out.println(current.output.size());
            } else if (command.equals("vectors")) {
                System.out.println(currentVector.printVector());
            } else if (command.substring(0, 5).equals("topj ")) {
                PorterStemmer stemmer = new PorterStemmer();
                String[] splitted = command.split("\\s+");
                if (currentVector.vector.containsKey(stemmer.stem(splitted[1]))) {
                    TopJ test = new TopJ(splitted[1], splitted[2]);
                    test.calculateJ(currentVector.vector, sim);
                    System.out.println(entriesSortedByValues(test.output, test.length));
                } else {
                    System.out.println("Cannot compute top-J similarity to " + splitted[1]);
                }
            } else if (command.substring(0, 8).equals("measure ")) {
                String[] splitted = command.split("\\s+");
                if (splitted[1].equals("cosine")) {
                    sim = new CosineSimilarity();
                    System.out.println("Similarity measure is cosine similarity");
                } else if (splitted[1].equals("euc")) {
                    sim = new EuclideanDistance();
                    System.out.println("Similarity measure is negative euclidean distance");
                } else if (splitted[1].equals("eucnorm")) {
                    sim = new EuclideanNorm();
                    System.out.println("Similarity measure is negative euclidean distance between norms");
                } else {
                    System.out.println("unrecognized similarity measure - recognized measure are: cosine, euc, eucnorm");
                }
            } else if (command.substring(0, 7).equals("kmeans ")) {
                String[] splitted = command.split("\\s+");
                finale = new KMeans(splitted[1], splitted[2], currentVector.vector);
                finale.meansCalculator();
                for (int j=0; j<finale.clusterValues.size(); j++) {
                    Double sum = 0.0;
                    for (int v=0; v<finale.clusterValues.get(j).size(); v++) {
                        sum = sum + finale.clusterValues.get(j).get(v);
                    }
                    System.out.println("Iteration " + j + ": " + (sum/finale.clusterValues.get(j).size()));
                }
                for (int i=0; i<finale.clusterStrings.size(); i++) {
                    System.out.println("Cluster " + i);
                    System.out.println(finale.clusterStrings.get(i));
                }
            } else {
                System.err.println("Unrecognized command");
            }
        }
    }
}
