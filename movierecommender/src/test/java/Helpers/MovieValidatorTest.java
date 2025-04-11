package Helpers;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

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
}
