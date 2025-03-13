package movierecommender.Contracts;

import java.util.HashMap;
import movierecommender.Entities.Movie;
import movierecommender.Errors.AppError;

public interface IMovieFileParser {
    public HashMap<String, Movie> parseMovies(String filename) throws AppError;
}
