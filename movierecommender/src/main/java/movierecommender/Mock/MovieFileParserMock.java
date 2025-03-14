package movierecommender.Mock;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import movierecommender.Contracts.IMovieFileParser;
import movierecommender.Entities.Movie;

public class MovieFileParserMock implements IMovieFileParser {
    
    // Test scenario constants
    public static final int SCENARIO_STANDARD_MOVIES = 0;
    public static final int SCENARIO_NO_MOVIES = 1;
    public static final int SCENARIO_DIFFERENT_GENRE_MOVIES = 2;
    public static final int SCENARIO_ALL_SAME_GENRE_MOVIES = 3;
    
    private int scenario;
    
    public MovieFileParserMock() {
        this.scenario = SCENARIO_STANDARD_MOVIES;
    }
    
    public MovieFileParserMock(int scenario) {
        this.scenario = scenario;
    }

    @Override
    public HashMap<String, Movie> parseMovies(String filename) {
        switch (scenario) {
            case SCENARIO_NO_MOVIES:
                return createNoMovies();
            case SCENARIO_DIFFERENT_GENRE_MOVIES:
                return createDifferentGenreMovies();
            case SCENARIO_ALL_SAME_GENRE_MOVIES:
                return createAllSameGenreMovies();
            case SCENARIO_STANDARD_MOVIES:
            default:
                return createStandardMovies();
        }
    }
    
    public static HashMap<String, Movie> createStandardMovies() {
        HashMap<String, Movie> movies = new HashMap<>();
        movies.put("Movie1", new Movie("Movie1", "1", new HashSet<>(Arrays.asList("Action", "Adventure"))));
        movies.put("Movie2", new Movie("Movie2", "2", new HashSet<>(Arrays.asList("Action", "Comedy"))));
        movies.put("Movie3", new Movie("Movie3", "3", new HashSet<>(Arrays.asList("Drama"))));
        movies.put("Movie4", new Movie("Movie4", "4", new HashSet<>(Arrays.asList("Action", "Drama"))));
        movies.put("Movie5", new Movie("Movie5", "5", new HashSet<>(Arrays.asList("Comedy", "Romance"))));
        movies.put("Movie6", new Movie("Movie6", "6", new HashSet<>(Arrays.asList("Horror", "Thriller"))));
        return movies;
    }
    
    public static HashMap<String, Movie> createNoMovies() {
        return new HashMap<>();
    }
    
    public static HashMap<String, Movie> createDifferentGenreMovies() {
        HashMap<String, Movie> movies = new HashMap<>();
        movies.put("Movie1", new Movie("Movie1", "1", new HashSet<>(Arrays.asList("Action"))));
        movies.put("Movie2", new Movie("Movie2", "2", new HashSet<>(Arrays.asList("Comedy"))));
        movies.put("Movie3", new Movie("Movie3", "3", new HashSet<>(Arrays.asList("Drama"))));
        movies.put("Movie4", new Movie("Movie4", "4", new HashSet<>(Arrays.asList("Horror"))));
        return movies;
    }
    
    public static HashMap<String, Movie> createAllSameGenreMovies() {
        HashMap<String, Movie> movies = new HashMap<>();
        movies.put("Movie1", new Movie("Movie1", "1", new HashSet<>(Arrays.asList("Action"))));
        movies.put("Movie2", new Movie("Movie2", "2", new HashSet<>(Arrays.asList("Action"))));
        movies.put("Movie3", new Movie("Movie3", "3", new HashSet<>(Arrays.asList("Action"))));
        movies.put("Movie4", new Movie("Movie4", "4", new HashSet<>(Arrays.asList("Action"))));
        return movies;
    }
}