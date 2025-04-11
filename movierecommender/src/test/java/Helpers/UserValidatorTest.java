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

    // *2) isValidUserID
    @Test
    public void isValidUserID_NullableUserIDShouldReturnFalse() {
        assertFalse(UserValidator.isValidUserID(null));
    }

    @Test
    public void isValidUserID_IDLengthNot9ShouldReturnFalse() {
        assertFalse(UserValidator.isValidUserID("1234"));
        assertFalse(UserValidator.isValidUserID("12345678910"));
    }

    @Test
    public void isValidUserID_FirstCharacterIsNotADigitShouldReturnFalse() {
        assertFalse(UserValidator.isValidUserID("A12345678"));
    }

    @Test
    public void isValidUserID_9CharactersWithNoCharactersShouldReturnTrue() {
        assertTrue(UserValidator.isValidUserID("123456789"));
    }

    @Test
    public void isValidUserID_LastCharacterIsADigitShouldReturnTrue() {
        assertTrue(UserValidator.isValidUserID("12345678A"));
    }

    @Test
    public void isValidUserID_AlphabetCharacterInTheMiddleShouldReturnFalse() {
        assertFalse(UserValidator.isValidUserID("123A45678"));
    }
}
