package wordsimilarity;

import wordsimilarity.SimilarityMeasure;
import wordsimilarity.EuclideanDistance;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class KMeans {

    Integer means;
    Integer iterations;
    Integer vectorsize;
    HashMap<String, HashMap<String, Integer>> input;
    ArrayList<ArrayList<Double>> points;
    ArrayList<ArrayList<String>> clusterStrings;
    ArrayList<ArrayList<Double>> clusterValues;

    public KMeans(String means, String iterations, HashMap<String, HashMap<String, Integer>> input) {
        this.means = Integer.parseInt(means);
        this.iterations = Integer.parseInt(iterations);
        this.vectorsize = input.size();
        this.input = input;
        clusterStrings = new ArrayList<>();
        clusterValues = new ArrayList<>();
        points = new ArrayList<>();
        for (int i = 0; i < Integer.parseInt(means); i++) {
            ArrayList<Double> temp = new ArrayList<>();
            for (int j = 0; j < vectorsize; j++) {
                Random r = new Random();
                temp.add(r.nextDouble() * 1.0);
            }
            points.add(temp);
        }
    }

    public void meansCalculator() {
        for (int i = 0; i < iterations; i++) {
            clusterValues.add(new ArrayList());
            clusterStrings = new ArrayList<>();
            for (int j = 0; j < points.size(); j++) {
                clusterStrings.add(new ArrayList());
            }

            for (String g : input.keySet()) {
                ArrayList<Double> vectorB = new ArrayList();
                for (String h : input.keySet()) {
                    if (input.get(g).containsKey(h)) {
                        vectorB.add(input.get(g).get(h).doubleValue());
                    } else {
                        vectorB.add(0.0);
                    }
                }
                SimilarityMeasure sim = new EuclideanDistance();
                Double min = sim.similarityFunction(points.get(0), vectorB);
                Integer index = 0;
                for (int z = 1; z < points.size(); z++) {
                    if (min < sim.similarityFunction(points.get(z), vectorB)) {
                        min = sim.similarityFunction(points.get(z), vectorB);
                        index = z;
                    }
                }
                clusterStrings.get(index).add(g);
                clusterValues.get(i).add(min);
            }
            points = new ArrayList();
            for (int x = 0; x < means; x++) {
                ArrayList<Double> added = new ArrayList<>();
                for (String y : input.keySet()) {
                    Double sum = 0.0;
                    for (int z = 0; z < clusterStrings.get(x).size(); z++) {
                        if (input.get(clusterStrings.get(x).get(z)).containsKey(y)) {
                            sum = +input.get(clusterStrings.get(x).get(z)).get(y).doubleValue();
                        } else {
                            sum = +0.0;
                        }
                    }
                    added.add(sum / 3);
                }
                points.add(added);
            } 
        }
    }
}
