package movierecommender;

import movierecommender.Controllers.RecommenderController;
import movierecommender.Errors.AppError;
import movierecommender.Services.UserFileParserService;

public class Main {
    public static void main(String[] args) throws AppError {
        // System.out.println("Hello world!");
        // MovieFilerParserService test = new MovieFilerParserService();
        // test.parseMovies("Assets/movies.txt");
        UserFileParserService testUser = new UserFileParserService();
        testUser.parseUsers("Assets/users.txt").forEach((key, value) -> {
            System.out.println(value.toString() + "\n");
        });
        RecommenderController controller = new RecommenderController();
        // ! Initiate the program
        controller.recommend();
    }
}