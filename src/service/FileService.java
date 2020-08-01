package service;

import enumeration.ObjectType;
import model.Comment;
import model.Post;
import model.User;

import java.io.File;

public interface FileService {
    File getFile(ObjectType objectType, String fileName);

    void saveUserToFile(User user);
    void savePostToFile(Post post);
    void saveCommentToFile(Comment comment);

    void deleteFile(ObjectType objectType, String fileName, String username);
    void deleteAllFilesByUser(User user);
    File getFolder(String folderName);
}
