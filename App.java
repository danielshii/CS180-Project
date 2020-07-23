import jdk.nashorn.internal.scripts.JO;

import javax.swing.*;
import java.util.ArrayList;

public class App {
    private ArrayList<User> users;
    private final String[] options = new String[]{"Create New Account", "Login"};

    public void register() {
        String username;
        String password;
        do {
            username = JOptionPane.showInputDialog(null, "Enter a username", "Register", JOptionPane.QUESTION_MESSAGE);
            if (contains(users, username)) {
                JOptionPane.showMessageDialog(null, "That username is already taken", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            if (username.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Username cannot be empty", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        } while (contains(users, username) || username.isEmpty());

        password = JOptionPane.showInputDialog(null, "Enter a password", "Register", JOptionPane.QUESTION_MESSAGE);
        users.add(new User(password, username, new ArrayList<Post>()));
    }

    public void login() {
        User user;
        String username;
        String password;
        do {
            username = JOptionPane.showInputDialog(null, "Enter username", "Login", JOptionPane.QUESTION_MESSAGE);
            password = JOptionPane.showInputDialog(null, "Enter password", "Login", JOptionPane.QUESTION_MESSAGE);
            User u = new User(password, username, new ArrayList<Post>());
            user = findUser(users, u);
            if(user == null)
            {
                JOptionPane.showMessageDialog(null, "Username or Password was incorrect", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        } while (user == null);
        //TODO: show pane that allows user to either view all posts or view their own posts
    }

    public boolean contains(ArrayList<User> users, String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public User findUser(ArrayList<User> users, User user) {
        for (User u : users) {
            if (u.equals(user)) {
                return u;
            }
        }
        return null;
    }


}
