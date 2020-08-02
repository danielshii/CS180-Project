import enumeration.ActionType;
import model.Comment;
import model.Post;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientApp implements Serializable {
    private static ClientApp app;
    private User currentUser;
    private int postIndex = 0;
    private int postIndexByUser = 0;
    private int commentIndex = 0;
    private int commentIndexByUser = 0;
    private static final String NONAUTHORIZED_POST_USER = "You are not the author of this post";
    private static final String NONAUTHORIZED_COMMENT_USER = "You are not the author of this comment";
    private String errorMessage = "";
    private static final String[] loginOptions = new String[]{"Create account", "Login", "Quit"};
    private List<Post> posts = new ArrayList<>();
    private List<Post> userPosts = new ArrayList<>();
    private List<Comment> comments = new ArrayList<>();
    private List<Comment> userComments = new ArrayList<>();
    private ActionType actionType;
    private Post currentPost;
    private Post currentUserPost;
    private Comment currentComment;
    private Comment currentUserComment;
    private String postContent;
    private String username;

    private static Socket socket;
    private static BufferedReader reader;
    private static PrintWriter writer;

    private static OutputStream outputStream;
    private static ObjectOutputStream objectOutputStream;

    private static InputStream inputStream;
    private static ObjectInputStream objectInputStream;

    public ClientApp() throws IOException {
        socket = new Socket("localhost", 4242);
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new PrintWriter(socket.getOutputStream());

        outputStream = socket.getOutputStream();
        objectOutputStream = new ObjectOutputStream(outputStream);

        inputStream = socket.getInputStream();
        objectInputStream = new ObjectInputStream(inputStream);
    }

    public ClientApp(User currentUser) {

        this.currentUser = currentUser;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public Post getCurrentUserPost() {
        return currentUserPost;
    }

    public void setCurrentUserPost(Post currentUserPost) {
        this.currentUserPost = currentUserPost;
    }

    public int getPostIndex() {
        return postIndex;
    }

    public void setPostIndex(int postIndex) {
        this.postIndex = postIndex;
    }

    public int getCommentIndex() {
        return commentIndex;
    }

    public void setCommentIndex(int commentIndex) {
        this.commentIndex = commentIndex;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public List<Comment> getUserComments() {
        return userComments;
    }

    public void setUserComments(List<Comment> userComments) {
        this.userComments = userComments;
    }

    public List<Post> getUserPosts() {
        return userPosts;
    }

    public void setUserPosts(List<Post> userPosts) {
        this.userPosts = userPosts;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public Comment getCurrentUserComment() {
        return currentUserComment;
    }

    public void setCurrentUserComment(Comment currentUserComment) {
        this.currentUserComment = currentUserComment;
    }

    public int getIndex() {
        return postIndex;
    }

    public void setIndex(int index) {
        this.postIndex = index;
    }

    public Post getCurrentPost() {
        return currentPost;
    }

    public void setCurrentPost(Post currentPost) {
        this.currentPost = currentPost;
    }

    public Comment getCurrentComment() {
        return currentComment;
    }

    public void setCurrentComment(Comment currentComment) {
        this.currentComment = currentComment;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private void createNewConnection() throws IOException {
        socket = new Socket("localhost", 4242);
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new PrintWriter(socket.getOutputStream());
        objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectInputStream = new ObjectInputStream(socket.getInputStream());
    }

    public void runApp() throws IOException, ClassNotFoundException {
        JFrame postFrame = new JFrame("BoilerGram");
        postFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel postLabel = new JLabel("");
        JLabel currentUserLabel = new JLabel("Now logged in as " + app.getCurrentUser().getUsername());
        currentUserLabel.setHorizontalAlignment(SwingConstants.CENTER);
        currentUserLabel.setVerticalAlignment(SwingConstants.NORTH);
        JButton viewPostsByUser = new JButton();
        postLabel.setHorizontalAlignment(SwingConstants.CENTER);


        JPanel actionPanel = new JPanel();
        JPanel topPanel = new JPanel();
        Container container = postFrame.getContentPane();
        container.setLayout(new BorderLayout());

        JButton previous = new JButton("Previous Post");
        JButton next = new JButton("Next Post");
        JButton addPost = new JButton("Add Post");
        JButton edit = new JButton("Edit Post");
        JButton delete = new JButton("Delete Post");
        JButton comment = new JButton("View Comments");
        JButton refreshPosts = new JButton("Refresh");
        JButton logout = new JButton("Log Out");
        JButton deleteAcc = new JButton("Delete Account");

        actionPanel.add(addPost);
        actionPanel.add(edit);
        actionPanel.add(delete);
        actionPanel.add(comment);
        actionPanel.add(refreshPosts);

        topPanel.add(currentUserLabel);
        topPanel.add(logout);
        topPanel.add(deleteAcc);


        container.add(postLabel, BorderLayout.CENTER);
        container.add(topPanel, BorderLayout.NORTH);

        container.add(previous, BorderLayout.WEST);
        container.add(next, BorderLayout.EAST);
        container.add(actionPanel, BorderLayout.SOUTH);

        postFrame.setSize(1000, 800);
        postFrame.setLocationRelativeTo(null);
        postFrame.setVisible(true);


        //posts = postService.getAllPosts();
        app.setActionType(ActionType.GET_ALL_POSTS);
        app.createNewConnection();
        objectOutputStream.writeObject(app);
        objectOutputStream.flush();

        app = (ClientApp) objectInputStream.readObject();

        posts = app.getPosts();
        if (!posts.isEmpty()) {
            app.setCurrentPost(posts.get(0));
            postLabel.setText(posts.get(0).toString());

            viewPostsByUser.setText("View all posts by " + posts.get(postIndex).getCreatedUsername());
            actionPanel.add(viewPostsByUser);


        } else {
            postLabel.setText("No one has posted anything. Be the first to post!");
        }


        ActionListener actionListener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    app.createNewConnection();
                    if (e.getSource() == previous) {
                        if (postIndex == 0) {
                            //error
                        } else {
                            postIndex--;
                            Post post = posts.get(postIndex);
                            app.setCurrentPost(post);
                            postLabel.setText(post.toString());
                            viewPostsByUser.setText("View all posts by " + posts.get(postIndex).getCreatedUsername());

                        }
                    } else if (e.getSource() == next) {
                        if (postIndex >= posts.size() - 1) {

                        } else {
                            postIndex++;
                            Post post = posts.get(postIndex);
                            app.setCurrentPost(post);
                            postLabel.setText(post.toString());
                            viewPostsByUser.setText("View all posts by " + posts.get(postIndex).getCreatedUsername());
                        }
                    } else if (e.getSource() == edit) {
                        if (posts.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "No posts to delete", "ERROR", JOptionPane.ERROR_MESSAGE);
                        } else {
                            currentPost = app.getCurrentPost();
                            if (currentPost.getCreatedUsername().equals(currentUser.getUsername())) {

                                String newContent = JOptionPane.showInputDialog(null, "Edit Content", "Edit Post", JOptionPane.PLAIN_MESSAGE);
                                if (newContent != null) {
                                    //postService.editPost(currentPost.getUuid(), newContent, currentUser.getUsername());
                                    app.setActionType(ActionType.EDIT_POST);
                                    app.setPostContent(newContent);
                                    objectOutputStream.writeObject(app);

                                    app = (ClientApp) objectInputStream.readObject();
                                    if (app.getErrorMessage().isEmpty()) {
                                        posts = app.getPosts();
                                        postIndex = 0;
                                        postLabel.setText(posts.get(0).toString());
                                    } else {
                                        JOptionPane.showMessageDialog(null, NONAUTHORIZED_POST_USER, "ERROR", JOptionPane.ERROR_MESSAGE);
                                    }
                                } else {
                                    JOptionPane.showMessageDialog(null, "Post content cannot be empty.", "ERROR", JOptionPane.ERROR_MESSAGE);
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, NONAUTHORIZED_POST_USER, "ERROR", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    } else if (e.getSource() == delete) {
                        if (posts.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "No posts to delete", "ERROR", JOptionPane.ERROR_MESSAGE);
                        } else {

                            currentPost = posts.get(postIndex);

                            app.setActionType(ActionType.DELETE_POST);
                            objectOutputStream.writeObject(app);
                            objectOutputStream.flush();
                            app = (ClientApp) objectInputStream.readObject();
                            if (app.getErrorMessage().isEmpty()) {
                                posts = app.getPosts();
                                postIndex = 0;
                                if (posts.size() > 0) {
                                    postLabel.setText(posts.get(0).toString());

                                    viewPostsByUser.setText("View all posts by " + posts.get(postIndex).getCreatedUsername());
                                    actionPanel.add(viewPostsByUser);

                                } else {
                                    postLabel.setText("No one has posted anything. Be the first to post!");
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, app.getErrorMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
                            }

                        }
                    } else if (e.getSource() == comment) {
                        //opens a new window with all of the post's comments
                        if (posts.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "No posts available.", "ERROR", JOptionPane.ERROR_MESSAGE);
                        } else {
                            currentPost = posts.get(postIndex);

                            openCommentWindow(currentPost);
                        }

                    } else if (e.getSource() == addPost) {
                        String content = JOptionPane.showInputDialog(null, "Write your post", "Add Post", JOptionPane.QUESTION_MESSAGE);
                        app.setActionType(ActionType.CREATE_POST);
                        app.setPostContent(content);
                        objectOutputStream.writeObject(app);
                        objectOutputStream.flush();
                        app = (ClientApp) objectInputStream.readObject();
                        Post newPost = app.getCurrentPost();
                        posts = app.getPosts();

                        postIndex = 0;
                        postLabel.setText(newPost.toString());
                        viewPostsByUser.setText("View all posts by " + posts.get(postIndex).getCreatedUsername());
                        actionPanel.add(viewPostsByUser);


                    } else if (e.getSource() == viewPostsByUser) {
                        currentPost = posts.get(postIndex);
                        openPostWindowByUser(currentPost.getCreatedUsername());
                    } else if (e.getSource() == refreshPosts) {
                        app.setActionType(ActionType.GET_ALL_POSTS);
                        objectOutputStream.writeObject(app);
                        objectOutputStream.flush();
                        app = (ClientApp) objectInputStream.readObject();
                        posts = app.getPosts();
                        postIndex = 0;
                        if (posts.size() > 0) {
                            postLabel.setText(posts.get(0).toString());
                        } else {
                            postLabel.setText("No one has posted anything. Be the first to post!");
                        }
                    } else if (e.getSource() == logout) {
                        postFrame.dispose();
                        startApp();
                    } else if (e.getSource() == deleteAcc) {

                        app.setActionType(ActionType.DELETE_USER);
                        objectOutputStream.writeObject(app);
                        objectOutputStream.flush();
                        app = (ClientApp) objectInputStream.readObject();
                        postFrame.dispose();
                        startApp();
                    }
                } catch (IOException | ClassNotFoundException exception) {
                    exception.printStackTrace();
                }
            }
        };
        previous.addActionListener(actionListener);
        next.addActionListener(actionListener);

        addPost.addActionListener(actionListener);
        edit.addActionListener(actionListener);
        delete.addActionListener(actionListener);
        comment.addActionListener(actionListener);
        viewPostsByUser.addActionListener(actionListener);
        refreshPosts.addActionListener(actionListener);
        deleteAcc.addActionListener(actionListener);
        logout.addActionListener(actionListener);


    }


    public void openCommentWindow(Post currentPost) {
        commentIndex = 0;
        app.setCurrentPost(currentPost);
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    app.createNewConnection();
                    JFrame commentFrame = new JFrame("BoilerGram");

                    //comments = commentService.getCommentsByPost(currentPost);
                    app.setActionType(ActionType.GET_COMMENTS);
                    objectOutputStream.writeObject(app);
                    objectOutputStream.flush();
                    app = (ClientApp) objectInputStream.readObject();
                    comments = app.getComments();

                    JPanel actionPanel = new JPanel();
                    Container container = commentFrame.getContentPane();
                    container.setLayout(new BorderLayout());


                    JButton viewCommentsByUser = new JButton();

                    JLabel commentLabel = new JLabel("");
                    if (comments.size() > 0) {
                        app.setCurrentComment(comments.get(0));
                        commentLabel.setText(comments.get(0).toString());
                        viewCommentsByUser.setText("View all comments by " + comments.get(0).getCreatedUsername());
                    } else {
                        commentLabel.setText("No one has commented yet. Be the first to comment!");
                    }
                    commentLabel.setHorizontalAlignment(SwingConstants.CENTER);

                    JButton previous = new JButton("Previous Comment");
                    JButton next = new JButton("Next Comment");
                    JButton edit = new JButton("Edit Comment");
                    JButton delete = new JButton("Delete Comment");
                    JButton addComment = new JButton("Add Comment");
                    JButton refreshComments = new JButton("Refresh");

                    ActionListener actionListener = new ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent e) {

                            try {
                                app.createNewConnection();
                                if (e.getSource() == previous) {
                                    if (commentIndex == 0) {
                                        //error
                                    } else {
                                        commentIndex--;
                                        Comment comment = comments.get(commentIndex);
                                        app.setCurrentComment(comment);
                                        commentLabel.setText(comment.toString());
                                        viewCommentsByUser.setText("View all comments by " + comments.get(commentIndex).getCreatedUsername());
                                        actionPanel.repaint();
                                    }
                                } else if (e.getSource() == next) {
                                    if (commentIndex >= comments.size() - 1) {

                                    } else {
                                        commentIndex++;
                                        Comment comment = comments.get(commentIndex);
                                        app.setCurrentComment(comment);
                                        commentLabel.setText(comment.toString());
                                        viewCommentsByUser.setText("View all comments by " + comments.get(commentIndex).getCreatedUsername());
                                        actionPanel.repaint();
                                    }
                                } else if (e.getSource() == edit) {
                                    if (comments.size() == 0) {
                                        JOptionPane.showMessageDialog(null, "No comments to edit", "ERROR", JOptionPane.ERROR_MESSAGE);
                                    } else {
                                        currentComment = comments.get(commentIndex);
                                        if (currentComment.getCreatedUsername().equals(currentUser.getUsername())) {
                                            String newContent = JOptionPane.showInputDialog(null, "Edit your comment", "Edit Comment", JOptionPane.PLAIN_MESSAGE);
                                            if (newContent == null) {
                                                JOptionPane.showMessageDialog(null, "Comment cannot be empty.", "ERROR", JOptionPane.ERROR_MESSAGE);
                                            } else {
                                                app.setActionType(ActionType.EDIT_COMMENT);
                                                objectOutputStream.writeObject(app);
                                                objectOutputStream.flush();

                                                app = (ClientApp) objectInputStream.readObject();

                                                if (app.getErrorMessage().isEmpty()) {
                                                    comments = app.getComments();
                                                    commentIndex = 0;
                                                    app.setCurrentComment(comments.get(0));
                                                    commentLabel.setText(comments.get(0).toString());
                                                } else {
                                                    JOptionPane.showMessageDialog(null, app.getErrorMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
                                                }
                                            }
                                        } else {
                                            JOptionPane.showMessageDialog(null, NONAUTHORIZED_COMMENT_USER, "ERROR", JOptionPane.ERROR_MESSAGE);
                                        }
                                    }
                                } else if (e.getSource() == delete) {
                                    if (comments.size() == 0) {
                                        JOptionPane.showMessageDialog(null, "No comments to delete.", "ERROR", JOptionPane.ERROR_MESSAGE);
                                    } else {

                                        currentComment = comments.get(commentIndex);

                                        app.setActionType(ActionType.DELETE_COMMENT);
                                        objectOutputStream.writeObject(app);
                                        objectOutputStream.flush();
                                        app = (ClientApp) objectInputStream.readObject();


                                        if (!app.getErrorMessage().isEmpty()) {
                                            JOptionPane.showMessageDialog(null, app.getErrorMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
                                        } else {
                                            comments = app.getComments();


                                            if (comments.isEmpty()) {
                                                commentLabel.setText("No one has commented yet. Be the first to comment!");
                                                actionPanel.remove(viewCommentsByUser);
                                            } else {
                                                app.setCurrentComment(comments.get(0));
                                                commentLabel.setText(comments.get(0).toString());
                                                viewCommentsByUser.setText("View all comments by " + comments.get(commentIndex).getCreatedUsername());
                                            }
                                            actionPanel.repaint();
                                            commentIndex = 0;
                                        }


                                    }
                                } else if (e.getSource() == addComment) {
                                    //opens a new window with all of the post's comments
                                    String content = JOptionPane.showInputDialog(null, "Comment on this post", "Comment", JOptionPane.PLAIN_MESSAGE);
                                    app.setActionType(ActionType.ADD_COMMENT);
                                    app.setPostContent(content);
                                    objectOutputStream.writeObject(app);
                                    objectOutputStream.flush();
                                    app = (ClientApp) objectInputStream.readObject();

                                    comments = app.getComments();
                                    commentIndex = 0;
                                    commentLabel.setText(comments.get(0).toString());
                                    viewCommentsByUser.setText("View all comments by " + comments.get(commentIndex).getCreatedUsername());
                                    actionPanel.add(viewCommentsByUser);
                                    actionPanel.repaint();

                                } else if (e.getSource() == viewCommentsByUser) {
                                    currentComment = comments.get(commentIndex);
                                    openCommentWindowByUser(currentComment.getCreatedUsername());

                                } else if (e.getSource() == refreshComments) {
                                    app.setActionType(ActionType.GET_COMMENTS);
                                    objectOutputStream.writeObject(app);
                                    objectOutputStream.flush();
                                    comments = app.getComments();

                                    commentIndex = 0;
                                    if (comments.size() > 0) {
                                        app.setCurrentComment(comments.get(0));
                                        commentLabel.setText(comments.get(0).toString());
                                        viewCommentsByUser.setText("View all comments by " + comments.get(commentIndex).getCreatedUsername());
                                    } else {
                                        commentLabel.setText("No one has commented yet. Be the first to comment!");
                                    }
                                }
                            } catch (IOException | ClassNotFoundException exception) {
                                exception.printStackTrace();
                            }
                        }
                    };

                    addComment.addActionListener(actionListener);
                    edit.addActionListener(actionListener);
                    delete.addActionListener(actionListener);
                    viewCommentsByUser.addActionListener(actionListener);
                    refreshComments.addActionListener(actionListener);

                    previous.addActionListener(actionListener);
                    next.addActionListener(actionListener);

                    actionPanel.add(edit);
                    actionPanel.add(delete);
                    actionPanel.add(addComment);
                    actionPanel.add(refreshComments);
                    if (!comments.isEmpty()) {
                        actionPanel.add(viewCommentsByUser);
                    }


                    container.add(previous, BorderLayout.WEST);
                    container.add(next, BorderLayout.EAST);
                    container.add(actionPanel, BorderLayout.SOUTH);

                    container.add(commentLabel, BorderLayout.CENTER);

                    commentFrame.setSize(600, 400);
                    commentFrame.setLocationRelativeTo(null);
                    commentFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    commentFrame.setVisible(true);
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });


    }

    public void openPostWindowByUser(String username) {
        postIndexByUser = 0;
        app.setUsername(username);
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    app.createNewConnection();
                    JFrame postFrame = new JFrame("Posts by " + username);
                    JLabel postLabel = new JLabel("");
                    postLabel.setHorizontalAlignment(SwingConstants.CENTER);

                    app.setActionType(ActionType.GET_POSTS_BY_USER);
                    objectOutputStream.writeObject(app);
                    objectOutputStream.flush();
                    app = (ClientApp) objectInputStream.readObject();
                    userPosts = app.getUserPosts();


                    if (userPosts.size() > 0) {
                        postLabel.setText(userPosts.get(0).toString());
                    } else {
                        postLabel.setText(username + " has not posted anything yet.");
                    }


                    JPanel actionPanel = new JPanel();
                    Container container = postFrame.getContentPane();
                    container.setLayout(new BorderLayout());

                    JButton previous = new JButton("Previous Post");
                    JButton next = new JButton("Next Post");
                    JButton comment = new JButton("View Comments");
                    JButton refreshPosts = new JButton("Refresh");

                    ActionListener actionListener = new ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            try {
                                app.createNewConnection();
                                if (e.getSource() == previous) {
                                    if (postIndexByUser == 0) {
                                        //error
                                    } else {
                                        postIndexByUser--;
                                        Post post = posts.get(postIndexByUser);
                                        app.setCurrentUserPost(post);
                                        postLabel.setText(post.toString());
                                    }
                                } else if (e.getSource() == next) {
                                    if (postIndexByUser >= userPosts.size() - 1) {

                                    } else {
                                        postIndexByUser++;
                                        Post post = userPosts.get(postIndexByUser);
                                        app.setCurrentUserPost(post);
                                        postLabel.setText(post.toString());
                                    }
                                } else if (e.getSource() == comment) {
                                    //opens a new window with all of the post's comments
                                    currentUserPost = userPosts.get(postIndexByUser);

                                    openCommentWindow(currentUserPost);

                                } else if (e.getSource() == refreshPosts) {
                                    app.setActionType(ActionType.GET_POSTS_BY_USER);
                                    objectOutputStream.writeObject(app);
                                    objectOutputStream.flush();
                                    app = (ClientApp) objectInputStream.readObject();
                                    userPosts = app.getUserPosts();
                                    postIndexByUser = 0;
                                    if (userPosts.size() > 0) {
                                        postLabel.setText(userPosts.get(0).toString());
                                    } else {
                                        postLabel.setText("No one has posted anything. Be the first to post!");
                                    }
                                }
                            } catch (IOException |
                                    ClassNotFoundException exception) {
                                exception.printStackTrace();
                            }
                        }
                    };
                    previous.addActionListener(actionListener);
                    next.addActionListener(actionListener);
                    comment.addActionListener(actionListener);
                    refreshPosts.addActionListener(actionListener);

                    actionPanel.add(comment);
                    actionPanel.add(refreshPosts);

                    container.add(postLabel, BorderLayout.CENTER);


                    container.add(previous, BorderLayout.WEST);
                    container.add(next, BorderLayout.EAST);
                    container.add(actionPanel, BorderLayout.SOUTH);

                    postFrame.setSize(600, 400);
                    postFrame.setLocationRelativeTo(null);
                    postFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    postFrame.setVisible(true);


                } catch (IOException |
                        ClassNotFoundException exception) {
                    exception.printStackTrace();
                }
            }
        });
    }

    public void openCommentWindowByUser(String username) {
        commentIndexByUser = 0;
        app.setUsername(username);
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {

                    app.createNewConnection();

                    JFrame commentFrame = new JFrame("Comments by " + username);

                    app.setActionType(ActionType.GET_COMMENTS_BY_USER);
                    objectOutputStream.writeObject(app);

                    app = (ClientApp) objectInputStream.readObject();
                    userComments = app.getUserComments();

                    JPanel actionPanel = new JPanel();
                    Container container = commentFrame.getContentPane();
                    container.setLayout(new BorderLayout());


                    JLabel commentLabel = new JLabel("");
                    if (userComments.size() > 0) {
                        commentLabel.setText(userComments.get(0).toString());

                    } else {
                        commentLabel.setText(username + " does not have any comments.");
                    }
                    commentLabel.setHorizontalAlignment(SwingConstants.CENTER);

                    JButton previous = new JButton("Previous Comment");
                    JButton next = new JButton("Next Comment");
                    JButton refreshComments = new JButton("Refresh");

                    ActionListener actionListener = new ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            try {
                                app.createNewConnection();
                                if (e.getSource() == previous) {
                                    if (commentIndexByUser == 0) {
                                        //error
                                    } else {
                                        commentIndexByUser--;
                                        Comment comment = userComments.get(commentIndexByUser);
                                        commentLabel.setText(comment.toString());


                                    }
                                } else if (e.getSource() == next) {
                                    if (commentIndexByUser >= userComments.size() - 1) {

                                    } else {
                                        commentIndexByUser++;
                                        Comment comment = userComments.get(commentIndexByUser);
                                        commentLabel.setText(comment.toString());

                                    }
                                } else if (e.getSource() == refreshComments) {
                                    app.setActionType(ActionType.GET_COMMENTS_BY_USER);
                                    objectOutputStream.writeObject(app);

                                    app = (ClientApp) objectInputStream.readObject();
                                    userComments = app.getUserComments();
                                    commentIndexByUser = 0;
                                    if (userComments.size() > 0) {
                                        commentLabel.setText(userComments.get(0).toString());
                                    } else {
                                        commentLabel.setText(username + " does not have any comments.");
                                    }
                                }
                            } catch (IOException | ClassNotFoundException exception) {
                                exception.printStackTrace();
                            }
                        }
                    };

                    previous.addActionListener(actionListener);
                    next.addActionListener(actionListener);

                    container.add(previous, BorderLayout.WEST);
                    container.add(next, BorderLayout.EAST);
                    container.add(actionPanel, BorderLayout.SOUTH);

                    container.add(commentLabel, BorderLayout.CENTER);

                    commentFrame.setSize(600, 400);
                    commentFrame.setLocationRelativeTo(null);
                    commentFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    commentFrame.setVisible(true);
                } catch (IOException | ClassNotFoundException exception) {
                    exception.printStackTrace();
                }
            }
        });
    }

    public static void startApp() {
        try {

            //int choice = JOptionPane.showOptionDialog(null, "Welcome to BoilerGram", "BoilerGram", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, loginOptions, null);
            int choice = JOptionPane.showOptionDialog(null, "Welcome to BoilerGram", "BoilerGram", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, loginOptions, null);
            app = new ClientApp();
            String username = "";
            String password = "";
            User user = null;
            switch (choice) {
                case 0:
                    while (true) {

                        username = JOptionPane.showInputDialog(null, "Create a username", "Create Account", JOptionPane.PLAIN_MESSAGE);
                        password = JOptionPane.showInputDialog(null, "Create a password", "Create Account", JOptionPane.PLAIN_MESSAGE);
                        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Username and/or password cannot be empty!", "ERROR", JOptionPane.ERROR_MESSAGE);
                            app.createNewConnection();
                            continue;
                        } else {
                            user = new User(username, password);

                            app.setCurrentUser(user);
                            app.setActionType(ActionType.ADD_USER);
                            objectOutputStream.writeObject(app);
                            objectOutputStream.flush();
                            app = (ClientApp) objectInputStream.readObject();
                            if (!app.getErrorMessage().isEmpty()) {
                                JOptionPane.showMessageDialog(null, app.getErrorMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
                                app.createNewConnection();
                                continue;
                            } else {
                                app.runApp();
                                break;
                            }
                        }
                    }
                    break;
                case 1:
                    while (true) {

                        username = JOptionPane.showInputDialog(null, "Enter username", "Create Account", JOptionPane.PLAIN_MESSAGE);
                        password = JOptionPane.showInputDialog(null, "Enter password", "Create Account", JOptionPane.PLAIN_MESSAGE);
                        user = new User(username, password);
                        app.setCurrentUser(user);
                        app.setActionType(ActionType.LOGIN_USER);

                        objectOutputStream.writeObject(app);
                        objectOutputStream.flush();
                        System.out.println("client app written: " + app.getActionType());
                        app = (ClientApp) objectInputStream.readObject();

                        if (!app.getErrorMessage().isEmpty()) {
                            JOptionPane.showMessageDialog(null, app.getErrorMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
                            app.createNewConnection();
                            continue;
                        } else {
                            app.runApp();
                            break;
                        }

                    }

                    break;
                case 2:
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        startApp();

    }
}
