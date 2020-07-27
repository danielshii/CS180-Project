
import exception.DuplicateUsernameException;
import exception.InvalidUserException;
import exception.UserNotFoundException;
import jdk.nashorn.internal.scripts.JO;
import model.Post;
import model.User;
import service.*;

import javax.swing.*;
import java.util.ArrayList;

public class App implements Runnable {
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

    public void run() {
        //TODO: code for buttons/panels/frames goes here
    }

    public static void main(String[] args) {


    }
}
