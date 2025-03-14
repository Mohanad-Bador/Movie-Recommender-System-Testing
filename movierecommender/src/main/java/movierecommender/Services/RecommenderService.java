package movierecommender.Services;

import java.util.HashMap;

import movierecommender.Contracts.IRecommender;
import movierecommender.Entities.Movie;
import movierecommender.Entities.Recommendation;
import movierecommender.Entities.User;

public class RecommenderService implements IRecommender {

    @Override
    public Recommendation generateRecommendations(User user,HashMap<String,Movie> movies) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'generateRecommendations'");
    }

}
