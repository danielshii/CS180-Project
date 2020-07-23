import jdk.nashorn.internal.scripts.JO;

import javax.swing.*;
import java.util.ArrayList;

public class Post {

    private String time;
    private String content;
    private User author;
    private ArrayList<Comment> comments;

    public Post(String time, String content, User author, ArrayList<Comment> comments) {
        this.time = time;
        this.content = content;
        this.author = author;
        this.comments = comments;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    public String printComments()
    {
        StringBuilder s = new StringBuilder("");
        for(Comment c : comments)
        {
            s.append(c.toString());
        }
        return s.toString();
    }

    public void showComments()
    {
        JOptionPane.showMessageDialog(null, printComments(), "Comments", JOptionPane.INFORMATION_MESSAGE);
    }
}
