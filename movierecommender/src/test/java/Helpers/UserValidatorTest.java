package Helpers;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import movierecommender.Helpers.UserValidator;

public class UserValidatorTest {

    // *1) isValidUserName
    @Test
    public void isValidUserName_Alphabetic() {
        // !1) Arrange
        String userName = "Hassan Ali";

        // !2) Act
        boolean isValidUserName = UserValidator.isValidUserName(userName);

        // !3) Assert
        assertTrue(isValidUserName);
    }

    @Test
    public void isValidUserName_NonAlphabetic() {
        // !1) Arrange
        String userName = "Ha1ssan Ali";

        // !2) Act
        boolean isValidMovie = UserValidator.isValidUserName(userName);

        // !3) Assert
        assertFalse(isValidMovie);
    }

    @Test
    public void isValidMovieName_NullableNameShouldReturnFalse() {
        assertFalse(UserValidator.isValidUserName(null));
    }
    // *2)isValidUserID
}
