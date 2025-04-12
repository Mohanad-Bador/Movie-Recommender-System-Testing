package Services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import movierecommender.Entities.Movie;
import movierecommender.Entities.Recommendation;
import movierecommender.Entities.User;
import movierecommender.Errors.AppError;
import movierecommender.Services.RecommendationFileWriterService;

@ExtendWith(MockitoExtension.class)
public class RecommendationFileWriterServiceTest {

        @Mock
        private File mockFile;

        @Mock
        private FileWriter mockFileWriter;

        private RecommendationFileWriterService service;

        @BeforeEach
        public void setup() {
                service = new RecommendationFileWriterService(mockFile, mockFileWriter);
        }

        @Test
        public void RecommendationFileWriterService_OneUser_SuccessfulWrite() throws IOException, AppError {
                // Arrange
                when(mockFile.getName()).thenReturn("test.txt");
                Movie movie1 = new Movie("The Shawshank Redemption", "123",
                                new HashSet<String>(Arrays.asList("Action", "Adventure")));
                Movie movie2 = new Movie("The Godfather", "222",
                                new HashSet<String>(Arrays.asList("Drama", "Adventure")));
                Movie movie3 = new Movie("The Dark Knight", "333",
                                new HashSet<String>(Arrays.asList("Sc-fi", "Adventure")));

                HashSet<String> userMovies1 = new HashSet<String>();
                userMovies1.add("1");
                userMovies1.add("2");
                User user = new User("1", "user1", userMovies1);

                ArrayList<Movie> recommendedMovies1 = new ArrayList<Movie>(Arrays.asList(movie3));
                ArrayList<Recommendation> recommendations = new ArrayList<>(
                                Arrays.asList(new Recommendation(user, recommendedMovies1)));

                // Act
                service.writeRecommendations(recommendations);

                // Assert
                verify(mockFileWriter).write(eq("user1, 1\nThe Dark Knight\n"));
                verify(mockFileWriter).close();
        }

        @Test
        public void RecommendationFileWriterService_OneUserMultiRecommendations_SuccessfulWrite()
                        throws IOException, AppError {
                // Arrange
                when(mockFile.getName()).thenReturn("test.txt");

                // User 1
                Movie movie1 = new Movie("The Shawshank Redemption", "123",
                                new HashSet<String>(Arrays.asList("Action", "Adventure")));
                Movie movie2 = new Movie("The Godfather", "222",
                                new HashSet<String>(Arrays.asList("Drama", "Adventure")));
                Movie movie3 = new Movie("The Dark Knight", "333",
                                new HashSet<String>(Arrays.asList("Sc-fi", "Adventure")));

                HashSet<String> userMovies1 = new HashSet<String>();
                userMovies1.add("1");
                User user = new User("1", "user1", userMovies1);

                ArrayList<Movie> recommendedMovies1 = new ArrayList<Movie>(Arrays.asList(movie2, movie3));
                ArrayList<Recommendation> recommendations = new ArrayList<>(
                                Arrays.asList(new Recommendation(user, recommendedMovies1)));
                // Act
                service.writeRecommendations(recommendations);

                // Assert
                verify(mockFileWriter).write(eq("user1, 1\nThe Godfather, The Dark Knight\n"));
                verify(mockFileWriter).close();
        }

        @Test
        public void RecommendationFileWriterService_MultiUser_SuccessfulWrite() throws IOException, AppError {
                // Arrange
                when(mockFile.getName()).thenReturn("test.txt");
                // User 1
                Movie movie1 = new Movie("The Shawshank Redemption", "123",
                                new HashSet<String>(Arrays.asList("Action", "Adventure")));
                Movie movie2 = new Movie("The Godfather", "222",
                                new HashSet<String>(Arrays.asList("Drama", "Adventure")));
                Movie movie3 = new Movie("The Dark Knight", "333",
                                new HashSet<String>(Arrays.asList("Sc-fi", "Adventure")));

                HashSet<String> userMovies1 = new HashSet<String>();
                userMovies1.add("1");
                userMovies1.add("2");
                User user1 = new User("1", "user1", userMovies1);

                ArrayList<Movie> recommendedMovies1 = new ArrayList<Movie>(Arrays.asList(movie3));

                // User 2
                Movie movie4 = new Movie("The Green Mile", "444",
                                new HashSet<String>(Arrays.asList("Action", "Adventure")));
                Movie movie5 = new Movie("Imagination", "555",
                                new HashSet<String>(Arrays.asList("apoclaypse", "Adventure")));
                Movie movie6 = new Movie("Chernobyl: Abyss", "666",
                                new HashSet<String>(Arrays.asList("apoclaypse")));

                HashSet<String> userMovies2 = new HashSet<String>();
                userMovies1.add("1");
                User user2 = new User("2", "user2", userMovies2);

                ArrayList<Movie> recommendedMovies2 = new ArrayList<Movie>(Arrays.asList(movie5, movie6));

                ArrayList<Recommendation> recommendations = new ArrayList<>(
                                Arrays.asList(new Recommendation(user1, recommendedMovies1),
                                                new Recommendation(user2, recommendedMovies2)));

                // Act
                service.writeRecommendations(recommendations);

                // Assert
                verify(mockFileWriter)
                                .write(eq("user1, 1\nThe Dark Knight\nuser2, 2\nImagination, Chernobyl: Abyss\n"));
                verify(mockFileWriter).close();
        }

        @Test
        public void RecommendationFileWriterService_NoRecommendations_Exception() {
                // Arrange
                ArrayList<Recommendation> recommendations = new ArrayList<>();

                // Act & Assert
                IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                                () -> service.writeRecommendations(recommendations));
                assertEquals("No Recommendations Available", exception.getMessage());
        }

        @Test
        public void RecommendationFileWriterService_NoFilenameSpecified_Exception() {
                // Arrange
                when(mockFile.getName()).thenReturn("");
                Movie movie1 = new Movie("The Shawshank Redemption", "123",
                                new HashSet<String>(Arrays.asList("Action", "Adventure")));
                Movie movie2 = new Movie("The Godfather", "222",
                                new HashSet<String>(Arrays.asList("Drama", "Adventure")));
                Movie movie3 = new Movie("The Dark Knight", "333",
                                new HashSet<String>(Arrays.asList("Sc-fi", "Adventure")));

                HashSet<String> userMovies1 = new HashSet<String>();
                userMovies1.add("1");
                userMovies1.add("2");
                User user = new User("1", "user1", userMovies1);

                ArrayList<Movie> recommendedMovies1 = new ArrayList<Movie>(Arrays.asList(movie3));
                ArrayList<Recommendation> recommendations = new ArrayList<>(
                                Arrays.asList(new Recommendation(user, recommendedMovies1)));

                // Act & Assert
                IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                                () -> service.writeRecommendations(recommendations));
                assertEquals("No Filename Specified", exception.getMessage());
        }

        @Test
        public void RecommendationFileWriterService_NoFilenameSpecified_IOException() throws IOException, AppError {
                // Arrange
                when(mockFile.getName()).thenReturn("test.txt");
                doThrow(new IOException("Simulated IO error")).when(mockFileWriter).write(anyString());

                Movie movie1 = new Movie("The Shawshank Redemption", "123",
                                new HashSet<String>(Arrays.asList("Action", "Adventure")));
                Movie movie2 = new Movie("The Godfather", "222",
                                new HashSet<String>(Arrays.asList("Drama", "Adventure")));
                Movie movie3 = new Movie("The Dark Knight", "333",
                                new HashSet<String>(Arrays.asList("Sc-fi", "Adventure")));

                HashSet<String> userMovies1 = new HashSet<String>();
                userMovies1.add("1");
                userMovies1.add("2");
                User user = new User("1", "user1", userMovies1);

                ArrayList<Movie> recommendedMovies1 = new ArrayList<Movie>(Arrays.asList(movie3));
                ArrayList<Recommendation> recommendations = new ArrayList<>(
                                Arrays.asList(new Recommendation(user, recommendedMovies1)));

                // Act & Assert
                AppError error = assertThrows(AppError.class,
                                () -> service.writeRecommendations(recommendations));
                assertTrue(error.getMessage().contains("Error writing recommendations to file"));
        }
}