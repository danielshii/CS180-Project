
import exception.*;
import jdk.nashorn.internal.scripts.JO;
import model.Comment;
import model.Post;
import model.User;
import service.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class App implements Runnable {
    private User currentUser;
    private int postIndex = 0;
    private int commentIndex = 0;
    private static final String NONAUTHORIZED_POST_USER = "You are not the author of this post";
    private static final String NONAUTHORIZED_COMMENT_USER = "You are not the author of this comment";
    private static final String[] loginOptions = new String[]{"Create account", "Login", "Quit"};
    private List<Post> posts = new ArrayList<>();
    private List<Comment> comments = new ArrayList<>();

    private UserService userService;
    private PostService postService;
    private CommentService commentService;

    public App() {
        userService = new UserServiceImpl();
        postService = new PostServiceImpl();
        commentService = new CommentServiceImpl();
        currentUser = new User("daniel", "pw");
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
        return postIndex;
    }

    public void setIndex(int index) {
        this.postIndex = index;
    }

    public void run() {
        App app = new App();

        JFrame postFrame = new JFrame("BoilerGram");
        JLabel postLabel = new JLabel("");
        postLabel.setHorizontalAlignment(SwingConstants.CENTER);
        postFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        posts = postService.getAllPosts();
        if (posts.size() > 0) {
            postLabel.setText(posts.get(0).toString());
        } else {
            postLabel.setText("No one has posted anything. Be the first to post!");
        }

        JPanel panel = new JPanel();

        JPanel actionPanel = new JPanel();
        Container container = postFrame.getContentPane();
        container.setLayout(new BorderLayout());

        JButton previous = new JButton("Previous Post");
        JButton next = new JButton("Next Post");
        JButton addPost = new JButton("Add Post");
        JButton edit = new JButton("Edit Post");
        JButton delete = new JButton("Delete Post");
        JButton comment = new JButton("View Comments");


        ActionListener actionListener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == previous) {
                    if (postIndex == 0) {
                        //error
                    } else {
                        postIndex--;
                        Post post = posts.get(postIndex);
                        postLabel.setText(post.toString());

                    }
                } else if (e.getSource() == next) {
                    if (postIndex >= posts.size() - 1) {

                    } else {
                        postIndex++;
                        Post post = posts.get(postIndex);
                        postLabel.setText(post.toString());
                    }
                } else if (e.getSource() == edit) {
                    Post currentPost = posts.get(postIndex);
                    if (currentPost.getCreatedUsername().equals(currentUser.getUsername())) {

                    } else {
                        JOptionPane.showMessageDialog(null, NONAUTHORIZED_POST_USER, "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                } else if (e.getSource() == delete) {
                    try {
                        Post currentPost = posts.get(postIndex);
                        postService.deletePost(currentPost.getUuid(), currentUser.getUsername());
                    } catch (UserNotAuthorizedException exception) {
                        JOptionPane.showMessageDialog(null, NONAUTHORIZED_POST_USER, "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                } else if (e.getSource() == comment) {
                    //opens a new window with all of the post's comments
                    Post currentPost = posts.get(postIndex);
                    try {
                        openCommentWindow(currentPost);
                    } catch (PostNotFoundException exception) {

                    }
                } else if (e.getSource() == addPost) {
                    String content = JOptionPane.showInputDialog(null, "Write your post", "Add Post", JOptionPane.QUESTION_MESSAGE);
                    Post newPost = postService.createPost(content, currentUser.getUsername());
                    posts = postService.getAllPosts();
                    postIndex = 0;
                    postLabel.setText(newPost.toString());
                }
            }
        };
        previous.addActionListener(actionListener);
        next.addActionListener(actionListener);

        addPost.addActionListener(actionListener);
        edit.addActionListener(actionListener);
        delete.addActionListener(actionListener);
        comment.addActionListener(actionListener);

        actionPanel.add(addPost);
        actionPanel.add(edit);
        actionPanel.add(delete);
        actionPanel.add(comment);

        container.add(postLabel, BorderLayout.CENTER);


        container.add(previous, BorderLayout.WEST);
        container.add(next, BorderLayout.EAST);
        container.add(actionPanel, BorderLayout.SOUTH);

        postFrame.setSize(600, 400);
        postFrame.setLocationRelativeTo(null);
        postFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        postFrame.setVisible(true);


    }

    public void openCommentWindow(Post currentPost) {
        commentIndex = 0;
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame commentFrame = new JFrame("BoilerGram");
                commentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                CommentService commentService = new CommentServiceImpl();
                comments = commentService.getCommentsByPost(currentPost);

                JPanel panel = new JPanel();

                JPanel actionPanel = new JPanel();
                Container container = commentFrame.getContentPane();
                container.setLayout(new BorderLayout());

                JLabel commentLabel = new JLabel("");
                if (comments.size() > 0) {
                    commentLabel.setText(comments.get(0).toString());
                } else {
                    commentLabel.setText("No one has commented yet. Be the first to comment!");
                }
                commentLabel.setHorizontalAlignment(SwingConstants.CENTER);

                JButton previous = new JButton("Previous Comment");
                JButton next = new JButton("Next Comment");
                JButton edit = new JButton("Edit Comment");
                JButton delete = new JButton("Delete Comment");
                JButton addComment = new JButton("Add Comment");

                ActionListener actionListener = new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (e.getSource() == previous) {
                            if (commentIndex == 0) {
                                //error
                            } else {
                                commentIndex--;
                                Comment comment = comments.get(commentIndex);
                                commentLabel.setText(comment.toString());

                            }
                        } else if (e.getSource() == next) {
                            if (commentIndex >= comments.size() - 1) {

                            } else {
                                commentIndex++;
                                Comment comment = comments.get(commentIndex);
                                commentLabel.setText(comment.toString());
                            }
                        } else if (e.getSource() == edit) {
                            Comment currentComment = comments.get(commentIndex);
                            if (currentComment.getCreatedUsername().equals(currentUser.getUsername())) {

                            } else {
                                JOptionPane.showMessageDialog(null, NONAUTHORIZED_COMMENT_USER, "ERROR", JOptionPane.ERROR_MESSAGE);
                            }
                        } else if (e.getSource() == delete) {
                            try {
                                Comment currentComment = comments.get(commentIndex);
                                commentService.deleteComment(currentComment, currentUser.getUsername());
                            } catch (UserNotAuthorizedException exception) {
                                JOptionPane.showMessageDialog(null, NONAUTHORIZED_COMMENT_USER, "ERROR", JOptionPane.ERROR_MESSAGE);
                            }
                        } else if (e.getSource() == addComment) {
                            //opens a new window with all of the post's comments
                            String content = JOptionPane.showInputDialog(null, "Comment on this post", "Comment", JOptionPane.PLAIN_MESSAGE);
                            Comment newComment = commentService.createComment(currentPost.getUuid(), content, currentUser.getUsername());
                            comments = commentService.getCommentsByPost(currentPost);
                            commentIndex = 0;
                            commentLabel.setText(comments.get(0).toString());
                        }
                    }
                };

                addComment.addActionListener(actionListener);
                edit.addActionListener(actionListener);
                delete.addActionListener(actionListener);

                previous.addActionListener(actionListener);
                next.addActionListener(actionListener);

                actionPanel.add(edit);
                actionPanel.add(delete);
                actionPanel.add(addComment);


                container.add(previous, BorderLayout.WEST);
                container.add(next, BorderLayout.EAST);
                container.add(actionPanel, BorderLayout.SOUTH);

                container.add(commentLabel, BorderLayout.CENTER);

                commentFrame.setSize(600, 400);
                commentFrame.setLocationRelativeTo(null);
                commentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                commentFrame.setVisible(true);
            }
        });


    }
/*
    private List<Post> getTestPosts() {
        List<Post> posts = new ArrayList<>();
        Post post = new Post(new Date(), "post 1", "daniel");
        post.setUuid(UUID.randomUUID());
        posts.add(post);
        post = new Post(new Date(), "post 2", "william");
        post.setUuid(UUID.randomUUID());
        posts.add(post);
        return posts;
    }

    private List<Comment> getTestComments(Post post) {
        List<Comment> comments = new ArrayList<>();
        Comment comment = new Comment(post.getUuid(), new Date(), "comment 1", "daniel");
        comment.setUuid(UUID.randomUUID());
        comments.add(comment);
        comment = new Comment(post.getUuid(), new Date(), "comment 2", "william");
        comment.setUuid(UUID.randomUUID());
        comments.add(comment);
        return comments;
    }
    */


    public static void main(String[] args) {

        SwingUtilities.invokeLater(new App());
    }
}
