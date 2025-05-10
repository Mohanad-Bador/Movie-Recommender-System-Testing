package Services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.MockedStatic;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import movierecommender.Contracts.IMovieFileParser;
import movierecommender.Entities.Movie;
import movierecommender.Errors.AppError;
import movierecommender.Helpers.MovieValidator;
import movierecommender.Helpers.UserValidator;
import movierecommender.Services.MovieFileParserService;

public class MovieFileParserServiceTest {
    private IMovieFileParser movieFileParserService;

    @BeforeEach
    public void initializeService() {
        movieFileParserService = new MovieFileParserService();
    }

    @Test
    public void parseMovies_ValidData_ShouldReturnMoviesHashMap() throws AppError,IOException{
        String filename = "moves.txt";
        List<String> mockFileLines = Arrays.asList(
           "The Shawshank Redemption, TSR001",
                "Drama",
                "The Godfather, TG002",
                "Crime, Drama",
                "The Dark Knight, TDK003",
                "Action, Crime, Drama"
        );
        try(
            MockedStatic<Files> mockedFiles = mockStatic((Files.class));
            MockedStatic<Paths> mockedPaths = mockStatic(Paths.class);
            MockedStatic<MovieValidator> mockedMovieValidator = mockStatic(MovieValidator.class);
            MockedStatic<UserValidator> mockedUserValidator = mockStatic(UserValidator.class)
        ){
            Path mockPath = mock(Path.class);
            when(Paths.get(filename)).thenReturn(mockPath);
            
            when(Files.readAllLines(mockPath)).thenReturn(mockFileLines);
            
            when(MovieValidator.isValidMovieName(anyString())).thenReturn(true);
            when(MovieValidator.isValidMovieID(anyString())).thenReturn(true);
            when(MovieValidator.isUniqueIDNumbers(anyString(), any())).thenReturn(true);
            
            HashMap<String,Movie> movies = movieFileParserService.parseMovies(filename);

            assertNotNull(movies);
            assertEquals(3, movies.size());
            assertTrue(movies.containsKey("TSR001"));
            assertTrue(movies.get("TSR001").getMovieName().equals("The Shawshank Redemption"));
            assertEquals(movies.get("TSR001").getGenres(),new HashSet<>(Arrays.asList("Drama")));

            assertTrue(movies.containsKey("TG002"));
            assertTrue(movies.get("TG002").getMovieName().equals("The Godfather"));
            assertEquals(movies.get("TG002").getGenres(),new HashSet<>(Arrays.asList("Crime", "Drama")));

            assertTrue(movies.containsKey("TDK003"));
            assertTrue(movies.get("TDK003").getMovieName().equals("The Dark Knight"));
            assertEquals(movies.get("TDK003").getGenres(),new HashSet<>(Arrays.asList("Action", "Crime", "Drama")));
        }
    }

    @Test
    public void parseMovies_InvalidDataNoCommaSeperatedMovieNameAndID_ShouldThrowAppError() throws AppError, IOException{
        String filename = "moves.txt";
        List<String> mockFileLines = Arrays.asList(
           "The Shawshank Redemption TSR001",
                "Drama",
                "The Godfather, TG002",
                "Crime, Drama",
                "The Dark Knight, TDK003",
                "Action, Crime, Drama"
        );
        try(
            MockedStatic<Files> mockedFiles = mockStatic((Files.class));
            MockedStatic<Paths> mockedPaths = mockStatic(Paths.class);
            MockedStatic<MovieValidator> mockedMovieValidator = mockStatic(MovieValidator.class);
            MockedStatic<UserValidator> mockedUserValidator = mockStatic(UserValidator.class)
        ){
            Path mockPath = mock(Path.class);
            when(Paths.get(filename)).thenReturn(mockPath);
            
            when(Files.readAllLines(mockPath)).thenReturn(mockFileLines);
            
            when(MovieValidator.isValidMovieName(anyString())).thenReturn(true);
            when(MovieValidator.isValidMovieID(anyString())).thenReturn(true);
            when(MovieValidator.isUniqueIDNumbers(anyString(), any())).thenReturn(true);
            
            AppError exception = assertThrows(AppError.class, ()-> movieFileParserService.parseMovies(filename));
            assertTrue(exception.getMessage().equals("There were no commas"));
        }
    }

    @Test
    public void parseMovies_InvalidDataMovieNameIsInvalid_ShouldThrowAppError() throws AppError,IOException{
        String filename = "moves.txt";
        List<String> mockFileLines = Arrays.asList(
           "The shawshank Redemption, TSR001",
                "Drama",
                "The Godfather, TG002",
                "Crime, Drama",
                "The Dark Knight, TDK003",
                "Action, Crime, Drama"
        );
        try(
            MockedStatic<Files> mockedFiles = mockStatic((Files.class));
            MockedStatic<Paths> mockedPaths = mockStatic(Paths.class);
            MockedStatic<MovieValidator> mockedMovieValidator = mockStatic(MovieValidator.class);
            MockedStatic<UserValidator> mockedUserValidator = mockStatic(UserValidator.class)
        ){
            Path mockPath = mock(Path.class);
            when(Paths.get(filename)).thenReturn(mockPath);
            
            when(Files.readAllLines(mockPath)).thenReturn(mockFileLines);
            
            when(MovieValidator.isValidMovieName(anyString())).thenReturn(false);
            when(MovieValidator.isValidMovieID(anyString())).thenReturn(true);
            when(MovieValidator.isUniqueIDNumbers(anyString(), any())).thenReturn(true);
            
            AppError exception = assertThrows(AppError.class, ()-> movieFileParserService.parseMovies(filename));
            assertTrue(exception.getMessage().equals("Movie Title \"The shawshank Redemption\" is wrong"));
        }
    }

    @Test
    public void parseMovies_InvalidDataMovieIDIsInvalid_ShouldThrowAppError() throws AppError,IOException{
        String filename = "moves.txt";
        List<String> mockFileLines = Arrays.asList(
           "The Shawshank Redemption, TSRAAA0011",
                "Drama",
                "The Godfather, TG002",
                "Crime, Drama",
                "The Dark Knight, TDK003",
                "Action, Crime, Drama"
        );
        try(
            MockedStatic<Files> mockedFiles = mockStatic((Files.class));
            MockedStatic<Paths> mockedPaths = mockStatic(Paths.class);
            MockedStatic<MovieValidator> mockedMovieValidator = mockStatic(MovieValidator.class);
            MockedStatic<UserValidator> mockedUserValidator = mockStatic(UserValidator.class)
        ){
            Path mockPath = mock(Path.class);
            when(Paths.get(filename)).thenReturn(mockPath);
            
            when(Files.readAllLines(mockPath)).thenReturn(mockFileLines);
            
            when(MovieValidator.isValidMovieName(anyString())).thenReturn(true);
            when(MovieValidator.isValidMovieID(anyString())).thenReturn(false);
            when(MovieValidator.isUniqueIDNumbers(anyString(), any())).thenReturn(true);
            
            AppError exception = assertThrows(AppError.class, ()-> movieFileParserService.parseMovies(filename));
            assertTrue(exception.getMessage().equals("Movie id letters \"TSRAAA0011\" are wrong"));
        }
    }

    @Test
    public void parseMovies_InvalidDataMovieIDNumberIsNotUnique_ShouldThrowAppError() throws AppError,IOException{
        String filename = "moves.txt";
        List<String> mockFileLines = Arrays.asList(
           "The Shawshank Redemption, TSR001",
                "Drama",
                "The Godfather, TG002",
                "Crime, Drama",
                "The Dark Knight, TDK003",
                "Action, Crime, Drama"
        );
        try(
            MockedStatic<Files> mockedFiles = mockStatic((Files.class));
            MockedStatic<Paths> mockedPaths = mockStatic(Paths.class);
            MockedStatic<MovieValidator> mockedMovieValidator = mockStatic(MovieValidator.class);
            MockedStatic<UserValidator> mockedUserValidator = mockStatic(UserValidator.class)
        ){
            Path mockPath = mock(Path.class);
            when(Paths.get(filename)).thenReturn(mockPath);
            
            when(Files.readAllLines(mockPath)).thenReturn(mockFileLines);
            
            when(MovieValidator.isValidMovieName(anyString())).thenReturn(true);
            when(MovieValidator.isValidMovieID(anyString())).thenReturn(true);
            when(MovieValidator.isUniqueIDNumbers(anyString(), any())).thenReturn(false);
            
            AppError exception = assertThrows(AppError.class, ()-> movieFileParserService.parseMovies(filename));
            assertTrue(exception.getMessage().equals("Movie id letters \"TSR001\" are wrong"));
        }
    }

    @Test
    public void parseMovies_InvalidDataMovieIDIsDuplicate_ShouldThrowAppError() throws AppError,IOException{
        String filename = "moves.txt";
        List<String> mockFileLines = Arrays.asList(
           "The shawshank Redemption, TSR001",
                "Drama",
                "The Godfather, TSR001",
                "Crime, Drama",
                "The Dark Knight, TDK003",
                "Action, Crime, Drama"
        );
        try(
            MockedStatic<Files> mockedFiles = mockStatic((Files.class));
            MockedStatic<Paths> mockedPaths = mockStatic(Paths.class);
            MockedStatic<MovieValidator> mockedMovieValidator = mockStatic(MovieValidator.class);
            MockedStatic<UserValidator> mockedUserValidator = mockStatic(UserValidator.class)
        ){
            Path mockPath = mock(Path.class);
            when(Paths.get(filename)).thenReturn(mockPath);
            
            when(Files.readAllLines(mockPath)).thenReturn(mockFileLines);
            
            when(MovieValidator.isValidMovieName(anyString())).thenReturn(true);
            when(MovieValidator.isValidMovieID(anyString())).thenReturn(true);
            when(MovieValidator.isUniqueIDNumbers(anyString(), any())).thenReturn(true);
            
            AppError exception = assertThrows(AppError.class, ()-> movieFileParserService.parseMovies(filename));
            assertTrue(exception.getMessage().equals("Duplicated Movie ID \"TSR001\""));
        }
    }

    // WHITE BOX TESTING
    // Statement Coverage Tests
    @Test
    public void statementCoverage_validMovieData() throws AppError, IOException {
        // Test Case 1: Valid movie data
        String filename = "movies.txt";
        List<String> mockFileLines = Arrays.asList(
            "The Shawshank Redemption, TSR001",
            "Drama"
        );
        
        try (
            MockedStatic<Files> mockedFiles = mockStatic(Files.class);
            MockedStatic<Paths> mockedPaths = mockStatic(Paths.class);
            MockedStatic<MovieValidator> mockedValidator = mockStatic(MovieValidator.class)
        ) {
            Path mockPath = mock(Path.class);
            when(Paths.get(filename)).thenReturn(mockPath);
            when(Files.readAllLines(mockPath)).thenReturn(mockFileLines);
            
            when(MovieValidator.isValidMovieName(anyString())).thenReturn(true);
            when(MovieValidator.isValidMovieID(anyString())).thenReturn(true);
            when(MovieValidator.isUniqueIDNumbers(anyString(), any())).thenReturn(true);
            
            HashMap<String, Movie> result = movieFileParserService.parseMovies(filename);
            
            assertNotNull(result);
            assertEquals(1, result.size());
            assertTrue(result.containsKey("TSR001"));
        }
    }
    
    @Test
    public void statementCoverage_noCommasInMovieLine() throws IOException {
        // Test Case 2: No commas in movie line
        String filename = "movies.txt";
        List<String> mockFileLines = Arrays.asList(
            "The Shawshank Redemption TSR001", // No comma
            "Drama"
        );
        
        try (
            MockedStatic<Files> mockedFiles = mockStatic(Files.class);
            MockedStatic<Paths> mockedPaths = mockStatic(Paths.class)
        ) {
            Path mockPath = mock(Path.class);
            when(Paths.get(filename)).thenReturn(mockPath);
            when(Files.readAllLines(mockPath)).thenReturn(mockFileLines);
            
            AppError exception = assertThrows(AppError.class, 
                () -> movieFileParserService.parseMovies(filename));
            assertEquals("There were no commas", exception.getMessage());
        }
    }
    
    @Test
    public void statementCoverage_invalidMovieName() throws IOException {
        // Test Case 3: Invalid movie name
        String filename = "movies.txt";
        List<String> mockFileLines = Arrays.asList(
            "invalid movie, TSR001",
            "Drama"
        );
        
        try (
            MockedStatic<Files> mockedFiles = mockStatic(Files.class);
            MockedStatic<Paths> mockedPaths = mockStatic(Paths.class);
            MockedStatic<MovieValidator> mockedValidator = mockStatic(MovieValidator.class)
        ) {
            Path mockPath = mock(Path.class);
            when(Paths.get(filename)).thenReturn(mockPath);
            when(Files.readAllLines(mockPath)).thenReturn(mockFileLines);
            
            when(MovieValidator.isValidMovieName("invalid movie")).thenReturn(false);
            
            AppError exception = assertThrows(AppError.class, 
                () -> movieFileParserService.parseMovies(filename));
            assertEquals("Movie Title \"invalid movie\" is wrong", exception.getMessage());
        }
    }
    
    @Test
    public void statementCoverage_invalidMovieID() throws IOException {
        // Test Case 4: Invalid movie ID
        String filename = "movies.txt";
        List<String> mockFileLines = Arrays.asList(
            "The Shawshank Redemption, invalid123",
            "Drama"
        );
        
        try (
            MockedStatic<Files> mockedFiles = mockStatic(Files.class);
            MockedStatic<Paths> mockedPaths = mockStatic(Paths.class);
            MockedStatic<MovieValidator> mockedValidator = mockStatic(MovieValidator.class)
        ) {
            Path mockPath = mock(Path.class);
            when(Paths.get(filename)).thenReturn(mockPath);
            when(Files.readAllLines(mockPath)).thenReturn(mockFileLines);
            
            when(MovieValidator.isValidMovieName(anyString())).thenReturn(true);
            when(MovieValidator.isValidMovieID("invalid123")).thenReturn(false);
            
            AppError exception = assertThrows(AppError.class, 
                () -> movieFileParserService.parseMovies(filename));
            assertEquals("Movie id letters \"invalid123\" are wrong", exception.getMessage());
        }
    }
    
    @Test
    public void statementCoverage_duplicateMovieID() throws IOException {
        // Test Case 5: Duplicate movie ID
        String filename = "movies.txt";
        List<String> mockFileLines = Arrays.asList(
            "The Shawshank Redemption, TSR001",
            "Drama",
            "The Godfather, TSR001", // Same ID
            "Crime" 
        );
        
        try (
            MockedStatic<Files> mockedFiles = mockStatic(Files.class);
            MockedStatic<Paths> mockedPaths = mockStatic(Paths.class);
            MockedStatic<MovieValidator> mockedValidator = mockStatic(MovieValidator.class)
        ) {
            Path mockPath = mock(Path.class);
            when(Paths.get(filename)).thenReturn(mockPath);
            when(Files.readAllLines(mockPath)).thenReturn(mockFileLines);
            
            when(MovieValidator.isValidMovieName(anyString())).thenReturn(true);
            when(MovieValidator.isValidMovieID(anyString())).thenReturn(true);
            when(MovieValidator.isUniqueIDNumbers(anyString(), any())).thenReturn(true);
            
            AppError exception = assertThrows(AppError.class, 
                () -> movieFileParserService.parseMovies(filename));
            assertEquals("Duplicated Movie ID \"TSR001\"", exception.getMessage());
        }
    }
    
    @Test
    public void statementCoverage_ioExceptionHandling() throws IOException, AppError {
        // Test Case 6: IO Exception handling
        String filename = "nonexistent.txt";
        
        try (
            MockedStatic<Files> mockedFiles = mockStatic(Files.class);
            MockedStatic<Paths> mockedPaths = mockStatic(Paths.class)
        ) {
            Path mockPath = mock(Path.class);
            when(Paths.get(filename)).thenReturn(mockPath);
            
            when(Files.readAllLines(mockPath)).thenThrow(new IOException("File not found"));
            
            HashMap<String, Movie> result = movieFileParserService.parseMovies(filename);
            
            assertNotNull(result);
            assertTrue(result.isEmpty());
        }
    }

    // Data Flow Tests
    @Test
    public void dataFlow_testCase1_emptyFile() throws AppError, IOException {
        String filename = "empty.txt";
        List<String> mockFileLines = Arrays.asList();  // Empty file
        
        try (
            MockedStatic<Files> mockedFiles = mockStatic(Files.class);
            MockedStatic<Paths> mockedPaths = mockStatic(Paths.class)
        ) {
            Path mockPath = mock(Path.class);
            when(Paths.get(filename)).thenReturn(mockPath);
            when(Files.readAllLines(mockPath)).thenReturn(mockFileLines);
            
            HashMap<String, Movie> result = movieFileParserService.parseMovies(filename);
            
            // Verify result
            assertNotNull(result);
            assertTrue(result.isEmpty());
        }
    }

    @Test
    public void dataFlow_testCase2_noCommas() throws IOException {
        // Test Case 2: No Commas in Movie Line
        String filename = "movies.txt";
        List<String> mockFileLines = Arrays.asList(
            "The Shawshank Redemption TSR001", // No comma
            "Drama"
        );
        
        try (
            MockedStatic<Files> mockedFiles = mockStatic(Files.class);
            MockedStatic<Paths> mockedPaths = mockStatic(Paths.class)
        ) {
            Path mockPath = mock(Path.class);
            when(Paths.get(filename)).thenReturn(mockPath);
            when(Files.readAllLines(mockPath)).thenReturn(mockFileLines);
            
            AppError exception = assertThrows(AppError.class, 
                () -> movieFileParserService.parseMovies(filename));
            assertEquals("There were no commas", exception.getMessage());
        }
    }

    @Test
    public void dataFlow_testCase3_invalidMovieName() throws IOException {
        // Test Case 3: Invalid Movie Name
        String filename = "movies.txt";
        List<String> mockFileLines = Arrays.asList(
            "invalid movie, TSR001",
            "Drama"
        );
        
        try (
            MockedStatic<Files> mockedFiles = mockStatic(Files.class);
            MockedStatic<Paths> mockedPaths = mockStatic(Paths.class);
            MockedStatic<MovieValidator> mockedValidator = mockStatic(MovieValidator.class)
        ) {
            Path mockPath = mock(Path.class);
            when(Paths.get(filename)).thenReturn(mockPath);
            when(Files.readAllLines(mockPath)).thenReturn(mockFileLines);
            
            when(MovieValidator.isValidMovieName("invalid movie")).thenReturn(false);
            
            AppError exception = assertThrows(AppError.class, 
                () -> movieFileParserService.parseMovies(filename));
            assertEquals("Movie Title \"invalid movie\" is wrong", exception.getMessage());
        }
    }

    @Test
    public void dataFlow_testCase4_invalidMovieID() throws IOException {
        // Test Case 4: Invalid Movie ID
        String filename = "movies.txt";
        List<String> mockFileLines = Arrays.asList(
            "The Shawshank Redemption, invalid123",
            "Drama"
        );
        
        try (
            MockedStatic<Files> mockedFiles = mockStatic(Files.class);
            MockedStatic<Paths> mockedPaths = mockStatic(Paths.class);
            MockedStatic<MovieValidator> mockedValidator = mockStatic(MovieValidator.class)
        ) {
            Path mockPath = mock(Path.class);
            when(Paths.get(filename)).thenReturn(mockPath);
            when(Files.readAllLines(mockPath)).thenReturn(mockFileLines);
            
            when(MovieValidator.isValidMovieName(anyString())).thenReturn(true);
            when(MovieValidator.isValidMovieID(eq("invalid123"))).thenReturn(false);
            
            AppError exception = assertThrows(AppError.class, 
                () -> movieFileParserService.parseMovies(filename));
            assertEquals("Movie id letters \"invalid123\" are wrong", exception.getMessage());
        }
    }

    @Test
    public void dataFlow_testCase5_validSingleMovie() throws AppError, IOException {
        // Test Case 5: Valid Single Movie
        String filename = "movies.txt";
        List<String> mockFileLines = Arrays.asList(
            "The Shawshank Redemption, TSR001",
            "Drama"
        );
        
        try (
            MockedStatic<Files> mockedFiles = mockStatic(Files.class);
            MockedStatic<Paths> mockedPaths = mockStatic(Paths.class);
            MockedStatic<MovieValidator> mockedValidator = mockStatic(MovieValidator.class)
        ) {
            Path mockPath = mock(Path.class);
            when(Paths.get(filename)).thenReturn(mockPath);
            when(Files.readAllLines(mockPath)).thenReturn(mockFileLines);
            
            when(MovieValidator.isValidMovieName(anyString())).thenReturn(true);
            when(MovieValidator.isValidMovieID(anyString())).thenReturn(true);
            when(MovieValidator.isUniqueIDNumbers(anyString(), any())).thenReturn(true);
            
            HashMap<String, Movie> result = movieFileParserService.parseMovies(filename);
            
            assertNotNull(result);
            assertEquals(1, result.size());
            assertTrue(result.containsKey("TSR001"));
            assertEquals("The Shawshank Redemption", result.get("TSR001").getMovieName());
        }
    }

    @Test
    public void dataFlow_testCase6_duplicateMovieID() throws IOException {
        // Test Case 6: Duplicate Movie ID
        String filename = "movies.txt";
        List<String> mockFileLines = Arrays.asList(
            "The Shawshank Redemption, TSR001",
            "Drama",
            "The Godfather, TSR001", // Same ID
            "Crime"
        );
        
        try (
            MockedStatic<Files> mockedFiles = mockStatic(Files.class);
            MockedStatic<Paths> mockedPaths = mockStatic(Paths.class);
            MockedStatic<MovieValidator> mockedValidator = mockStatic(MovieValidator.class)
        ) {
            Path mockPath = mock(Path.class);
            when(Paths.get(filename)).thenReturn(mockPath);
            when(Files.readAllLines(mockPath)).thenReturn(mockFileLines);
            
            when(MovieValidator.isValidMovieName(anyString())).thenReturn(true);
            when(MovieValidator.isValidMovieID(anyString())).thenReturn(true);
            when(MovieValidator.isUniqueIDNumbers(anyString(), any())).thenReturn(true);
            
            AppError exception = assertThrows(AppError.class, 
                () -> movieFileParserService.parseMovies(filename));
            assertEquals("Duplicated Movie ID \"TSR001\"", exception.getMessage());
        }
    }

    @Test
    public void dataFlow_testCase7_multipleGenres() throws AppError, IOException {
        // Test Case 7: Multiple Genres
        String filename = "movies.txt";
        List<String> mockFileLines = Arrays.asList(
            "The Shawshank Redemption, TSR001",
            "Drama, Action, Thriller" // Multiple genres
        );
        
        try (
            MockedStatic<Files> mockedFiles = mockStatic(Files.class);
            MockedStatic<Paths> mockedPaths = mockStatic(Paths.class);
            MockedStatic<MovieValidator> mockedValidator = mockStatic(MovieValidator.class)
        ) {
            Path mockPath = mock(Path.class);
            when(Paths.get(filename)).thenReturn(mockPath);
            when(Files.readAllLines(mockPath)).thenReturn(mockFileLines);
            
            when(MovieValidator.isValidMovieName(anyString())).thenReturn(true);
            when(MovieValidator.isValidMovieID(anyString())).thenReturn(true);
            when(MovieValidator.isUniqueIDNumbers(anyString(), any())).thenReturn(true);
            
            HashMap<String, Movie> result = movieFileParserService.parseMovies(filename);
            
            assertNotNull(result);
            assertEquals(1, result.size());
            assertEquals(3, result.get("TSR001").getGenres().size());
            assertTrue(result.get("TSR001").getGenres().contains("Drama"));
            assertTrue(result.get("TSR001").getGenres().contains("Action"));
            assertTrue(result.get("TSR001").getGenres().contains("Thriller"));
        }
    }

    @Test
    public void dataFlow_testCase8_ioException() throws IOException, AppError {
        // Test Case 8: IO Exception
        String filename = "nonexistent.txt";
        
        try (
            MockedStatic<Files> mockedFiles = mockStatic(Files.class);
            MockedStatic<Paths> mockedPaths = mockStatic(Paths.class)
        ) {
            Path mockPath = mock(Path.class);
            when(Paths.get(filename)).thenReturn(mockPath);
            
            when(Files.readAllLines(mockPath)).thenThrow(new IOException("File not found"));
            
            HashMap<String, Movie> result = movieFileParserService.parseMovies(filename);
            
            assertNotNull(result);
            assertTrue(result.isEmpty());
        }
    }
}