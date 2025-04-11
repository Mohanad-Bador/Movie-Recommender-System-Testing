package movierecommender.Helpers;

public class UserValidator extends BaseValidator {

    public static boolean isValidUserName(String name) {
        if (name == null)
            return false;
        boolean whitespaceExists = false;
        if (name.charAt(0) == ' ') {
            return false;
        }
        for (char c : name.toCharArray()) {
            if (!Character.isAlphabetic(c) && c != ' ') {
                return false;
            }
            if (c == ' ') {
                whitespaceExists = true;
            }

        }
        if (!whitespaceExists) {
            return false;
        }

        return true;
    }

    public static boolean isValidUserID(String id) {
        if (id == null)
            return false;

        if (id.length() != 9)
            return false;

        if (!Character.isDigit(id.charAt(0)))
            return false;

        String userIDAlphabets = extractAlphabets(id);
        if (userIDAlphabets.length() > 1 || (userIDAlphabets.length() == 1 && Character.isDigit(id.charAt(8))))
            return false;

        return true;
    }
}
