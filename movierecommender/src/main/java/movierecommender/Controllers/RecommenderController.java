package movierecommender.Controllers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import movierecommender.Contracts.IMovieFileParser;
import movierecommender.Contracts.IRecommendationFileWriter;
import movierecommender.Contracts.IRecommender;
import movierecommender.Contracts.IUserFileParser;
import movierecommender.Entities.Movie;
import movierecommender.Entities.Recommendation;
import movierecommender.Entities.User;
import movierecommender.Errors.AppError;
import movierecommender.Services.MovieFilerParserService;
import movierecommender.Services.RecommendationFileWriterService;
import movierecommender.Services.RecommenderService;
import movierecommender.Services.UserFileParserService;

public class RecommenderController {
    public void recommend() throws AppError, IOException{
        recommend("hell.txt","./Assets/users.txt" , "./Assets/movies.txt");
    }
    public void recommend(String outputFile, String userFile, String movieFile) throws AppError, IOException{
        //!1) Initialize the services
        IMovieFileParser movieService = new MovieFilerParserService();
        IUserFileParser userService = new UserFileParserService();
        IRecommender recommenderService = new RecommenderService();
        
        File file = new File(outputFile);
        FileWriter fileWriter = new FileWriter(file);

        IRecommendationFileWriter recommenderFileWriterService = new RecommendationFileWriterService(file,fileWriter);

        //!2) Initialize our repositories (The User HashMap and the Movie HashMap)
        HashMap<String,User> users = userService.parseUsers(userFile); 
        HashMap<String,Movie> movies = movieService.parseMovies(movieFile); 
        
        //!3) For each user, generate the recommendations
        ArrayList<Recommendation> recommendations = new ArrayList<>();
        
        users.forEach((userID,user)->{
            var recommendation = recommenderService.generateRecommendations(user, movies);
            recommendations.add(recommenderService.generateRecommendations(user, movies));
            System.out.println(recommendation);
        });

        //!4) Write the recommendations
        recommenderFileWriterService.writeRecommendations(recommendations); 
    }
}
