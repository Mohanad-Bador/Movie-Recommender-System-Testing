package Services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import movierecommender.Contracts.IMovieFileParser;
import movierecommender.Entities.Movie;
import movierecommender.Errors.AppError;
import movierecommender.Helpers.MovieValidator;
import movierecommender.Helpers.UserValidator;
import movierecommender.Services.MovieFilerParserService;

public class MovieFileParserServiceTest {
    private IMovieFileParser movieFileParserService;

    @BeforeEach
    public void initializeService() {
        movieFileParserService = new MovieFilerParserService();
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
}
