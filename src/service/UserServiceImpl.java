package service;

import enumeration.ObjectType;
import exception.DuplicateUsernameException;
import exception.InvalidUserException;
import exception.UserNotFoundException;
import model.User;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Objects;

public class UserServiceImpl implements UserService {

    private FileService fileService;

    private PostService postService;

    private CommentService commentService;

    public UserServiceImpl() {
        this.fileService = new FileServiceImpl();
        this.postService = new PostServiceImpl();
        this.commentService = new CommentServiceImpl();
    }

    @Override
    public User getUser(String username) {
        File userFile = fileService.getFile(ObjectType.USER, username);
        if (Objects.isNull(userFile)) {
            // User file with the username not exists, throw exception
            throw new UserNotFoundException();
        }
        return getUserFromFile(userFile, username);
    }

    @Override
    public User createUser(String username, String password) {
        File userFile = fileService.getFile(ObjectType.USER, username);
        if (Objects.nonNull(userFile)) {
            // User file with the same username already exists, throw exception
            throw new DuplicateUsernameException(username);
        }
        User newUser = new User(username, password);
        storeInFile(newUser);
        return newUser;
    }

    @Override
    public void deleteUser(User user) {
        // TODO: Delete all comments and posts by this user

        // commentService.delete

        // postService.delete

        fileService.deleteFile(ObjectType.USER, user.getUsername(), user.getUsername());
    }

    @Override
    public void storeInFile(User user) {
        fileService.saveUserToFile(user);
    }

    @Override
    public User login(String username, String password) {
        User user = getUser(username);

        if (!user.getPassword().equals(password)) {
            throw new InvalidUserException();
        }
        return user;
    }

    private User getUserFromFile(File file, String username) {
        User user = null;
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file.getAbsolutePath()))) {
            user = (User) inputStream.readObject();
            if (!user.getUsername().equals(username)) {
                throw new UserNotFoundException();
            }
        } catch (Exception e) {

        }
        return user;
    }

}
