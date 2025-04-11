package movierecommender;

import java.lang.reflect.Array;
import java.util.Arrays;

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
import movierecommender.Services.MovieFilerParserService;
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