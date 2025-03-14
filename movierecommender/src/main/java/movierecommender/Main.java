package movierecommender;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import movierecommender.Controllers.RecommenderController;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        RecommenderController controller = new RecommenderController();
        //! Initiate the program
        controller.recommend();
    }
}