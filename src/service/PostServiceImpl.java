package service;

import enumeration.ObjectType;
import exception.UserNotAuthorizedException;
import model.Post;

import java.io.*;
import java.util.*;

public class PostServiceImpl implements PostService {

    private final FileService fileService;

    public PostServiceImpl() {
        this.fileService = new FileServiceImpl();
    }

    @Override
    public List<Post> getAllPosts() {
        List<Post> posts = new ArrayList<>();
        File directory = new File("posts");
        File[] files = directory.listFiles();
        Arrays.sort(files, Comparator.comparingLong(File::lastModified));
        for (File f : files) {
            try (ObjectInputStream inputStream = new ObjectInputStream((new FileInputStream(f.getAbsolutePath())))) {
                Post post = (Post) inputStream.readObject();
                posts.add(post);
            } catch (Exception e) {

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
        fileService.savePostToFile(post);
        return post;
    }

    @Override
    public void deletePostsByUser(String username) {
        List<Post> posts = getPostsByUser(username);
        for (Post p : posts) {
            deletePost(p.getUuid(), username);
        }
    }

    @Override
    public void deletePost(UUID uuid, String username) {
        fileService.deleteFile(ObjectType.POST, uuid.toString(), username);
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
                post = new Post(new Date(), content, createdUsername);
                fileService.savePostToFile(post);

            }
        } catch (Exception e) {

        }
    }
}
