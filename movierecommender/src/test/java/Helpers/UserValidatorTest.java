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

    // Path Coverage
    @Test
    public void testPath_NullOrEmpty() {
        // Path: 2→3
        assertFalse(UserValidator.isValidUserName(null));
        assertFalse(UserValidator.isValidUserName(""));
    }

    @Test
    public void testPath_StartsWithSpace() {
        // Path: 2→4→5→6
        assertFalse(UserValidator.isValidUserName(" Hassan"));
    }

    @Test
    public void testPath_NonAlphabetic() {
        // Path: 2→4→5→7→8→9
        assertFalse(UserValidator.isValidUserName("Hassan123"));
    }

    @Test
    public void testPath_NoSpaces() {
        // Path: 2→4→5→7→8→10→12→13→14
        assertFalse(UserValidator.isValidUserName("Hassan"));
    }

    @Test
    public void testPath_Valid() {
        // Path: 2→4→5→7→8→10→11→12→13→15→16
        assertTrue(UserValidator.isValidUserName("Hassan Ali"));
    }
}
