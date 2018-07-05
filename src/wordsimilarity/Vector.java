package wordsimilarity;

import java.util.HashMap;
import java.util.Map;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Iterator;
import opennlp.tools.stemmer.PorterStemmer;

public class Vector {

    private HashSet<String> wordSet = new HashSet<>();
    public HashMap<String, HashMap<String, Integer>> vector = new HashMap<>();

    public Vector(ArrayList<ArrayList> a) {
        
        //adds all words to wordSet    
        for (ArrayList currentList : a) {
            for (Object o : currentList) {
                String word = (String) o;
                wordSet.add(word);
            }
        }
        
        //adds all words to vector with word counts
        for (String s : wordSet) {
            vector.put(s, new HashMap<String,Integer>());
            for (ArrayList currentList : a) {
                if (currentList.contains(s)) {
                    for (Object o : currentList) {
                        String word = (String) o;
                        if (!word.equals(s)) {
                            HashMap<String,Integer> index = vector.get(s);
                            if(index.containsKey(word)){
                                vector.get(s).put(word, vector.get(s).get(word) + 1);
                            }
                            else{
                                vector.get(s).put(word, 1);
                            }
                        }
                    }
                }
            }
        }
    }

    public void updateVector(Vector v) {
        for (Map.Entry<String, HashMap<String, Integer>> entry : v.vector.entrySet()) {
            String currentWord = entry.getKey();
            HashMap<String, Integer> wordCounts = entry.getValue();
            if (this.vector.containsKey(currentWord)) {
                HashMap<String, Integer> valueToModify = this.vector.get(currentWord);
                for (Map.Entry<String, Integer> e : wordCounts.entrySet()) {
                    String word = e.getKey();
                    Integer num = e.getValue();
                    if (valueToModify.containsKey(word)) {
                        valueToModify.put(word, valueToModify.get(word) + num);
                    } else {
                        valueToModify.put(word, num);
                    }
                }
            } else {
                this.vector.put(currentWord, new HashMap<String, Integer>());
                HashMap<String, Integer> valueToModify = this.vector.get(currentWord);
                for (Map.Entry<String, Integer> e : wordCounts.entrySet()) {
                    String word = e.getKey();
                    Integer num = e.getValue();
                    valueToModify.put(word, num);
                }
            }
        }
    }

    public String printVector() {
        String result = "{";
        for (Map.Entry<String, HashMap<String, Integer>> entry : vector.entrySet()) {
            String currentWord = entry.getKey();
            HashMap<String, Integer> wordCounts = entry.getValue();
            result += currentWord + "={{";
            for (Map.Entry<String, Integer> e : wordCounts.entrySet()) {
                String someWord = e.getKey();
                Integer someNum = e.getValue();
                if (someNum != 0) {
                    result += someWord + "=" + someNum + ", ";
                }
            }
            result += "}}, ";
        }
        return result + "}";
    }
}
