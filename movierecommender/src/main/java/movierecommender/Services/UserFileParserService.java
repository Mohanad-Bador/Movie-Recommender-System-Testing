package movierecommender.Services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import movierecommender.Contracts.IUserFileParser;
import movierecommender.Entities.Movie;
import movierecommender.Entities.User;
import movierecommender.Errors.AppError;
import movierecommender.Helpers.UserValidator;

public class UserFileParserService implements IUserFileParser {

    @Override
    public HashMap<String, User> parseUsers(String filename) throws AppError {
        // TODO Auto-generated method stub
        // Update with your file's actual path
        // Length of file
        // read file
        // read line by line
        // Odd lines are names, id
        // Even lines are generes of that movie
        HashMap<String, User> users = new HashMap<String, User>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(filename));
            for (int i = 0; i < lines.size(); i += 2) {
                String[] splitted = lines.get(i).split(",");
                if (splitted.length <= 1) {
                    throw new AppError("There were no commas");
                }
                String name = splitted[0];
                if (!UserValidator.isValidUserName(name))
                    throw new AppError("User Name \"" + name + "\" is wrong");
                String id = splitted[1].trim();
                if (!UserValidator.isValidUserID(id))
                    throw new AppError("User id letters \"" + id + "\" are wrong");
                String[] movieIDArray = lines.get(i + 1).split(",");
                Set<String> IDs = new HashSet<>(Arrays.asList(movieIDArray));
                User user = new User(id, name, IDs);
                if (users.containsKey(user.getuserID())) {
                    throw new AppError("Duplicated User ID \"" + id + "\"");
                }
                users.put(id, user);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // throw new UnsupportedOperationException("Unimplemented method
        // 'parseusers'");
        return users;
    }

}
