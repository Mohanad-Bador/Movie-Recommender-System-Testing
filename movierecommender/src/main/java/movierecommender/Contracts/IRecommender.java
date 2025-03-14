package movierecommender.Contracts;

import java.util.HashMap;

import movierecommender.Entities.Movie;
import movierecommender.Entities.Recommendation;
import movierecommender.Entities.User;

public interface IRecommender {
    public Recommendation generateRecommendations(User user,HashMap<String,Movie> movies);
}
