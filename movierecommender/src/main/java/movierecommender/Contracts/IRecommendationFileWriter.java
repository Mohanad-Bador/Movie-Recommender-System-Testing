package movierecommender.Contracts;

import java.util.ArrayList;

import movierecommender.Entities.Recommendation;
import movierecommender.Errors.AppError;

public interface IRecommendationFileWriter {
    public void writeRecommendations(ArrayList<Recommendation> recommendations) throws AppError;
}
