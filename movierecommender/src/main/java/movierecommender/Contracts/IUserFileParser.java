package movierecommender.Contracts;

import java.util.HashMap;

import movierecommender.Entities.User;

public interface IUserFileParser {
    public HashMap<String,User> parseUsers(String filename);
}
