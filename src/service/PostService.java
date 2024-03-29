package service;

import model.Post;

import java.util.List;
import java.util.UUID;

public interface PostService {
    List<Post> getAllPosts();
    List<Post> getPostsByUser(String username);
    Post createPost(String content, String createdUsername);
    void deletePostsByUser(String username);
    void deletePost(Post post, String username);
    void editPost(UUID uuid, String content, String createdUsername);
}
