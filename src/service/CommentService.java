package service;

import model.Comment;
import model.Post;

import java.util.List;
import java.util.UUID;

public interface CommentService {
    List<Comment> getCommentsByPost(Post post);
    Comment createComment(UUID postUuid, String content, String createdUsername);
    void deleteComment(Comment comment, String username);
    void storeComment(Comment comment);
    //void deleteCommentsByUser(String username);
}
