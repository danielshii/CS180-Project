package service;

import enumeration.ContentType;
import model.Comment;
import model.Post;
import model.User;

public interface FileService {
    void saveUserToFile(User user);
    void savePostToFile(Post post);
    void saveCommentToFile(Comment comment);

    void deleteFile(ContentType contentType, String fileName, String username);
    void deleteAllFilesByUser(User user);
}
