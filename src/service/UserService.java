package service;

import model.User;

public interface UserService {
    User getUser(String username);
    User createUser(String username, String password);
    void deleteUser(String username);
    void storeInFile(User user);
    User login(String username, String password);

}
