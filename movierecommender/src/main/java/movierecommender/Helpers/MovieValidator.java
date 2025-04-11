package movierecommender.Helpers;

import java.util.HashSet;
import java.util.HashMap;
import java.util.Map;
import movierecommender.Entities.Movie;

public class MovieValidator extends BaseValidator {
    public static boolean isValidMovieName(String movieName) {
        if (movieName == null)
            return false;
        String[] movieNameList = movieName.split(" ");

        for (String name : movieNameList)
            if (name == " " || name == "")
                continue;
            else if (Character.isLowerCase(name.charAt(0)))
                return false;

        return true;
    }

    public static boolean isValidMovieID(String id) {
        // capital charcters
        // T3TR3 & 3 int
        // Unique numbers ????
        if (id == null)
            return false;

        int numSize = 3;
        for (char c : id.toCharArray()) {
            if (Character.isLowerCase(c))
                return false;
            if (Character.isDigit(c))
                numSize -= 1;

            if (numSize < 0)
                return false;
        }
        return true;
    }

    public static boolean isUniqueIDNumbers(String id, HashMap<String, Movie> movieMap) {
        String movieIDNumbers = extractDigits(id);
        for (Map.Entry<String, Movie> entry : movieMap.entrySet()) {
            String otherMovieIDNumbers = extractDigits(entry.getKey());
            if (movieIDNumbers.equals(otherMovieIDNumbers))
                return false;
        }
        return true;
    }
}
