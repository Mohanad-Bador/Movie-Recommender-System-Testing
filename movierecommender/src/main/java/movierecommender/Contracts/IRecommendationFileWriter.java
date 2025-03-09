package movierecommender.Contracts;

import java.util.ArrayList;

import movierecommender.Entities.Recommendation;

public interface IRecommendationFileWriter {
    public void writeRecommendations(String filename, ArrayList<Recommendation> recommendations);
}
