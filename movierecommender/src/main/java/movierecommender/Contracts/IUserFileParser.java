package movierecommender.Contracts;

import java.util.HashMap;

import movierecommender.Entities.User;
import movierecommender.Errors.AppError;

public interface IUserFileParser {
    public HashMap<String, User> parseUsers(String filename) throws AppError;
}
