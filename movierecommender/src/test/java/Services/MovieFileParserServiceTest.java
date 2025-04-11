package Services;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import movierecommender.Contracts.IMovieFileParser;
import movierecommender.Services.MovieFilerParserService;

public class MovieFileParserServiceTest {
    private IMovieFileParser movieFileParserService;

    @BeforeAll
    public void initializeService() {
        movieFileParserService = new MovieFilerParserService();
    }

}
