package movierecommender.Entities;

// import java.util.ArrayList;
import java.util.HashMap;

public class User {
    private String userID;
    private String username;
    private HashMap<String, Movie> favoriteMoviesDict;

    public User() {

    }

    public User(String userID, String username, HashMap<String, Movie> favoriteMoviesDict) {
        this.userID = userID;
        this.username = username;
        this.favoriteMoviesDict = favoriteMoviesDict;
    }

    public void setuserID(String userID) {
        this.userID = userID;
    }

    public void setUserName(String username) {
        this.username = username;
    }

    public void SetfavoriteMoviesDict(HashMap<String, Movie> favoriteMoviesDict) {
        this.favoriteMoviesDict = favoriteMoviesDict;
    }

    public String getuserID() {
        return this.userID;
    }

    public String getUserName() {
        return this.username;
    }

    public HashMap<String, Movie> getfavoriteMoviesDict() {
        return this.favoriteMoviesDict;
    }

    @Override
    public String toString() {
        StringBuilder user = new StringBuilder(
                "userID: " + userID + "\nUserName: " + username + "\nfavoriteMovies: \n");
        this.favoriteMoviesDict.forEach((key, value) -> {
            user.append(value.getMovieName() + ", ");
        });
        return user.toString();
    }
}