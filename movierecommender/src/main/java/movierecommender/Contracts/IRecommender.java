package movierecommender.Contracts;

import java.util.ArrayList;
import java.util.HashMap;

import movierecommender.Entities.Movie;
import movierecommender.Entities.Recommendation;
import movierecommender.Entities.User;

public interface IRecommender {
    public ArrayList<Recommendation> generateRecommendations(User user,HashMap<String,Movie> movies);
}
