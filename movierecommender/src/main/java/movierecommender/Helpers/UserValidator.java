package movierecommender.Helpers;

public class UserValidator {

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
        // Last alpahbet shit figure out later
        if (id.length() > 9) {
            return false;
        }
        if (!Character.isDigit(id.charAt(0))) {
            return false;
        }
        return true;
    }
}
