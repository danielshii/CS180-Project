
import exception.DuplicateUsernameException;
import exception.InvalidUserException;
import exception.UserNotFoundException;
import jdk.nashorn.internal.scripts.JO;
import model.Post;
import model.User;
import service.*;

import javax.swing.*;
import java.util.ArrayList;

public class App {
    private User currentUser;
    private static final String[] loginOptions = new String[]{"Create account", "Login", "Quit"};

    public App() {

    }

    public App(User currentUser) {
        this.currentUser = currentUser;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public static void main(String[] args) {

        UserService userService = new UserServiceImpl();
        PostService postService = new PostServiceImpl();
        CommentService commentService = new CommentServiceImpl();
        User user = null;
        String username;
        String password;

        int choice = JOptionPane.showOptionDialog(null, "Welcome", "App", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, loginOptions, null);
        switch (choice) {
            case 0:
                while (true) {
                    username = JOptionPane.showInputDialog(null, "Username", "Create", JOptionPane.QUESTION_MESSAGE);
                    password = JOptionPane.showInputDialog(null, "Password", "Create", JOptionPane.QUESTION_MESSAGE);
                    try {
                        user = userService.createUser(username, password);
                        userService.storeInFile(user);
                        break;
                    } catch (DuplicateUsernameException e) {
                        JOptionPane.showMessageDialog(null, "That username already exists!", "ERROR", JOptionPane.ERROR_MESSAGE);
                        continue;
                    }
                }
                break;
            case 1:
                while (true) {
                    username = JOptionPane.showInputDialog(null, "Username", "Login", JOptionPane.QUESTION_MESSAGE);
                    password = JOptionPane.showInputDialog(null, "Password", "Login", JOptionPane.QUESTION_MESSAGE);
                    try {
                        user = userService.login(username, password);
                        break;
                    } catch (UserNotFoundException | InvalidUserException e) {
                        JOptionPane.showMessageDialog(null, "Username or password is incorrect!", "ERROR", JOptionPane.ERROR_MESSAGE);
                        continue;
                    }
                }
                break;
            case 2:
                JOptionPane.showMessageDialog(null, "Goodbye", "App", JOptionPane.INFORMATION_MESSAGE);
                break;
        }
        App app = new App(user);
        JOptionPane.showMessageDialog(null, "Now logged in as " + app.getCurrentUser().getUsername(), "App", JOptionPane.INFORMATION_MESSAGE);

    }
}
