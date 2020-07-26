package service;

import exception.UserNotAuthorizedException;
import model.Comment;
import model.Post;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class CommentServiceImpl implements CommentService {
    @Override
    public List<Comment> getCommentsByPost(UUID postUuid) {
        File file = new File(postUuid.toString());
        List<Comment> comments = new ArrayList<Comment>();
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file.getAbsolutePath()))) {
            Post post = (Post) inputStream.readObject();
            comments = post.getComments();
        } catch (Exception e) {

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
        return comment;
    }

    @Override
    public void deleteComment(UUID uuid, String username) {
        File path = new File("comments");
        boolean b = path.mkdir();
        File file = new File(path.getAbsolutePath() + "/" + uuid.toString());
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file.getAbsolutePath()))) {
            Comment comment = (Comment) inputStream.readObject();
            if (!comment.getCreatedUsername().equals(username)) {
                throw new UserNotAuthorizedException();
            } else if (file.delete()) {

            }
        } catch (Exception e) {

        }
    }

    @Override
    public void storeComment(Comment comment) {
        File file = new File("comments");
        boolean b = file.mkdir();
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file.getAbsolutePath() + "/" + comment.getUuid().toString(), false))) {
            outputStream.writeObject(comment);
            //outputStream.writeUTF("\n");
            outputStream.flush();
        } catch (IOException e) {

        }
    }

    @Override
    public void deleteCommentsByUser(String username) {
        File directory = new File("comments");
        boolean b = directory.mkdir();
        File[] files = directory.listFiles();
        for (File f : files) {
            try (ObjectInputStream inputStream = new ObjectInputStream((new FileInputStream(f.getAbsolutePath())))) {
                Comment comment = (Comment) inputStream.readObject();
                if (comment.getCreatedUsername().equals(username)) {
                    deleteComment(comment.getUuid(), username);
                }
            } catch (Exception e) {

            }
        }
    }
}
