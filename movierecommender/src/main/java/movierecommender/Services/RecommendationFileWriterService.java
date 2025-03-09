package movierecommender.Services;

import java.util.ArrayList;

import movierecommender.Contracts.IRecommendationFileWriter;
import movierecommender.Entities.Recommendation;

public class RecommendationFileWriterService implements IRecommendationFileWriter {

    @Override
    public void writeRecommendations(String filename, ArrayList<Recommendation> recommendations) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'writeRecommendations'");
    }

}
