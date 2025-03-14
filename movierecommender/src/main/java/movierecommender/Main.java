package movierecommender;

import java.util.HashMap;
import java.util.HashSet;

import movierecommender.Contracts.IRecommender;
import movierecommender.Entities.Movie;
import movierecommender.Entities.Recommendation;
import movierecommender.Entities.User;
import movierecommender.Services.RecommenderService;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        Movie movie1 = new Movie("Movie1", "1", new HashSet<String>() {{
            add("Action");
            add("Adventure");
        }});
        Movie movie2 = new Movie("Movie2", "2", new HashSet<String>() {{
            add("Action");
            add("Comedy");
        }});
        Movie movie3 = new Movie("Movie3","3", new HashSet<String>() {{
            add("Drama");
        }});
        Movie movie4 = new Movie("Movie4","4", new HashSet<String>() {{
            add("Action");
            add("Drama");
        }});



        // Create a user and set favorite movies
        User user = new User("1", "Hamada", new HashMap<String,Movie>(){{
            put("Movie1", movie1);
        }});

        // Create a map of all movies
        HashMap<String, Movie> movies = new HashMap<>();
        movies.put("Movie1", movie1);
        movies.put("Movie2", movie2);
        movies.put("Movie3", movie3);
        movies.put("Movie4", movie4);

        // Create the recommender service and generate recommendations
        IRecommender recommender = new RecommenderService();
        Recommendation recommendations = recommender.generateRecommendations(user, movies);

        // Print the recommendations
        System.out.println( recommendations.toString());
       
    }
}