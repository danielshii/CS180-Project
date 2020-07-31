
import exception.DuplicateUsernameException;
import exception.InvalidUserException;
import exception.UserNotAuthorizedException;
import exception.UserNotFoundException;
import jdk.nashorn.internal.scripts.JO;
import model.Post;
import model.User;
import service.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class App implements Runnable {
    private User currentUser;
    private int index;
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

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void run() {
        App app = new App();
        app.setCurrentUser(new User("daniel", "pw"));
        JFrame postFrame = new JFrame("BoilerGram");
        postFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        PostService postService = new PostServiceImpl();
        List<Post> posts = postService.getAllPosts();

        JPanel panel = new JPanel();

        JPanel actionPanel = new JPanel();
        Container container = postFrame.getContentPane();
        container.setLayout(new BorderLayout());

        JButton previous = new JButton("Previous Post");
        JButton next = new JButton("Next Post");
        JButton edit = new JButton("Edit Post");
        JButton delete = new JButton("Delete Post");
        JButton comment = new JButton("View Comments");
        JButton addPost = new JButton("Add Post");

        ActionListener actionListener = new ActionListener() {
            String post;
            JPanel postPanel = new JPanel();

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == previous) {
                    if (app.getIndex() == 0) {

                    } else if (posts.size() == 0) {
                        post = "No one has posted anything. Be the first to post something!";


                    } else {
                        app.setIndex(app.getIndex() - 1);
                        String post = posts.get(app.getIndex()).toString();

                    }
                } else if (e.getSource() == next) {
                    if (app.getIndex() == posts.size() - 1) {

                    } else if (posts.size() == 0) {
                        String error = "No one has posted anything.";


                    } else {
                        app.setIndex(app.getIndex() + 1);
                        String post = posts.get(app.getIndex()).toString();

                    }
                } else if (e.getSource() == edit) {
                    try {

                    } catch (UserNotAuthorizedException exception) {
                        //error
                    }
                } else if (e.getSource() == delete) {
                    try {
                        postService.deletePost(posts.get(app.getIndex()).getUuid(), currentUser.getUsername());
                    } catch (UserNotAuthorizedException exception) {
                        //error
                    }
                } else if (e.getSource() == comment) {
                    //opens a new window with all of the post's comments
                } else if (e.getSource() == addPost) {

                }
            }
        };
        actionPanel.add(edit);
        actionPanel.add(delete);
        actionPanel.add(comment);


        container.add(previous, BorderLayout.WEST);
        container.add(next, BorderLayout.EAST);
        container.add(actionPanel, BorderLayout.SOUTH);

        postFrame.setSize(600, 400);
        postFrame.setLocationRelativeTo(null);
        postFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        postFrame.setVisible(true);


    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new App());
    }
}
