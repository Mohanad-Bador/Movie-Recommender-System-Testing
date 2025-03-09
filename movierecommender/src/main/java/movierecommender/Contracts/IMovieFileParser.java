package movierecommender.Contracts;

import java.util.HashMap;
import movierecommender.Entities.Movie;

public interface IMovieFileParser {
    public HashMap<String,Movie> parseMovies(String filename);
}
