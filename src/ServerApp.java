import exception.DuplicateUsernameException;
import exception.InvalidUserException;
import exception.UserNotAuthorizedException;
import model.Comment;
import model.Post;
import model.User;
import service.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class ServerApp implements Runnable {

    static Socket socket;
    int clientNumber;

    private UserService userService;
    private PostService postService;
    private CommentService commentService;

    private InputStream inputStream;
    private static ObjectInputStream objectInputStream;
    private OutputStream outputStream;
    private static ObjectOutputStream objectOutputStream;
    private static ServerSocket serverSocket;

    public ServerApp() throws IOException {
        this.serverSocket = new ServerSocket(4242);

        this.clientNumber = clientNumber;
    }

    public ServerApp(Socket socket, int clientNumber) throws IOException {
        this.socket = socket;


    }

    public static Socket getSocket() {
        return socket;
    }

    public static void setSocket(Socket socket) {
        ServerApp.socket = socket;
    }

    public static ObjectInputStream getObjectInputStream() {
        return objectInputStream;
    }

    public static void setObjectInputStream(ObjectInputStream objectInputStream) {
        ServerApp.objectInputStream = objectInputStream;
    }

    public static ObjectOutputStream getObjectOutputStream() {
        return objectOutputStream;
    }

    public static void setObjectOutputStream(ObjectOutputStream objectOutputStream) {
        ServerApp.objectOutputStream = objectOutputStream;
    }

    /**
     * Waits for client connection, reads request from client, and returns a response
     *
     * @param args command line arguments
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException {

        //ServerApp serverApp = new ServerApp();
        int currentClient = 1;
        ServerApp server = new ServerApp();
        while (true) {
            System.out.println("Waiting for the client to connect...");

            socket = serverSocket.accept();
            server.setSocket(socket);

            server.setObjectInputStream(new ObjectInputStream(socket.getInputStream()));

            server.setObjectOutputStream(new ObjectOutputStream(socket.getOutputStream()));

            new Thread(server).start();
            System.out.printf("Client %d connected!\n", currentClient);
            currentClient++;
        }

    }


    /**
     * Processes client request
     */
    public void run() {

        try {
            userService = new UserServiceImpl();
            postService = new PostServiceImpl();
            commentService = new CommentServiceImpl();


            ClientApp clientApp = (ClientApp) objectInputStream.readObject();
            System.out.println("client app received: " + clientApp.getActionType());
            List<Post> posts = new ArrayList<>();
            List<Comment> comments = new ArrayList<>();

            switch (clientApp.getActionType()) {
                case LOGIN_USER:
                    try {
                        User user = clientApp.getCurrentUser();
                        userService.login(user.getUsername(), user.getPassword());
                        clientApp.setErrorMessage("");
                    } catch (InvalidUserException e) {
                        clientApp.setErrorMessage("Username or password was incorrect.");

                    } finally {
                        try {
                            objectOutputStream.writeObject(clientApp);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case ADD_USER:
                    try {
                        User user = clientApp.getCurrentUser();
                        User newUser = userService.createUser(user.getUsername(), user.getPassword());
                        clientApp.setCurrentUser(newUser);
                        clientApp.setErrorMessage("");
                    } catch (DuplicateUsernameException e) {
                        clientApp.setErrorMessage("That username is already taken. Please try another username.");
                    } finally {
                        objectOutputStream.writeObject(clientApp);
                    }
                    break;
                case DELETE_USER:
                    userService.deleteUser(clientApp.getCurrentUser());
                    clientApp.setErrorMessage("");
                    objectOutputStream.writeObject(clientApp);

                    break;
                case GET_ALL_POSTS:
                    posts = postService.getAllPosts();
                    clientApp.setPosts(posts);
                    clientApp.setErrorMessage("");
                    objectOutputStream.writeObject(clientApp);
                    System.out.println("client app written");
                    break;
                case GET_POSTS_BY_USER:
                    posts = postService.getPostsByUser(clientApp.getUsername());
                    clientApp.setUserPosts(posts);
                    clientApp.setErrorMessage("");
                    objectOutputStream.writeObject(clientApp);
                    break;
                case DELETE_POST:
                    try {
                        postService.deletePost(clientApp.getCurrentPost(), clientApp.getCurrentUser().getUsername());
                        posts = postService.getAllPosts();
                        clientApp.setPosts(posts);
                        if (!posts.isEmpty()) {
                            clientApp.setCurrentPost(posts.get(0));
                        } else {
                            clientApp.setCurrentPost(null);
                        }
                        clientApp.setErrorMessage("");
                    } catch (UserNotAuthorizedException e) {
                        clientApp.setErrorMessage("You are not the author of this post.");
                    } finally {
                        objectOutputStream.writeObject(clientApp);
                    }
                    break;
                case CREATE_POST:
                    Post post = postService.createPost(clientApp.getPostContent(), clientApp.getCurrentUser().getUsername());
                    posts = postService.getAllPosts();
                    clientApp.setPosts(posts);
                    clientApp.setCurrentPost(post);
                    clientApp.setErrorMessage("");
                    objectOutputStream.writeObject(clientApp);
                    break;
                case EDIT_POST:
                    try {
                        postService.editPost(clientApp.getCurrentPost().getUuid(), clientApp.getPostContent(), clientApp.getCurrentUser().getUsername());
                        posts = postService.getAllPosts();
                        clientApp.setPosts(posts);

                        clientApp.setErrorMessage("");

                    } catch (UserNotAuthorizedException e) {
                        clientApp.setErrorMessage("You are not the author of this post.");
                    } finally {
                        objectOutputStream.writeObject(clientApp);
                    }
                    break;
                case GET_COMMENTS:
                    comments = commentService.getCommentsByPost(clientApp.getCurrentPost());
                    clientApp.setErrorMessage("");
                    clientApp.setComments(comments);
                    if (comments.size() > 0) {
                        clientApp.setCurrentComment(comments.get(0));
                    } else {
                        clientApp.setCurrentComment(null);
                    }
                    objectOutputStream.writeObject(clientApp);
                    break;
                case GET_COMMENTS_BY_USER:
                    comments = commentService.getCommentsByUser(clientApp.getUsername());
                    clientApp.setErrorMessage("");
                    clientApp.setUserComments(comments);
                    if (comments.size() > 0) {
                        clientApp.setCurrentComment(comments.get(0));
                    } else {
                        clientApp.setCurrentComment(null);
                    }
                    objectOutputStream.writeObject(clientApp);
                    break;
                case EDIT_COMMENT:
                    try {
                        commentService.editComment(clientApp.getCurrentComment().getUuid(), clientApp.getCurrentComment().getPostUuid(), clientApp.getPostContent(), clientApp.getCurrentUser().getUsername());
                        comments = commentService.getCommentsByPost(clientApp.getCurrentPost());
                    } catch (UserNotAuthorizedException e) {
                        clientApp.setErrorMessage("You are not the author of this comment.");
                    } finally {
                        objectOutputStream.writeObject(clientApp);
                    }
                    break;
                case DELETE_COMMENT:
                    try {
                        commentService.deleteComment(clientApp.getCurrentComment(), clientApp.getCurrentUser().getUsername());
                        comments = commentService.getCommentsByPost(clientApp.getCurrentPost());
                        clientApp.setErrorMessage("");
                        clientApp.setComments(comments);
                        if (comments.size() > 0) {
                            clientApp.setCurrentComment(comments.get(0));
                        } else {
                            clientApp.setCurrentComment(null);
                        }
                        objectOutputStream.writeObject(clientApp);
                    } catch (UserNotAuthorizedException e) {
                        clientApp.setErrorMessage("You are not the author of this comment.");
                    } finally {
                        objectOutputStream.writeObject(clientApp);
                    }
                    break;
                case ADD_COMMENT:
                    Comment newComment = commentService.createComment(clientApp.getCurrentPost().getUuid(), clientApp.getPostContent(), clientApp.getCurrentUser().getUsername());
                    comments = commentService.getCommentsByPost(clientApp.getCurrentPost());
                    clientApp.setComments(comments);
                    clientApp.setCurrentComment(newComment);
                    objectOutputStream.writeObject(clientApp);
                    break;
            }


            objectOutputStream.flush();

        } catch (IOException | ClassNotFoundException | NullPointerException e) {
            e.printStackTrace();
        }
    }


}
