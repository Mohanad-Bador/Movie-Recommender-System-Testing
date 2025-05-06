package Controllers;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import movierecommender.Controllers.RecommenderController;
import movierecommender.Errors.AppError;

public class RecommenderControllerIntegrationTest {

    private final String outputFilePath = "hell.txt";
    private final String userFilePath;
    private final String movieFilePath;

    public RecommenderControllerIntegrationTest() throws URISyntaxException {
        ClassLoader classLoader = getClass().getClassLoader();

        // Load users.txt
        if (classLoader.getResource("Assets/users.txt") == null) {
            throw new IllegalArgumentException("users.txt file not found in src/test/resources/Assets/");
        }
        this.userFilePath = Paths.get(classLoader.getResource("Assets/users.txt").toURI()).toString();

        // Load movies.txt
        if (classLoader.getResource("Assets/movies.txt") == null) {
            throw new IllegalArgumentException("movies.txt file not found in src/test/resources/Assets/");
        }
        this.movieFilePath = Paths.get(classLoader.getResource("Assets/movies.txt").toURI()).toString();
    }

    @BeforeEach
    public void setup() throws IOException {
        // Ensure the output file does not exist before each test
        File outputFile = new File(outputFilePath);
        if (outputFile.exists()) {
            outputFile.delete();
        }
    }

    @AfterEach
    public void cleanup() {
        // Clean up the output file after each test
        File outputFile = new File(outputFilePath);
        if (outputFile.exists()) {
            outputFile.delete();
        }
    }

    @Test
    public void recommend_ValidInputFiles_ShouldGenerateRecommendations() throws AppError, IOException {
        // Arrange
        RecommenderController controller = new RecommenderController();

        // Act
        controller.recommend(outputFilePath, userFilePath, movieFilePath);

        // Assert
        File outputFile = new File(outputFilePath);
        assertTrue(outputFile.exists(), "Output file should be created");

        // Read the output file and verify its content
        List<String> outputLines = Files.readAllLines(outputFile.toPath());
        assertFalse(outputLines.isEmpty(), "Output file should not be empty");
        // assertTrue(outputLines.get(0).contains("Recommendations for User"), "Output should contain recommendations");
    }

    @Test
    public void recommend_InvalidUserFile_ShouldThrowAppError() {
        // Arrange
        // Temporarily rename the users.txt file to simulate a missing or invalid file
        RecommenderController controller = new RecommenderController();

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> controller.recommend(outputFilePath, "randomfile.txt", movieFilePath));
        // assertTrue(exception.getMessage().contains("Error parsing users"));
    }

    @Test
    public void recommend_InvalidMovieFile_ShouldThrowAppError() {
        // Arrange
        // Temporarily rename the movies.txt file to simulate a missing or invalid file
        RecommenderController controller = new RecommenderController();

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> controller.recommend(outputFilePath, userFilePath, "random.txt"));
    }
}
