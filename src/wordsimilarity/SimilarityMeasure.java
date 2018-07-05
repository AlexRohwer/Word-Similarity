package wordsimilarity;

import java.util.ArrayList;

public interface SimilarityMeasure {
    double similarityFunction(ArrayList<Double> vectorA, ArrayList<Double> vectorB);
}
