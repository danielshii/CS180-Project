package service;

import enumeration.ObjectType;
import exception.*;
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
        final File directory = new File(objectType.getFolderName());
        directory.mkdir();
        final File[] files = directory.listFiles();
        for (final File file : files) {
            if (file.getName().equals(fileName)) {
                return file;
            }
        }
        return null;
    }

    @Override
    public void saveUserToFile(User user) {
        final File folder = getFolder(USER_FOLDER);
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(folder.getAbsolutePath() + "/" + user.getUsername(), false))) {
            outputStream.writeObject(user);
            //outputStream.writeUTF("\n");
            outputStream.flush();
        } catch (IOException e) {

        }
    }

    @Override
    public void savePostToFile(Post post) {
        final File folder = getFolder(POST_FOLDER);
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(folder.getAbsolutePath() + "/" + post.getUuid().toString(), false))) {
            outputStream.writeObject(post);
            //outputStream.writeUTF("\n");
            outputStream.flush();
        } catch (IOException e) {

        }
    }

    @Override
    public void saveCommentToFile(Comment comment) {
        final File folder = getFolder(COMMENT_FOLDER);
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(folder.getAbsolutePath() + "/" + comment.getUuid().toString(), false))) {
            outputStream.writeObject(comment);
            //outputStream.writeUTF("\n");
            outputStream.flush();
        } catch (IOException e) {

        }
    }

    @Override
    public void deleteFile(ObjectType objectType, String fileName, String username) {
        final File path = getFolder(objectType.getFolderName());
        final File file = new File(path.getAbsolutePath() + "/" + fileName);
        switch (objectType) {
            case USER:
                if (file.delete()) {
                } else {
                    throw new UserNotFoundException();
                }
                break;

            case POST:
                try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file.getAbsolutePath()))) {
                    final Post post = (Post) inputStream.readObject();
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
                    final Comment comment = (Comment) inputStream.readObject();
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
        // Delete all Comment files
        deleteAllPostOrCommentFilesByUser(user, ObjectType.COMMENT);

        // Delete all Post files
        deleteAllPostOrCommentFilesByUser(user, ObjectType.POST);

        // Delete the User file
        deleteFile(ObjectType.USER, user.getUsername(), user.getUsername());
    }

    @Override
    public File getFolder(String folderName) {
        final File file = new File(folderName);
        file.mkdir();
        return file;
    }

    private void deleteAllPostOrCommentFilesByUser(User user, ObjectType objectType) {
        File folder = null;
        switch (objectType) {
            case POST:
                folder = getFolder(POST_FOLDER);
                break;

            case COMMENT:
                folder = getFolder(COMMENT_FOLDER);
                break;

            default:
                throw new InvalidObjectTypeException(objectType);
        }

        final File[] files = folder.listFiles();
        for (final File file : files) {
            deleteFile(objectType, file.getName(), user.getUsername());
        }
    }
}
