package movierecommender;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import movierecommender.Entities.Movie;
import movierecommender.Entities.Recommendation;
import movierecommender.Entities.User;
import movierecommender.Errors.AppError;
import movierecommender.Services.RecommendationFileWriterService;

public class Main {
    public static void main(String[] args) throws AppError {

        Movie a1 = new Movie("The Shawshank Redemption", "123",
                new HashSet<String>(Arrays.asList("Action", "Adventure")));
        Movie a2 = new Movie("The Godfather", "124", new HashSet<String>(Arrays.asList("Drama", "Adventure")));
        Movie a3 = new Movie("The Dark Knight", "125", new HashSet<String>(Arrays.asList("Sc-fi", "Adventure")));
        Movie a4 = new Movie("The Shawshank Redemption", "126",
                new HashSet<String>(Arrays.asList("Action", "Adventure")));

        HashMap<String, Movie> hash = new HashMap<String, Movie>();
        hash.put("1", a1);
        hash.put("2", a2);
        hash.put("3", a3);
        hash.put("4", a4);

        ArrayList<Movie> movies = new ArrayList<Movie>(Arrays.asList(a1, a2, a3, a4));

        User user = new User("1", "user1", hash);

        ArrayList<Recommendation> scenarios = new ArrayList<>(
                Arrays.asList(new Recommendation(user, movies), new Recommendation(user, movies),
                        new Recommendation(user, movies)));

        try {
            File file = new File("hell.txt");
            FileWriter fileWriter = new FileWriter(file);
            RecommendationFileWriterService fileWriterService = new RecommendationFileWriterService(file, fileWriter);
            fileWriterService.writeRecommendations(scenarios);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // nnsk.wwwriteRecommendations("D:\\college\\Semaster 10\\Software
        // Testing\\Project\\Movie_Recommender\\Movie-recommender-Testing-Project\\hell.txt");
    }
}