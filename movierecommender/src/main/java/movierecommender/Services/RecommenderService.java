package movierecommender.Services;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import movierecommender.Contracts.IRecommender;
import movierecommender.Entities.Movie;
import movierecommender.Entities.Recommendation;
import movierecommender.Entities.User;
public class RecommenderService implements IRecommender {

    @Override
    public Recommendation generateRecommendations(User user, HashMap<String, Movie> movies) {
        if (movies.isEmpty()){
            throw new IllegalArgumentException("No Movies Available");
        }
        if (user == null){
            throw new IllegalArgumentException("No User Available");
        }
        Recommendation recommendations = new Recommendation(user, new ArrayList<>());
        // Case 1: Guard Clause: No Favourite Movies
        if (user.getfavoriteMoviesDict().isEmpty()){
            recommendations.getRecommendedMovies().add(new Movie("No Recommendations", "0", new HashSet<>()));
            return recommendations;
        }
        HashMap<String,Movie> userMovies = user.getfavoriteMoviesDict();
        // !1.Generate Favourite Genres
        Set<String> favouritGenres = new HashSet<>();
        for (Movie movie : userMovies.values()){
            favouritGenres.addAll(movie.getGenres());
        }
        
        // !2. Generate Recommendations
        for (Movie movie : movies.values()){
            Set<String> movieGenres = new HashSet<>(movie.getGenres());
            movieGenres.retainAll(favouritGenres);
            if(!movieGenres.isEmpty() && !userMovies.values().contains(movie)){
                recommendations.getRecommendedMovies().add(movie);
            }
        }

        // Case 2: No Available Recommendations
        if (recommendations.getRecommendedMovies().isEmpty()){
            recommendations.getRecommendedMovies().add(new Movie("No Recommendations", "0", new HashSet<>()));
        }
        return recommendations;
    }

}
