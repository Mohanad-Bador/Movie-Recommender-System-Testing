package movierecommender.Entities;

// import java.util.ArrayList;
import java.util.Set;

public class User {
    private String userID;
    private String username;
    private Set<String> favoriteMoviesSet;

    public User() {

    }

    public User(String userID, String username, Set<String> favoriteMoviesSet) {
        this.userID = userID;
        this.username = username;
        this.favoriteMoviesSet = favoriteMoviesSet;
    }

    public void setuserID(String userID) {
        this.userID = userID;
    }

    public void setUserName(String username) {
        this.username = username;
    }

    public void SetfavoriteMoviesSet(Set<String> favoriteMoviesSet) {
        this.favoriteMoviesSet = favoriteMoviesSet;
    }

    public String getuserID() {
        return this.userID;
    }

    public String getUserName() {
        return this.username;
    }

    public Set<String> getfavoriteMoviesSet() {
        return this.favoriteMoviesSet;
    }

    @Override
    public String toString() {
        StringBuilder user = new StringBuilder(
                "userID: " + userID + "\nUserName: " + username + "\nfavoriteMovies: \n");
        this.favoriteMoviesSet.forEach((MovieID) -> {
            user.append(MovieID + ", ");
        });
        return user.toString();
    }
}
