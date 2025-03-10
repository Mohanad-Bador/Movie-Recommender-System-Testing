package movierecommender;

import java.lang.reflect.Array;
import java.util.Arrays;

import movierecommender.Controllers.RecommenderController;
import movierecommender.Errors.AppError;
import movierecommender.Services.MovieFilerParserService;

public class Main {
    public static void main(String[] args) throws AppError {
        // System.out.println("Hello world!");
        MovieFilerParserService test = new MovieFilerParserService();
        test.parseMovies("Assets/movies.txt");
        // String[] s = "asdkjsadj, asd23 232 ksdh".split(",");
        // System.out.println((Arrays.toString(s))[0]);
        RecommenderController controller = new RecommenderController();
        // ! Initiate the program
        controller.recommend();
    }
}