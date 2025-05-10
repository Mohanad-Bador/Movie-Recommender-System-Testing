package Services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.MockedStatic;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import movierecommender.Contracts.IUserFileParser;
import movierecommender.Entities.User;
import movierecommender.Errors.AppError;
import movierecommender.Helpers.UserValidator;
import movierecommender.Services.UserFileParserService;

@ExtendWith(MockitoExtension.class)
public class UserFileParserServiceTest {

    private IUserFileParser userFileParser;
    
    @BeforeEach
    public void initializeService() {
        userFileParser = new UserFileParserService();
    }
    
    @Test
    public void parseUsers_ValidData_ShouldReturnUsersHashMap() throws AppError, IOException {
        // Arrange
        String filename = "users.txt";
        List<String> mockFileLines = Arrays.asList(
            "John Doe, 123456789",
            "1, 2, 3",
            "Jane Smith, 987654321",
            "4, 5, 6"
        );
        
        try (
            MockedStatic<Files> mockedFiles = mockStatic(Files.class);
            MockedStatic<Paths> mockedPaths = mockStatic(Paths.class);
            MockedStatic<UserValidator> mockedUserValidator = mockStatic(UserValidator.class)
        ) {
            Path mockPath = mock(Path.class);
            when(Paths.get(filename)).thenReturn(mockPath);
            
            when(Files.readAllLines(mockPath)).thenReturn(mockFileLines);
            
            when(UserValidator.isValidUserName(anyString())).thenReturn(true);
            when(UserValidator.isValidUserID(anyString())).thenReturn(true);
            
            // Act
            HashMap<String, User> users = userFileParser.parseUsers(filename);
            
            // Assert
            assertNotNull(users);
            assertEquals(2, users.size());
            assertTrue(users.containsKey("123456789"));
            assertTrue(users.containsKey("987654321"));
            
            // Verify user data
            User user1 = users.get("123456789");
            assertEquals("John Doe", user1.getUserName());
            assertEquals(3, user1.getfavoriteMoviesSet().size());
            assertTrue(user1.getfavoriteMoviesSet().contains("1"));
            assertTrue(user1.getfavoriteMoviesSet().contains("2"));
            assertTrue(user1.getfavoriteMoviesSet().contains("3"));
            
            User user2 = users.get("987654321");
            assertEquals("Jane Smith", user2.getUserName());
            assertEquals(3, user2.getfavoriteMoviesSet().size());
            assertTrue(user2.getfavoriteMoviesSet().contains("4"));
            assertTrue(user2.getfavoriteMoviesSet().contains("5"));
            assertTrue(user2.getfavoriteMoviesSet().contains("6"));
        }
    }
    
    @Test
    public void parseUsers_InvalidDataNoCommaSeparated_ShouldThrowAppError() throws AppError, IOException {
        // Arrange
        String filename = "users.txt";
        List<String> mockFileLines = Arrays.asList(
            "John Doe 123456789", // Missing comma
            "1, 2, 3",
            "Jane Smith, 987654321",
            "4, 5, 6"
        );
        
        try (
            MockedStatic<Files> mockedFiles = mockStatic(Files.class);
            MockedStatic<Paths> mockedPaths = mockStatic(Paths.class);
            MockedStatic<UserValidator> mockedUserValidator = mockStatic(UserValidator.class)
        ) {
            Path mockPath = mock(Path.class);
            when(Paths.get(filename)).thenReturn(mockPath);
            
            when(Files.readAllLines(mockPath)).thenReturn(mockFileLines);
            
            // Act & Assert
            AppError exception = assertThrows(AppError.class, () -> userFileParser.parseUsers(filename));
            assertTrue(exception.getMessage().equals("There were no commas"));
        }
    }
    
    @Test
    public void parseUsers_InvalidUserName_ShouldThrowAppError() throws AppError, IOException {
        // Arrange
        String filename = "users.txt";
        List<String> mockFileLines = Arrays.asList(
            "john doe, 123456789", // lowercase name (invalid)
            "1, 2, 3",
            "Jane Smith, 987654321",
            "4, 5, 6"
        );
        
        try (
            MockedStatic<Files> mockedFiles = mockStatic(Files.class);
            MockedStatic<Paths> mockedPaths = mockStatic(Paths.class);
            MockedStatic<UserValidator> mockedUserValidator = mockStatic(UserValidator.class)
        ) {
            Path mockPath = mock(Path.class);
            when(Paths.get(filename)).thenReturn(mockPath);
            
            when(Files.readAllLines(mockPath)).thenReturn(mockFileLines);
            
            // First user name is invalid
            when(UserValidator.isValidUserName("john doe")).thenReturn(false);
            when(UserValidator.isValidUserName("Jane Smith")).thenReturn(true);
            when(UserValidator.isValidUserID(anyString())).thenReturn(true);
            
            // Act & Assert
            AppError exception = assertThrows(AppError.class, () -> userFileParser.parseUsers(filename));
            assertTrue(exception.getMessage().contains("User Name"));
        }
    }
    
    @Test
    public void parseUsers_InvalidUserID_ShouldThrowAppError() throws AppError, IOException {
        // Arrange
        String filename = "users.txt";
        List<String> mockFileLines = Arrays.asList(
            "John Doe, ABC123456", // Non-numeric first character (invalid)
            "1, 2, 3",
            "Jane Smith, 987654321",
            "4, 5, 6"
        );
        
        try (
            MockedStatic<Files> mockedFiles = mockStatic(Files.class);
            MockedStatic<Paths> mockedPaths = mockStatic(Paths.class);
            MockedStatic<UserValidator> mockedUserValidator = mockStatic(UserValidator.class)
        ) {
            Path mockPath = mock(Path.class);
            when(Paths.get(filename)).thenReturn(mockPath);
            
            when(Files.readAllLines(mockPath)).thenReturn(mockFileLines);
            
            when(UserValidator.isValidUserName(anyString())).thenReturn(true);
            when(UserValidator.isValidUserID("ABC123456")).thenReturn(false);
            when(UserValidator.isValidUserID("987654321")).thenReturn(true);
            
            // Act & Assert
            AppError exception = assertThrows(AppError.class, () -> userFileParser.parseUsers(filename));
            assertTrue(exception.getMessage().contains("User id letters"));
        }
    }
    
    @Test
    public void parseUsers_DuplicateUserID_ShouldThrowAppError() throws AppError, IOException {
        // Arrange
        String filename = "users.txt";
        List<String> mockFileLines = Arrays.asList(
            "John Doe, 123456789",
            "1, 2, 3",
            "Jane Smith, 123456789", // Same ID as first user
            "4, 5, 6"
        );
        
        try (
            MockedStatic<Files> mockedFiles = mockStatic(Files.class);
            MockedStatic<Paths> mockedPaths = mockStatic(Paths.class);
            MockedStatic<UserValidator> mockedUserValidator = mockStatic(UserValidator.class)
        ) {
            Path mockPath = mock(Path.class);
            when(Paths.get(filename)).thenReturn(mockPath);
            
            when(Files.readAllLines(mockPath)).thenReturn(mockFileLines);
            
            when(UserValidator.isValidUserName(anyString())).thenReturn(true);
            when(UserValidator.isValidUserID(anyString())).thenReturn(true);
            
            // Act & Assert
            AppError exception = assertThrows(AppError.class, () -> userFileParser.parseUsers(filename));
            assertTrue(exception.getMessage().contains("Duplicated User ID"));
        }
    }
    
    @Test
    public void parseUsers_FileNotFound_ReturnsEmptyHashMap() throws IOException,AppError {
        // Arrange
        String filename = "nonexistent.txt";
        
        try (
            MockedStatic<Files> mockedFiles = mockStatic(Files.class);
            MockedStatic<Paths> mockedPaths = mockStatic(Paths.class)
        ) {
            Path mockPath = mock(Path.class);
            when(Paths.get(filename)).thenReturn(mockPath);
            
            // Mock Files.readAllLines to throw IOException
            when(Files.readAllLines(mockPath)).thenThrow(new IOException("File not found"));
            
            // Act
            HashMap<String, User> result = userFileParser.parseUsers(filename);
            
            // Assert - should return an empty map
            assertNotNull(result);
            assertTrue(result.isEmpty());
        }
    }
}
