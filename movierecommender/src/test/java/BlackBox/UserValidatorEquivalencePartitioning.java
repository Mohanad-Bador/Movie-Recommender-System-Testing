package BlackBox;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import movierecommender.Helpers.UserValidator;

public class UserValidatorEquivalencePartitioning {

    // ----------- isValidUserName Equivalence Classes -----------
    // Valid: Alphabetic with at least one space, not starting with space, not null
    // Invalid: null, starts with space, contains non-alphabetic/non-space, no space

    @Test
    public void validUserName_AlphabeticWithSpace() {
        assertTrue(UserValidator.isValidUserName("John Doe"));
    }

    @Test
    public void invalidUserName_Null() {
        assertFalse(UserValidator.isValidUserName(null));
    }

    @Test
    public void invalidUserName_StartsWithSpace() {
        assertFalse(UserValidator.isValidUserName(" John"));
    }

    @Test
    public void invalidUserName_ContainsNonAlphabetic() {
        assertFalse(UserValidator.isValidUserName("John1 Doe"));
    }

    @Test
    public void invalidUserName_NoSpace() {
        assertFalse(UserValidator.isValidUserName("JohnDoe"));
    }

    // ----------- isValidUserID Equivalence Classes -----------
    // Valid: 9 chars, starts with digit, at most one letter (not at end if digit), not null
    // Invalid: null, not 9 chars, does not start with digit, more than one letter, letter at end with digit

    @Test
    public void validUserID_AllDigits() {
        assertTrue(UserValidator.isValidUserID("123456789"));
    }

    @Test
    public void validUserID_OneLetterAtEnd() {
        assertTrue(UserValidator.isValidUserID("12345678A"));
    }

    @Test
    public void invalidUserID_Null() {
        assertFalse(UserValidator.isValidUserID(null));
    }

    @Test
    public void invalidUserID_NotNineChars() {
        assertFalse(UserValidator.isValidUserID("12345678"));
        assertFalse(UserValidator.isValidUserID("1234567890"));
    }

    @Test
    public void invalidUserID_DoesNotStartWithDigit() {
        assertFalse(UserValidator.isValidUserID("A23456789"));
    }

    @Test
    public void invalidUserID_MoreThanOneLetter() {
        assertFalse(UserValidator.isValidUserID("1234567AB"));
    }

    @Test
    public void invalidUserID_LetterAtEndWithDigit() {
        assertFalse(UserValidator.isValidUserID("12345678A1"));
    }
}
