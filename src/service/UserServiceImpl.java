package service;

import enumeration.ContentType;
import exception.DuplicateUsernameException;
import exception.InvalidUserException;
import exception.UserNotAuthorizedException;
import exception.UserNotFoundException;
import model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {

    private FileService fileService;

    public UserServiceImpl() {
        this.fileService = new FileServiceImpl();
    }

    @Override
    public User getUser(String username) {
        return null;
    }

    @Override
    public User createUser(String username, String password) {
        File directory = new File("users");
        directory.mkdir();
        boolean duplicate = false;
        File[] users = directory.listFiles();
        for (File f : users) {
            try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(f.getAbsolutePath()))) {
                User user = (User) inputStream.readObject();
                if (user.getUsername().equals(username)) {
                    duplicate = true;
                }
            } catch (Exception e) {

            }
        }
        if (duplicate) {
            throw new DuplicateUsernameException(username);
        } else {
            User newUser = new User(username, password);
            storeInFile(newUser);
            return newUser;
        }
    }

    @Override
    public void deleteUser(User user) {
        fileService.deleteFile(ContentType.USER, user.getUsername(), user.getUsername());
    }

    @Override
    public void storeInFile(User user) {
        fileService.saveUserToFile(user);
    }

    @Override
    public User login(String username, String password) {
        File directory = new File("users");
        directory.mkdir();
        File[] users = directory.listFiles();
        User user = null;

        for (File f : users) {
            try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(f.getAbsolutePath()))) {
                user = (User) inputStream.readObject();
                if (user.getUsername().equals(username)) {


                    if (user.getPassword().equals(password)) {
                        return user;
                    } else {
                        throw new InvalidUserException();
                    }
                }


            } catch (Exception e) {

            }
        }

        throw new UserNotFoundException();

    }

}
