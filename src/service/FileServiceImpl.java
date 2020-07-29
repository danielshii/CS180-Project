package service;

import enumeration.ObjectType;
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
    public File getFile(ObjectType objectType, String fileName) {
        File directory = new File(objectType.getFolderName());
        directory.mkdir();
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.getName().equals(fileName)) {
                return file;
            }
        }
        return null;
    }

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
    public void deleteFile(ObjectType objectType, String fileName, String username) {
        File path = getFolder(objectType.getFolderName());
        File file = new File(path.getAbsolutePath() + "/" + fileName);
        switch (objectType) {
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
            deleteFile(ObjectType.COMMENT, f.getName(), user.getUsername());
        }
        file = getFolder(POST_FOLDER);
        File[] postFiles = file.listFiles();
        for (File f : postFiles) {
            deleteFile(ObjectType.POST, f.getName(), user.getUsername());
        }
        deleteFile(ObjectType.USER, user.getUsername(), user.getUsername());
    }

    private File getFolder(String folderName) {
        File file = new File(folderName);
        boolean b = file.mkdir();
        return file;
    }
}
