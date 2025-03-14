package movierecommender.Mock;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import movierecommender.Contracts.IUserFileParser;
import movierecommender.Entities.Movie;
import movierecommender.Entities.User;

public class UserFileParserMock implements IUserFileParser {
    
    // Test scenario constants
    public static final int SCENARIO_NO_FAVORITES = 0;
    public static final int SCENARIO_WITH_FAVORITES = 1;
    public static final int SCENARIO_ALL_FAVORITES = 2;
    public static final int SCENARIO_NO_MATCHING_GENRES = 3;
    
    private int scenario;
    private HashMap<String, Movie> availableMovies;
    
    public UserFileParserMock(HashMap<String, Movie> availableMovies) {
        this.scenario = SCENARIO_WITH_FAVORITES;
        this.availableMovies = availableMovies;
    }
    
    public UserFileParserMock(int scenario, HashMap<String, Movie> availableMovies) {
        this.scenario = scenario;
        this.availableMovies = availableMovies;
    }

    @Override
    public HashMap<String, User> parseUsers(String filename) {
        switch (scenario) {
            case SCENARIO_NO_FAVORITES:
                return createUsersWithNoFavorites();
            case SCENARIO_ALL_FAVORITES:
                return createUsersWithAllFavorites();
            case SCENARIO_NO_MATCHING_GENRES:
                return createUsersWithNoMatchingGenres();
            case SCENARIO_WITH_FAVORITES:
            default:
                return createUsersWithFavorites();
        }
    }
    
    public HashMap<String, User> createUsersWithNoFavorites() {
        HashMap<String, User> users = new HashMap<>();
        users.put("1", new User("1", "User With No Favorites", new HashMap<>()));
        users.put("2", new User("2", "Another User With No Favorites", new HashMap<>()));
        return users;
    }
    
    public HashMap<String, User> createUsersWithFavorites() {
        HashMap<String, User> users = new HashMap<>();
        
        // User with 1 favorite movie
        HashMap<String, Movie> user1Favorites = new HashMap<>();
        if (availableMovies.containsKey("Movie1")) {
            user1Favorites.put("Movie1", availableMovies.get("Movie1"));
        }
        users.put("1", new User("1", "User With One Favorite", user1Favorites));
        
        // User with 2 favorite movies
        HashMap<String, Movie> user2Favorites = new HashMap<>();
        if (availableMovies.containsKey("Movie1")) {
            user2Favorites.put("Movie1", availableMovies.get("Movie1"));
        }
        if (availableMovies.containsKey("Movie2")) {
            user2Favorites.put("Movie2", availableMovies.get("Movie2"));
        }
        users.put("2", new User("2", "User With Two Favorites", user2Favorites));
        
        // User with 3 favorite movies
        HashMap<String, Movie> user3Favorites = new HashMap<>();
        if (availableMovies.containsKey("Movie1")) {
            user3Favorites.put("Movie1", availableMovies.get("Movie1"));
        }
        if (availableMovies.containsKey("Movie2")) {
            user3Favorites.put("Movie2", availableMovies.get("Movie2"));
        }
        if (availableMovies.containsKey("Movie3")) {
            user3Favorites.put("Movie3", availableMovies.get("Movie3"));
        }
        users.put("3", new User("3", "User With Three Favorites", user3Favorites));
        
        return users;
    }
    
    public HashMap<String, User> createUsersWithAllFavorites() {
        HashMap<String, User> users = new HashMap<>();
        users.put("1", new User("1", "User With All Favorites", new HashMap<>(availableMovies)));
        
        return users;
    }
    
    public HashMap<String, User> createUsersWithNoMatchingGenres() {
        HashMap<String, User> users = new HashMap<>();
        
        // User with 1 non-matching genre movie
        HashMap<String, Movie> user1Favorites = new HashMap<>();
        user1Favorites.put("DifferentGenre1", new Movie("DifferentGenre1", "991", 
            new HashSet<>(Arrays.asList("Western"))));
        users.put("1", new User("1", "User With One Non-Matching Genre", user1Favorites));
        
        // User with 2 non-matching genre movies
        HashMap<String, Movie> user2Favorites = new HashMap<>();
        user2Favorites.put("DifferentGenre1", new Movie("DifferentGenre1", "991", 
            new HashSet<>(Arrays.asList("Western"))));
        user2Favorites.put("DifferentGenre2", new Movie("DifferentGenre2", "992", 
            new HashSet<>(Arrays.asList("Musical"))));
        users.put("2", new User("2", "User With Two Non-Matching Genres", user2Favorites));
        
        // User with 3 non-matching genre movies
        HashMap<String, Movie> user3Favorites = new HashMap<>();
        user3Favorites.put("DifferentGenre1", new Movie("DifferentGenre1", "991", 
            new HashSet<>(Arrays.asList("Western"))));
        user3Favorites.put("DifferentGenre2", new Movie("DifferentGenre2", "992", 
            new HashSet<>(Arrays.asList("Musical"))));
        user3Favorites.put("DifferentGenre3", new Movie("DifferentGenre3", "993", 
            new HashSet<>(Arrays.asList("Documentary"))));
        users.put("3", new User("3", "User With Three Non-Matching Genres", user3Favorites));
        
        return users;
    }

}