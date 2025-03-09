package movierecommender.Entities;

import java.util.ArrayList;

public class User {
    private String userId;
    private String username;
    private ArrayList<Movie> favoriteMovies;
    
    public User(){

    }
    public User(String userId,String username,ArrayList<Movie> favoriteMovies){
        this.userId = userId;
        this.username = username;
        this.favoriteMovies = favoriteMovies;
    }
    
}
