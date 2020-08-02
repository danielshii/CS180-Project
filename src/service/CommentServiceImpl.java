package service;

import enumeration.ObjectType;
import exception.PostNotFoundException;
import exception.UserNotAuthorizedException;
import model.Comment;
import model.Post;

import java.io.*;
import java.util.*;

public class CommentServiceImpl implements CommentService {

    private final FileService fileService;

    public CommentServiceImpl() {
        this.fileService = new FileServiceImpl();
    }

    @Override
    public List<Comment> getCommentsByPost(Post post) {
        List<Comment> comments = new ArrayList<>();

        File commentFolder = fileService.getFolder("comments");
        File[] commentFiles = commentFolder.listFiles();
        Arrays.sort(commentFiles, Comparator.comparingLong(File::lastModified).reversed());
        for (File f : commentFiles) {
            try (ObjectInputStream inputStream = new ObjectInputStream((new FileInputStream(f.getAbsolutePath())))) {
                Comment comment = (Comment) inputStream.readObject();
                if (comment.getPostUuid().equals(post.getUuid())) {
                    comments.add(comment);
                }
            } catch (Exception e) {

            }
        }
        return comments;
    }

    @Override
    public List<Comment> getCommentsByUser(String username) {
        List<Comment> comments = new ArrayList<>();
        File directory = new File("comments");
        File[] files = directory.listFiles();
        for (File f : files) {
            try (ObjectInputStream inputStream = new ObjectInputStream((new FileInputStream(f.getAbsolutePath())))) {
                Comment comment = (Comment) inputStream.readObject();
                if (comment.getCreatedUsername().equals(username)) {
                    comments.add(comment);
                }
            } catch (Exception e) {

            }
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
    public void editComment(UUID uuid, UUID postUuid, String content, String createdUsername) {
        File path = new File("comments");
        File file = new File(path.getAbsolutePath() + "/" + uuid.toString());
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file.getAbsolutePath()))) {
            Comment comment = (Comment) inputStream.readObject();
            if (!comment.getCreatedUsername().equals(createdUsername)) {
                throw new UserNotAuthorizedException();
            } else {
                comment = new Comment(uuid, postUuid, new Date(), content, createdUsername);
                storeComment(comment);

            }
        } catch (Exception e) {

        }
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
