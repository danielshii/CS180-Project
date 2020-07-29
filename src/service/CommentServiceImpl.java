package service;

import enumeration.ObjectType;
import exception.PostNotFoundException;
import model.Comment;
import model.Post;

import java.io.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class CommentServiceImpl implements CommentService {

    private FileService fileService;

    public CommentServiceImpl() {
        this.fileService = new FileServiceImpl();
    }

    @Override
    public List<Comment> getCommentsByPost(UUID postUuid) {
        File file = new File(postUuid.toString());
        List<Comment> comments;
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file.getAbsolutePath()))) {
            Post post = (Post) inputStream.readObject();
            comments = post.getComments();
        } catch (Exception e) {
            throw new PostNotFoundException();
        }
        return comments;
    }

    @Override
    public Comment createComment(UUID postUuid, String content, String createdUsername) {
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setCreatedUsername(createdUsername);
        comment.setUuid(UUID.randomUUID());
        comment.setPostUuid(postUuid);
        comment.setCreatedDate(new Date());
        storeComment(comment);
        return comment;
    }

    @Override
    public void deleteComment(Comment comment, String username) {
        fileService.deleteFile(ObjectType.COMMENT, comment.getUuid().toString(), username);
    }

    @Override
    public void storeComment(Comment comment) {
        fileService.saveCommentToFile(comment);
    }
/*
    @Override
    public void deleteCommentsByUser(String username) {
        File directory = new File("comments");
        boolean b = directory.mkdir();
        File[] files = directory.listFiles();
        for (File f : files) {
            try (ObjectInputStream inputStream = new ObjectInputStream((new FileInputStream(f.getAbsolutePath())))) {
                Comment comment = (Comment) inputStream.readObject();
                if (comment.getCreatedUsername().equals(username)) {
                    deleteComment(comment, username);
                }
            } catch (Exception e) {

            }
        }
    }*/
}
