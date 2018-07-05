package wordsimilarity;

import wordsimilarity.SimilarityMeasure;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import opennlp.tools.stemmer.PorterStemmer;

public class TopJ {

    String wordInput;
    Integer length;
    HashMap<String, Double> output;

    public TopJ(String wordInput, String length) {
        PorterStemmer stemmer = new PorterStemmer();
        this.wordInput = stemmer.stem(wordInput);
        this.length = Integer.parseInt(length);
        output = new HashMap<>();
    }

    public static <K, V extends Comparable<? super V>>
            List entriesSortedByValues(Map<K, V> map, Integer max) {
        SortedSet<Map.Entry<K, V>> sortedEntries = new TreeSet<Map.Entry<K, V>>(
                new Comparator<Map.Entry<K, V>>() {
            @Override
            public int compare(Map.Entry<K, V> e1, Map.Entry<K, V> e2) {
                int res = -e1.getValue().compareTo(e2.getValue());
                return res != 0 ? res : 1;
            }
        }
        );
        sortedEntries.addAll(map.entrySet());
        List sub = new ArrayList(sortedEntries);
        if (sub.size() > max) {
            return sub.subList(0, max);
        } else {
            return sub.subList(0, sub.size());
        }
    }

    public void calculateJ(HashMap<String, HashMap<String, Integer>> input, SimilarityMeasure sim) {
        for (String f : input.keySet()) {
            ArrayList<Double> vectorB = new ArrayList();
            ArrayList<Double> vectorA = new ArrayList();
            for (String g : input.get(wordInput).keySet()) {
                vectorA.add(input.get(wordInput).get(g).doubleValue());
                if (input.get(f).containsKey(g)) {
                    vectorB.add(input.get(f).get(g).doubleValue());
                } else {
                    vectorB.add(0.0);
                }
            }
            if (!vectorA.equals(vectorB)) {
                output.put(f, sim.similarityFunction(vectorA, vectorB));
            }

        }

    }
}
