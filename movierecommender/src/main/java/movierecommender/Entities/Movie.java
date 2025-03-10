package movierecommender.Entities;

import java.util.Set;

public class Movie {
    private String movieName;
    private String movieID;
    private Set<String> genres;

    public Movie() {

    }

    public Movie(String movieName, String movieID, Set<String> genres) {
        this.movieName = movieName;
        this.movieID = movieID;
        this.genres = genres;
    }

    public String getMovieName() {
        return movieName;
    }

    // Setter for movieName
    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    // Getter for movieID
    public String getMovieID() {
        return movieID;
    }

    // Setter for movieID
    public void setMovieID(String movieID) {
        this.movieID = movieID;
    }

    // Getter for genres
    public Set<String> getGenres() {
        return genres;
    }

    // Setter for genres
    public void setGenres(Set<String> genres) {
        this.genres = genres;
    }

    @Override
    public String toString() {
        StringBuilder movie = new StringBuilder(
                "movieID: " + movieID + "\nmovieName: " + movieName + "\ngeneres: \n" + String.join(", ", this.genres));
        return movie.toString();
    }
}
