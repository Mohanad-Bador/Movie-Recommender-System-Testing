package Helpers;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.*;
import org.junit.jupiter.api.Test;

import movierecommender.Entities.Movie;
import movierecommender.Helpers.MovieValidator;

public class MovieValidatorTest {

    // *1) isValidMovieName
    @Test
    public void isValidMovieName_PascalCaseShouldReturnTrue() {
        // !1) Arrange
        String movieName = "The Dark Knight";

        // !2) Act
        boolean isValidMovie = MovieValidator.isValidMovieName(movieName);

        // !3) Assert
        assertTrue(isValidMovie);
    }

    @Test
    public void isValidMovieName_NonPascalCaseShouldReturnFalse() {
        // !1) Arrange
        String movieName = "The dark Knight";

        // !2) Act
        boolean isValidMovie = MovieValidator.isValidMovieName(movieName);

        // !3) Assert
        assertFalse(isValidMovie);
    }

    @Test
    public void isValidMovieName_NullableNameShouldReturnFalse() {
        assertFalse(MovieValidator.isValidMovieName(null));
    }

    // *2) isValidMovieID
    @Test
    public void isValidMovieID_AllUppercaseShouldReturnTrue() {
        assertTrue(MovieValidator.isValidMovieID("APV123"));
    }

    @Test
    public void isValidMovieID_OneLowercaseShouldReturnFalse() {
        assertFalse(MovieValidator.isValidMovieID("APv123"));
    }

    @Test
    public void isValidMovieID_ThreeUniqueNumsShouldReturnTrue() {
        assertTrue(MovieValidator.isValidMovieID("APV235"));
    }

    @Test
    public void isValidMovieID_MoreThanThreeNumsShouldReturnFalse() {
        assertFalse(MovieValidator.isValidMovieID("APV1234"));
    }

    // *3) isUniqueIDNumbers
    @Test
    public void isUniqueIDNumbers_NonUniqueIDNumbersShouldReturnFalse() {
        HashMap<String, Movie> movies = new HashMap<>();
        movies.put("KDA123", new Movie("I dunno", "KDA123", new HashSet<>()));
        assertFalse(MovieValidator.isUniqueIDNumbers("APV123", movies));
    }

    @Test
    public void isUniqueIDNumbers_UniqueIDNumbersShouldReturnTrue() {
        HashMap<String, Movie> movies = new HashMap<>();
        movies.put("KDA124", new Movie("I dunno", "KDA124", new HashSet<>()));
        movies.put("NJK120", new Movie("Maybe", "NJK120", new HashSet<>()));
        movies.put("NCV151", new Movie("I dunno", "NCV151", new HashSet<>()));
        assertTrue(MovieValidator.isUniqueIDNumbers("APV123", movies));
    }
}