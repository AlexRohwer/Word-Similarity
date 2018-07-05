package wordsimilarity;

import wordsimilarity.SimilarityMeasure;
import java.util.ArrayList;

public class EuclideanDistance implements SimilarityMeasure {
    @Override
    public double similarityFunction(ArrayList<Double> vectorA, ArrayList<Double> vectorB){
        double sigma = 0.0;
        for(int i=0;i<vectorA.size();i++){
            sigma += Math.pow((vectorA.get(i)-vectorB.get(i)),2.0);
        }
        return -1 * Math.sqrt(sigma);
    }
}
