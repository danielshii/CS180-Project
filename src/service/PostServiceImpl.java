package service;

import enumeration.ObjectType;
import exception.UserNotAuthorizedException;
import model.Comment;
import model.Post;

import java.io.*;
import java.util.*;

public class PostServiceImpl implements PostService {

    private final FileService fileService;

    private final CommentService commentService;

    public PostServiceImpl() {
        this.fileService = new FileServiceImpl();
        this.commentService = new CommentServiceImpl();
    }

    @Override
    public List<Post> getAllPosts() {
        List<Post> posts = new ArrayList<>();
        File directory = new File("posts");
        directory.mkdir();
        File[] files = directory.listFiles();
        Arrays.sort(files, Comparator.comparingLong(File::lastModified).reversed());


        for (File f : files) {
            try (ObjectInputStream inputStream = new ObjectInputStream((new FileInputStream(f.getAbsolutePath())))) {
                Post post = (Post) inputStream.readObject();
                posts.add(post);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return posts;
    }

    @Override
    public List<Post> getPostsByUser(String username) {
        List<Post> posts = new ArrayList<>();
        File directory = new File("posts");
        File[] files = directory.listFiles();
        for (File f : files) {
            try (ObjectInputStream inputStream = new ObjectInputStream((new FileInputStream(f.getAbsolutePath())))) {
                Post post = (Post) inputStream.readObject();
                if (post.getCreatedUsername().equals(username)) {
                    posts.add(post);
                }
            } catch (Exception e) {

            }
        }
        return posts;
    }

    @Override
    public Post createPost(String content, String createdUsername) {
        Post post = new Post();
        post.setContent(content);
        post.setCreatedUsername(createdUsername);
        post.setUuid(UUID.randomUUID());
        post.setCreatedDate(new Date());
        post.setComments(new ArrayList<>());
        fileService.savePostToFile(post);
        return post;
    }

    @Override
    public void deletePostsByUser(String username) {
        List<Post> posts = getPostsByUser(username);
        for (Post p : posts) {
            deletePost(p, username);
        }
    }

    @Override
    public void deletePost(Post post, String username) {
        List<Comment> comments = commentService.getCommentsByPost(post);
        for (Comment c : comments) {
            commentService.deleteComment(c, username);
        }
        fileService.deleteFile(ObjectType.POST, post.getUuid().toString(), username);
    }

    @Override
    public void editPost(UUID uuid, String content, String createdUsername) {
        File path = new File("posts");
        File file = new File(path.getAbsolutePath() + "/" + uuid.toString());
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file.getAbsolutePath()))) {
            Post post = (Post) inputStream.readObject();
            if (!post.getCreatedUsername().equals(createdUsername)) {
                throw new UserNotAuthorizedException();
            } else {
                post = new Post(uuid, new Date(), content, createdUsername, post.getComments());
                fileService.savePostToFile(post);

            }
        } catch (Exception e) {

        }
    }
}
