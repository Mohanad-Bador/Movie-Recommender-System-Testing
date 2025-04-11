package movierecommender.Helpers;

public class MovieValidator {
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
        // Unique numbers????
        if (id == null)
            return false;
        int numSize = 3;
        for (char c : id.toCharArray()) {
            if (Character.isLowerCase(c))
                return false;
            if (Character.isDigit(c)) {
                numSize -= 1;
            }
            if (numSize < 0)
                return false;
        }
        return true;
    }
}
