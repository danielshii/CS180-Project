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
        File file = new File(path.getAbsolutePath() + "\\" + fileName);
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
                        inputStream.close();
                        if (file.delete()) {
                        } else {
                            throw new PostNotFoundException();
                        }
                    }

                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                break;

            case COMMENT:
                try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file.getAbsolutePath()))) {
                    final Comment comment = (Comment) inputStream.readObject();
                    if (!comment.getCreatedUsername().equals(username)) {
                        throw new UserNotAuthorizedException();
                    } else {
                        inputStream.close();
                        if (file.delete()) {

                        } else {
                            throw new CommentNotFoundException();
                        }
                    }
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                break;

            default:
                break;
        }

    }

    @Override
    public void deleteAllFilesByUser(User user) {
        // Delete all Comment files
        File path = getFolder(COMMENT_FOLDER);
        path.mkdir();
        File[] commentFiles = path.listFiles();
        for (File file : commentFiles) {
            try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file.getAbsolutePath()))) {
                Comment comment = (Comment) inputStream.readObject();
                if (comment.getCreatedUsername().equals(user.getUsername())) {
                    inputStream.close();
                    file.delete();
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        // Delete all Post files
        path = getFolder(POST_FOLDER);
        path.mkdir();
        File[] postFiles = path.listFiles();
        for (File file : postFiles) {
            try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file.getAbsolutePath()))) {
                Post post = (Post) inputStream.readObject();
                if (post.getCreatedUsername().equals(user.getUsername())) {
                    inputStream.close();
                    file.delete();
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        // Delete the User file
        deleteFile(ObjectType.USER, user.getUsername(), user.getUsername());
    }

    @Override
    public File getFolder(String folderName) {
        final File file = new File(folderName);
        file.mkdir();
        return file;
    }


}
