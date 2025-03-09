package movierecommender;

import movierecommender.Controllers.RecommenderController;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        RecommenderController controller = new RecommenderController();
        //! Initiate the program
        controller.recommend();
    }
}