package service;

import exception.UserNotAuthorizedException;
import model.Post;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class PostServiceImpl implements PostService {

    @Override
    public List<Post> getAllPosts() {
        List<Post> posts = new ArrayList<Post>();
        File directory = new File("posts");
        boolean b = directory.mkdir();
        File[] files = directory.listFiles();
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
        List<Post> posts = new ArrayList<Post>();
        File directory = new File("posts");
        boolean b = directory.mkdir();
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
        return post;
    }

    @Override
    public void storePost(Post post) {
        File file = new File("posts");
        boolean b = file.mkdir();
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file.getAbsolutePath() + "/" + post.getUuid().toString(), false))) {
            outputStream.writeObject(post);
            //outputStream.writeUTF("\n");
            outputStream.flush();
        } catch (IOException e) {

        }
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
        File path = new File("posts");
        boolean b = path.mkdir();
        File file = new File(path.getAbsolutePath() + "/" + uuid.toString());
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file.getAbsolutePath()))) {
            Post post = (Post) inputStream.readObject();
            if (!post.getCreatedUsername().equals(username)) {
                throw new UserNotAuthorizedException();
            } else if (file.delete()) {

            }
        } catch (Exception e) {

        }
    }

    @Override
    public void editPost(UUID uuid, String content, String createdUsername) {
        File path = new File("posts");
        boolean b = path.mkdir();
        File file = new File(path.getAbsolutePath() + "/" + uuid.toString());
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file.getAbsolutePath()))) {
            Post post = (Post) inputStream.readObject();
            if (!post.getCreatedUsername().equals(createdUsername)) {
                throw new UserNotAuthorizedException();
            } else {
                post = new Post(new Date(), content, createdUsername);
                storePost(post);

            }
        } catch (Exception e) {

        }
    }
}
