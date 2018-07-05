package wordsimilarity;

import wordsimilarity.SimilarityMeasure;
import java.util.ArrayList;

public class EuclideanNorm implements SimilarityMeasure {
    public double similarityFunction(ArrayList<Double> vectorA, ArrayList<Double> vectorB) {
        double magA = 0.0;
        double magB = 0.0;
        double sigma = 0.0;
        for(int i=0;i<vectorA.size();i++){
            magA += Math.pow(vectorA.get(i),2.0);
            magB += Math.pow(vectorB.get(i),2.0);
        }
        magA = Math.sqrt(magA);
        magB = Math.sqrt(magB);
        for(int i=0;i<vectorA.size();i++){
            if(magA != 0 && magB != 0) {
                sigma += Math.pow(((vectorA.get(i)/magA)-(vectorB.get(i)/magB)),2.0);
            } else if(magA != 0 && magB == 0){
                sigma += Math.pow(((vectorA.get(i)/magA)-(vectorB.get(i))),2.0);
            } else if(magA == 0 && magB != 0){
                sigma += Math.pow(((vectorA.get(i))-(vectorB.get(i)/magB)),2.0);
            } else {
                sigma += Math.pow(((vectorA.get(i))-(vectorB.get(i))),2.0);
            }
        }
        return -1 * Math.sqrt(sigma);
    }
}
