package movierecommender.Entities;

import java.util.ArrayList;

public class Recommendation {
    private User user;
    private ArrayList<Movie> recommendedMovies;


    // Constructor
    public Recommendation() {
    }
    
    public Recommendation(User user, ArrayList<Movie> recommendedMovies) {
        this.user = user;
        this.recommendedMovies = recommendedMovies;
    }

    // Getters
    public User getUser() {
        return user;
    }
    
    public ArrayList<Movie> getRecommendedMovies() {
        return recommendedMovies;
    }

    // Setters
    public void setUser(User user) {
        this.user = user;
    }

    public void setRecommendedMovies(ArrayList<Movie> recommendedMovies) {
        this.recommendedMovies = recommendedMovies;
    }

    // toString
    public String toString(){
        StringBuilder recommendedMoviesSB = new StringBuilder();
        for (Movie movie : this.recommendedMovies){
            recommendedMoviesSB.append(movie.toString() + "\n");
        }
        return "User: " + user.toString() + "\nRecommended Movies: \n" + recommendedMoviesSB.toString();
    }
}