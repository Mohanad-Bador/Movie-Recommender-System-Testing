package movierecommender.Mock;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

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
    private HashMap<String,Movie> availableMovies;
    
    public UserFileParserMock(HashMap<String,Movie> availableMovies) {
        this.scenario = SCENARIO_WITH_FAVORITES;
        this.availableMovies = availableMovies;
    }
    
    public UserFileParserMock(int scenario,HashMap<String,Movie> availableMovies) {
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
        users.put("1", new User("1", "User With No Favorites", new HashSet<>()));
        users.put("2", new User("2", "Another User With No Favorites", new HashSet<>()));
        return users;
    }
    
    public HashMap<String, User> createUsersWithFavorites() {
        HashMap<String, User> users = new HashMap<>();
        
        // User with 1 favorite movie
        Set<String> user1Favorites = new HashSet<>();
        //, availableMovies.get("Movie1")
        if (availableMovies.containsKey("1")) {
            user1Favorites.add("1");
        }

        users.put("1", new User("1", "User With One Favorite", user1Favorites));
        
        // User with 2 favorite movies
        Set<String> user2Favorites = new HashSet<>();
        if (availableMovies.containsKey("1")) {
            user2Favorites.add("1");
        }
        if (availableMovies.containsKey("2")) {
            user2Favorites.add("2");
        }
        users.put("2", new User("2", "User With Two Favorites", user2Favorites));
        
        // User with 3 favorite movies
        Set<String> user3Favorites = new HashSet<>();
        if (availableMovies.containsKey("1")) {
            user3Favorites.add("1");
        }
        if (availableMovies.containsKey("2")) {
            user3Favorites.add("2");
        }
        if (availableMovies.containsKey("3")) {
            user3Favorites.add("3");
        }
        users.put("3", new User("3", "User With Three Favorites", user3Favorites));
        
        return users;
    }
    
    public HashMap<String, User> createUsersWithAllFavorites() {
        HashMap<String, User> users = new HashMap<>();
        users.put("1", new User("1", "User With All Favorites", new HashSet<>(availableMovies.keySet())));
        
        return users;
    }
    
    public HashMap<String, User> createUsersWithNoMatchingGenres() {
        HashMap<String, User> users = new HashMap<>();
        
        // User with 1 non-matching genre movie
        HashSet<String> user1Favorites = new HashSet<>();
        user1Favorites.add("1");
        users.put("1", new User("1", "User With One Non-Matching Genre", user1Favorites));
        
        // User with 2 non-matching genre movies
        HashSet<String> user2Favorites = new HashSet<>();
        user2Favorites.add("1");
        user2Favorites.add("2");
        users.put("2", new User("2", "User With Two Non-Matching Genres", user2Favorites));
        
        // User with 3 non-matching genre movies
        HashSet<String> user3Favorites = new HashSet<>();
        user3Favorites.add("1");
        user3Favorites.add("2");
        user3Favorites.add("3");
        users.put("3", new User("3", "User With Three Non-Matching Genres", user3Favorites));
        
        return users;
    }

}