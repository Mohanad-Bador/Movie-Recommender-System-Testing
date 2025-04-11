package movierecommender.Services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import movierecommender.Contracts.IMovieFileParser;
import movierecommender.Entities.Movie;
import movierecommender.Errors.AppError;
import movierecommender.Helpers.MovieValidator;

public class MovieFilerParserService implements IMovieFileParser {

    @Override
    public HashMap<String, Movie> parseMovies(String filename) throws AppError {
        // TODO Auto-generated method stub
        // Update with your file's actual path
        // Length of file
        // read file
        // read line by line
        // Odd lines are names, id
        // Even lines are generes of that movie
        HashMap<String, Movie> movies = new HashMap<String, Movie>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(filename));
            for (int i = 0; i < lines.size(); i += 2) {
                // ! 1) Split to get movie name and ID
                String[] splitted = lines.get(i).split(",");
                if (splitted.length <= 1)
                    throw new AppError("There were no commas");

                String name = splitted[0].trim();

                // ! 2) Validate movie name and ids
                if (!MovieValidator.isValidMovieName(name))
                    throw new AppError("Movie Title \"" + name + "\" is wrong");

                String id = splitted[1].trim();
                if (!MovieValidator.isValidMovieID(id) || !MovieValidator.isUniqueIDNumbers(id, movies))
                    throw new AppError("Movie id letters \"" + id + "\" are wrong");

                // ! 3) Construct Movie object
                String[] generesArray = lines.get(i + 1).split(",");

                for(int j =0;j<generesArray.length;j++)
                    generesArray[j] = generesArray[j].trim();

                Set<String> generes = new HashSet<>(Arrays.asList(generesArray));
                Movie movie = new Movie(name, id, generes);

                if (movies.containsKey(movie.getMovieID()))
                    throw new AppError("Duplicated Movie ID \"" + id + "\"");

                movies.put(id, movie);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return movies;
    }

}
