import javax.swing.*;
import java.util.*;

public class User {
    private String password;
    private String username;
    private ArrayList<Post> posts;

    public User(String password, String username, ArrayList<Post> posts) {
        this.password = password;
        this.username = username;
        this.posts = posts;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ArrayList<Post> getPosts() {
        return posts;
    }

    public void setPosts(ArrayList<Post> posts) {
        this.posts = posts;
    }

    public boolean equals(User user) {
        return username.equals(user.getUsername()) && password.equals(user.getPassword());
    }

    public Comment createComment()
    {
        String content = JOptionPane.showInputDialog(null, "Comment on this post", "Comment", JOptionPane.QUESTION_MESSAGE);
        long millis = System.currentTimeMillis();
        Date date = new Date(millis);
        String time = date.toString();
        return new Comment(time, content, username);

    }
}
