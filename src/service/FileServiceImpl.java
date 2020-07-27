package service;

import enumeration.ContentType;
import exception.CommentNotFoundException;
import exception.PostNotFoundException;
import exception.UserNotAuthorizedException;
import exception.UserNotFoundException;
import model.Comment;
import model.Post;
import model.User;

import java.io.*;

public class FileServiceImpl implements FileService {
    private static final String USER_FOLDER = "users";
    private static final String POST_FOLDER = "posts";
    private static final String COMMENT_FOLDER = "comments";

    @Override
    public void saveUserToFile(User user) {
        File folder = getFolder(USER_FOLDER);
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(folder.getAbsolutePath() + "/" + user.getUsername(), false))) {
            outputStream.writeObject(user);
            //outputStream.writeUTF("\n");
            outputStream.flush();
        } catch (IOException e) {

        }
    }

    @Override
    public void savePostToFile(Post post) {
        File folder = getFolder(POST_FOLDER);
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(folder.getAbsolutePath() + "/" + post.getUuid().toString(), false))) {
            outputStream.writeObject(post);
            //outputStream.writeUTF("\n");
            outputStream.flush();
        } catch (IOException e) {

        }
    }

    @Override
    public void saveCommentToFile(Comment comment) {
        File folder = getFolder(COMMENT_FOLDER);
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(folder.getAbsolutePath() + "/" + comment.getUuid().toString(), false))) {
            outputStream.writeObject(comment);
            //outputStream.writeUTF("\n");
            outputStream.flush();
        } catch (IOException e) {

        }
    }

    @Override
    public void deleteFile(ContentType contentType, String fileName, String username) {
        File path = getFolder(contentType.getFolderName());
        File file = new File(path.getAbsolutePath() + "/" + fileName);
        switch (contentType) {
            case USER:
                if (file.delete()) {
                } else {
                    throw new UserNotFoundException();
                }
                break;

            case POST:

                try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file.getAbsolutePath()))) {
                    Post post = (Post) inputStream.readObject();
                    if (!post.getCreatedUsername().equals(username)) {
                        throw new UserNotAuthorizedException();
                    } else {
                        if (file.delete()) {

                        } else {
                            throw new PostNotFoundException();
                        }
                    }

                } catch (Exception e) {

                }
                break;

            case COMMENT:
                try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file.getAbsolutePath()))) {
                    Comment comment = (Comment) inputStream.readObject();
                    if (!comment.getCreatedUsername().equals(username)) {
                        throw new UserNotAuthorizedException();
                    } else {
                        if (file.delete()) {

                        } else {
                            throw new CommentNotFoundException();
                        }
                    }
                } catch (Exception e) {

                }
                break;

            default:
                break;
        }

    }

    @Override
    public void deleteAllFilesByUser(User user) {
        File file = getFolder(COMMENT_FOLDER);
        File[] commentFiles = file.listFiles();
        for (File f : commentFiles) {
            deleteFile(ContentType.COMMENT, f.getName(), user.getUsername());
        }
        file = getFolder(POST_FOLDER);
        File[] postFiles = file.listFiles();
        for (File f : postFiles) {
            deleteFile(ContentType.POST, f.getName(), user.getUsername());
        }
        deleteFile(ContentType.USER, user.getUsername(), user.getUsername());
    }

    private File getFolder(String folderName) {
        File file = new File(folderName);
        boolean b = file.mkdir();
        return file;
    }
}
