package service;

import exception.DuplicateUsernameException;
import exception.InvalidUserException;
import exception.UserNotAuthorizedException;
import exception.UserNotFoundException;
import model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {

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
            //File file = new File(directory.getAbsolutePath() + "/" + newUser.getUsername());
            return newUser;
        }
    }

    @Override
    public void deleteUser(String username) {
        File path = new File("users");
        boolean b = path.mkdir();
        File file = new File(path.getAbsolutePath() + "/" + username);
        if (file.delete()) {

        }
    }

    @Override
    public void storeInFile(User user) {
        File file = new File("users");
        boolean b = file.mkdir();
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file.getAbsolutePath() + "/" + user.getUsername(), false))) {
            outputStream.writeObject(user);
            //outputStream.writeUTF("\n");
            outputStream.flush();
        } catch (IOException e) {

        }
    }

    @Override
    public User login(String username, String password) {
        File directory = new File("users");
        directory.mkdir();
        File[] users = directory.listFiles();
        User user = null;
        boolean foundUser = false;
        for (File f : users) {
            try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(f.getAbsolutePath()))) {
                user = (User) inputStream.readObject();
                if (user.getUsername().equals(username)) {
                    foundUser = true;

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

    //FOR TESTING ONLY
    public static void main(String[] args) {
        User user1 = new User("dshi", "pw");
        User user2 = new User("daniel", "pw");
        User user3 = new User("dan", "pw");
        UserService userService = new UserServiceImpl();
        userService.storeInFile(user1);
        userService.storeInFile(user2);
        userService.storeInFile(user3);
        userService.deleteUser("daniel");
        File directory = new File("users");
        directory.mkdir();
        File[] files = directory.listFiles();
    }


}
