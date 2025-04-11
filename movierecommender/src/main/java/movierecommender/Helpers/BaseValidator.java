package movierecommender.Helpers;

public class BaseValidator {
    protected static String extractDigits(String id) {
        StringBuilder IDNumbers = new StringBuilder("");
        for (Character c : id.toCharArray())
            if (Character.isDigit(c))
                IDNumbers.append(c);
        return IDNumbers.toString();
    }

    protected static String extractAlphabets(String id) {
        StringBuilder IDAlphabets = new StringBuilder("");
        for (Character c : id.toCharArray())
            if (Character.isAlphabetic(c))
                IDAlphabets.append(c);
        return IDAlphabets.toString();
    }
}