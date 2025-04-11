package movierecommender;

import java.io.IOException;

import movierecommender.Controllers.RecommenderController;
import movierecommender.Errors.AppError;

public class Main {
    public static void main(String[] args) throws AppError, IOException {
        RecommenderController controller = new RecommenderController();
        // ! Initiate the program
        controller.recommend();
    }
}