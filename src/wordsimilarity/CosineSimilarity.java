package wordsimilarity;

import wordsimilarity.SimilarityMeasure;
import java.util.ArrayList;

public class CosineSimilarity implements SimilarityMeasure {

    @Override
    public double similarityFunction(ArrayList<Double> vectorA, ArrayList<Double> vectorB) {
        double dotProduct = 0.0;
        double normA = 0.0;
        double normB = 0.0;
        for (int i = 0; i < vectorA.size(); i++) {
            dotProduct += vectorA.get(i) * vectorB.get(i);
            normA += Math.pow(vectorA.get(i), 2);
            normB += Math.pow(vectorB.get(i), 2);
        }
        if (normA > 0 && normB > 0) {
            return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
        } else {
            return 0.0;
        }
    }
}
