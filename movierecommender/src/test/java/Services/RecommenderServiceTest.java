package Services;

import java.util.HashMap;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import movierecommender.Entities.Movie;
import movierecommender.Entities.Recommendation;
import movierecommender.Entities.User;
import movierecommender.Mock.MovieFileParserMock;
import movierecommender.Mock.UserFileParserMock;
import movierecommender.Services.RecommenderService;

public class RecommenderServiceTest {
    /*  
        1. User Has No Favorite Movies
        2. User Has Favorite Movies but No Matching Genres
        3. User Has Favorite Movies with Matching Genres
        4. User Has All Available Movies as Favorites
        5. User Has the only available movie in this genre as a favorite
        6. No Available Movies
        7. No Available Users
     */ 
    @Test
    public void RecommenderServiceTest_NoFavouriteMovies_NoRecommendations() {       
        // Arrange
        MovieFileParserMock movieFileParserMock = new MovieFileParserMock();
        HashMap<String, Movie> movies = movieFileParserMock.parseMovies("movies.txt");

        UserFileParserMock userFileParserMock = new UserFileParserMock(UserFileParserMock.SCENARIO_NO_FAVORITES, movies);
        HashMap<String, User> users = userFileParserMock.parseUsers("users.txt");

        RecommenderService recommenderService = new RecommenderService();

        // Act
        for (User user : users.values()) {
            Recommendation recommendations = recommenderService.generateRecommendations(user, movies);
            // Assert
            assertEquals("No Recommendations", recommendations.getRecommendedMovies().get(0).getMovieName());
        }
    }

    @Test
    public void RecommenderServiceTest_FavouriteMoviesWithNoMatchingGenres_NoRecommendations() {       
        // Arrange
        MovieFileParserMock movieFileParserMock = new MovieFileParserMock(MovieFileParserMock.SCENARIO_DIFFERENT_GENRE_MOVIES);
        HashMap<String, Movie> movies = movieFileParserMock.parseMovies("movies.txt");

        UserFileParserMock userFileParserMock = new UserFileParserMock(UserFileParserMock.SCENARIO_NO_MATCHING_GENRES, movies);
        HashMap<String, User> users = userFileParserMock.parseUsers("users.txt");

        RecommenderService recommenderService = new RecommenderService();

        // Act
        for (User user : users.values()) {
            Recommendation recommendations = recommenderService.generateRecommendations(user, movies);
            // Assert
            assertEquals("No Recommendations", recommendations.getRecommendedMovies().get(0).getMovieName());
        }
    }

    @Test
    public void RecommenderServiceTest_FavouriteMoviesWithMatchingGenres_Recommendations() {       
        // Arrange
        MovieFileParserMock movieFileParserMock = new MovieFileParserMock();
        HashMap<String, Movie> movies = movieFileParserMock.parseMovies("movies.txt");
        UserFileParserMock userFileParserMock = new UserFileParserMock(UserFileParserMock.SCENARIO_WITH_FAVORITES, movies);
        HashMap<String, User> users = userFileParserMock.parseUsers("users.txt");
        RecommenderService recommenderService = new RecommenderService();

        // Act
        for (User user : users.values()) {
            Recommendation recommendations = recommenderService.generateRecommendations(user, movies);
            // Assert
            assertNotEquals("No Recommendations", recommendations.getRecommendedMovies().get(0).getMovieName());
        }
    }

    @Test
    public void RecommenderServiceTest_UserLovesAllMovies_NoRecommendations() {       
        // Arrange
        MovieFileParserMock movieFileParserMock = new MovieFileParserMock();
        HashMap<String, Movie> movies = movieFileParserMock.parseMovies("movies.txt");
        UserFileParserMock userFileParserMock = new UserFileParserMock(UserFileParserMock.SCENARIO_ALL_FAVORITES, movies);
        HashMap<String, User> users = userFileParserMock.parseUsers("users.txt");
        RecommenderService recommenderService = new RecommenderService();

        // Act
        for (User user : users.values()) {
            Recommendation recommendations = recommenderService.generateRecommendations(user, movies);
            // Assert
            assertEquals("No Recommendations", recommendations.getRecommendedMovies().get(0).getMovieName());
        }
    }
    
    @Test
    public void RecommenderServiceTest_UserHasAllMoviesOfFavaoriteGenre_NoRecommendations() {       
        // Arrange
        MovieFileParserMock movieFileParserMock = new MovieFileParserMock(MovieFileParserMock.SCENARIO_DIFFERENT_GENRE_MOVIES);
        HashMap<String, Movie> movies = movieFileParserMock.parseMovies("movies.txt");
        UserFileParserMock userFileParserMock = new UserFileParserMock(UserFileParserMock.SCENARIO_WITH_FAVORITES, movies);
        HashMap<String, User> users = userFileParserMock.parseUsers("users.txt");
        RecommenderService recommenderService = new RecommenderService();

        // Act
        for (User user : users.values()) {
            Recommendation recommendations = recommenderService.generateRecommendations(user, movies);
            // Assert
            assertEquals("No Recommendations", recommendations.getRecommendedMovies().get(0).getMovieName());
        }
    }
    
    @Test
    public void RecommenderServiceTest_NoAvailableMovies_ThrowNoAvailableMovies() {       
        // Arrange
        MovieFileParserMock movieFileParserMock = new MovieFileParserMock(MovieFileParserMock.SCENARIO_NO_MOVIES);
        HashMap<String, Movie> movies = movieFileParserMock.parseMovies("movies.txt");
        UserFileParserMock userFileParserMock = new UserFileParserMock(UserFileParserMock.SCENARIO_WITH_FAVORITES, movies);
        HashMap<String, User> users = userFileParserMock.parseUsers("users.txt");
        RecommenderService recommenderService = new RecommenderService();

        // Act
        for (User user : users.values()) {
            // Assert
            assertThrows(IllegalArgumentException.class, () -> recommenderService.generateRecommendations(user, movies));
        }
    }
    
    @Test
    public void RecommenderServiceTest_NoAvailableUser_ThrowNoAvailableUsers() {       
        // Arrange
        MovieFileParserMock movieFileParserMock = new MovieFileParserMock();
        HashMap<String, Movie> movies = movieFileParserMock.parseMovies("movies.txt");
        HashMap<String, User> users = new HashMap<>(); // Ensure users is initialized
        RecommenderService recommenderService = new RecommenderService();
        
        // Act & Assert
        if (users.isEmpty()) {
            // Test with null user when no users are available
            User nullUser = null;
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> recommenderService.generateRecommendations(nullUser, movies)
                );
                
                assertEquals("No User Available", exception.getMessage());
            }
        }

    @Test
    public void RecommenderServiceTest_AllMoviesOfTheSameGenre_Recommendations() {
        // Arrange
        MovieFileParserMock movieFileParserMock = new MovieFileParserMock(MovieFileParserMock.SCENARIO_ALL_SAME_GENRE_MOVIES);
        HashMap<String, Movie> movies = movieFileParserMock.parseMovies("movies.txt");
        UserFileParserMock userFileParserMock = new UserFileParserMock(UserFileParserMock.SCENARIO_WITH_FAVORITES, movies);
        HashMap<String, User> users = userFileParserMock.parseUsers("users.txt");
        RecommenderService recommenderService = new RecommenderService();

        // Act
        for (User user : users.values()) {
            // Assert
            int userMoviesCount = user.getfavoriteMoviesSet().size();
            assertEquals(movies.size() - userMoviesCount, recommenderService.generateRecommendations(user, movies).getRecommendedMovies().size());
            }
        }

    // WHITE BOX TESTING
    // Condition Coverage

    @Test
    public void testConditionCoverage_EmptyMovieGenres_NotRecommended() {
        // Arrange
        HashMap<String, Movie> movies = new HashMap<>();
        HashSet<String> actionGenre = new HashSet<>();

        actionGenre.add("Action");
        Movie movie1 = new Movie("Action Movie", "1", actionGenre);

        HashSet<String> horrorGenre = new HashSet<>();
        horrorGenre.add("Horror");
        Movie movie2 = new Movie("Horror Movie", "2", horrorGenre);
        
        movies.put("1", movie1);
        movies.put("2", movie2);
        
        User user = new User("testUser", "testUserEmail", new HashSet<>());
        user.getfavoriteMoviesSet().add("1");
        
        RecommenderService recommenderService = new RecommenderService();
        
        // Act
        Recommendation recommendations = recommenderService.generateRecommendations(user, movies);
        
        // Assert
        assertEquals(0, recommendations.getRecommendedMovies().stream()
                .filter(m -> m.getMovieID().equals("2")).count());
    }

    @Test
    public void testConditionCoverage_MovieAlreadyInFavorites_NoRecommendations() {
        // Arrange
        HashMap<String, Movie> movies = new HashMap<>();
        HashSet<String> actionGenre = new HashSet<>();

        actionGenre.add("Action");
        Movie movie1 = new Movie("Action Movie", "1", actionGenre);
        movies.put("1", movie1);
        
        User user = new User("testUser", "testUserEmail", new HashSet<>());
        user.getfavoriteMoviesSet().add("1");
        
        RecommenderService recommenderService = new RecommenderService();
        
        // Act
        Recommendation recommendations = recommenderService.generateRecommendations(user, movies);
        
        // Assert
        assertEquals("No Recommendations", recommendations.getRecommendedMovies().get(0).getMovieName());
    }

    @Test
    public void testConditionCoverage_MatchingGenreNotInFavorites_Recommended() {
        // Arrange
        HashMap<String, Movie> movies = new HashMap<>();
        HashSet<String> actionGenre = new HashSet<>();

        actionGenre.add("Action");
        
        Movie movie1 = new Movie("Action Movie 1", "1", actionGenre);
        Movie movie2 = new Movie("Action Movie 2", "2", actionGenre);
        
        movies.put("1", movie1);
        movies.put("2", movie2);
        
        User user = new User("testUser", "testUserEmail", new HashSet<>());
        user.getfavoriteMoviesSet().add("1");
        
        RecommenderService recommenderService = new RecommenderService();
        
        // Act
        Recommendation recommendations = recommenderService.generateRecommendations(user, movies);
        
        // Assert
        assertEquals(1, recommendations.getRecommendedMovies().size());
        assertEquals("2", recommendations.getRecommendedMovies().get(0).getMovieID());
    }
}