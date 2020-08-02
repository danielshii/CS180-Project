package test;


import exception.UserNotAuthorizedException;
import model.Comment;
import model.Post;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import service.PostServiceImpl;
import service.PostServiceImpl;
import service.UserServiceImpl;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


public class PostServiceImplTest {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(TestCase.class);
        if (result.wasSuccessful()) {
            System.out.println("Excellent - Test ran successfully");
        } else {
            for (Failure failure : result.getFailures()) {
                System.out.println(failure.toString());
            }
        }
    }


    public static class TestCase {
        private PostServiceImpl postService;

        @Before
        public void start() {
            postService = new PostServiceImpl();
        }

        @Test
        public void testExistsAndInheritsFromObject() {
            Class<?> clazz = PostServiceImpl.class;
            assertTrue("Ensure that your file PostServiceImpl.java extends Objects!", Object.class.isInstance(clazz));
        }



        @Test
        public void testFields() {
            Class<?> clazz = PostServiceImpl.class;
            Field[] fields = clazz.getDeclaredFields();
            assertTrue("Make sure the fields are correct!", fields.length == 2);
        }

        @Test
        public void testHasAllMethods() {
            Method getAllPosts;
            Method getPostsByUser;
            Method createPost;
            Method deletePostsByUser;
            Method deletePost;
            Method editPost;

            try {
                getAllPosts = PostServiceImpl.class.getDeclaredMethod("getAllPosts", null);
                if (!getAllPosts.getReturnType().equals(List.class)) {
                    fail("Ensure that your getAllPosts() method in PostServiceImpl return type is of type " +
                            "List!");
                    return;
                }
            } catch (NoSuchMethodException e) {
                fail("Ensure that you have a method called getAllPosts() in the PostServiceImpl interface!");
                return;
            }

            try {
                getPostsByUser = PostServiceImpl.class.getDeclaredMethod("getPostsByUser", String.class);
                if (!getPostsByUser.getReturnType().equals(List.class)) {
                    fail("Ensure that your getPostsByUser() method in PostServiceImpl return type is of type " +
                            "List!");
                    return;
                }
            } catch (NoSuchMethodException e) {
                fail("Ensure that you have a method called getPostsByUser() in the PostServiceImpl interface!");
                return;
            }

            try {
                Class[] cArg = new Class[2];
                cArg[0] = String.class;
                cArg[1] = String.class;
                createPost = PostServiceImpl.class.getDeclaredMethod("createPost", cArg);
                if (!createPost.getReturnType().equals(Post.class)) {
                    fail("Ensure that your createPost() method in PostServiceImpl return type is of type " +
                            "Post!");
                    return;
                }
            } catch (NoSuchMethodException e) {
                fail("Ensure that you have a method called createPost() in the PostServiceImpl interface!");
                return;
            }

            try {
                deletePostsByUser = PostServiceImpl.class.getDeclaredMethod("deletePostsByUser", String.class);
                if (!deletePostsByUser.getReturnType().equals(void.class)) {
                    fail("Ensure that your deletePostsByUser() method in PostServiceImpl return type is of type " +
                            "void!");
                    return;
                }
            } catch (NoSuchMethodException e) {
                fail("Ensure that you have a method called deletePostsByUser() in the PostServiceImpl interface!");
                return;
            }

            try {
                Class[] cArg = new Class[2];
                cArg[0] = Post.class;
                cArg[1] = String.class;
                deletePost = PostServiceImpl.class.getDeclaredMethod("deletePost", cArg);
                if (!deletePost.getReturnType().equals(void.class)) {
                    fail("Ensure that your deletePost() method in PostServiceImpl return type is of type " +
                            "void!");
                    return;
                }
            } catch (NoSuchMethodException e) {
                fail("Ensure that you have a method called deletePost() in the PostServiceImpl interface!");
                return;
            }

            try {
                Class[] cArg = new Class[3];
                cArg[0] = UUID.class;
                cArg[1] = String.class;
                cArg[2] = String.class;
                editPost = PostServiceImpl.class.getDeclaredMethod("editPost", cArg);
                if (!editPost.getReturnType().equals(void.class)) {
                    fail("Ensure that your editPost() method in PostServiceImpl return type is of type " +
                            "void!");
                    return;
                }
            } catch (NoSuchMethodException e) {
                fail("Ensure that you have a method called editPost() in the PostServiceImpl interface!");
                return;
            }
        }

        @Test
        public void testCreatePostSuccess() {
            deletePostFiles();
            String createdUsername = "testPost4";
            String content = "testPostContent";

            Post post = postService.createPost(content, createdUsername);
            List<Post> posts = postService.getAllPosts();
            assertTrue("Ensure the createPost() method returns the expected post!", posts.size() > 0);
        }

        @Test
        public void testCreatePostFailure() {
            deletePostFiles();
            //no failures for creating posts
        }

        @Test
        public void testGetAllPostsSuccess() {
            deletePostFiles();
            String createdUsername = "testPost4";
            String content = "testPostContent";

            List<Post> posts = postService.getAllPosts();
            int initialPostCount = posts.size();
            Post post = postService.createPost(content, createdUsername);
            posts = postService.getAllPosts();
            int finalPostCount = posts.size();


            assertTrue("Ensure all posts were fetched!", initialPostCount == finalPostCount - 1);
        }

        @Test
        public void testGetAllPostsFailure() {
            deletePostFiles();
            //no failures for getting all posts
        }

        @Test
        public void testDeletePostSuccess() {
            deletePostFiles();
            String createdUsername = "testPost4";
            String content = "testPostContent";

            Post post = postService.createPost(content, createdUsername);
            List<Post> posts = postService.getAllPosts();
            int initialPostCount = posts.size();
            postService.deletePost(post, createdUsername);
            posts = postService.getAllPosts();
            int finalPostCount = posts.size();


            assertTrue("Ensure the post was deleted!", initialPostCount == finalPostCount + 1);

        }

        @Test
        public void testDeletePostFailure() {
            deletePostFiles();
            try {
                String createdUsername = "nonexistent post";
                String content = "incorrect content";
                String wrongUsername = "wrong username";
                Post post = postService.createPost(content, createdUsername);
                postService.deletePost(post, wrongUsername);

            } catch (Exception e) {
                assertTrue("Ensure UserNotAuthorizedException is thrown!", e instanceof UserNotAuthorizedException);
            }
        }

        @Test
        public void testGetAllPostsByUserSuccess() {
            deletePostFiles();
            String createdUsername = "testPost4";
            String anotherUsername = "another username";
            String content = "testPostContent";
            Post post1 = postService.createPost(content, createdUsername);
            Post post2 = postService.createPost(content, anotherUsername);
            List<Post> posts = postService.getAllPosts();
            int allPostCount = posts.size();
            posts = postService.getPostsByUser(createdUsername);
            int userPostCount = posts.size();


            assertTrue("Ensure all posts by " + createdUsername + " were fetched!", userPostCount != allPostCount);
        }

        @Test
        public void testGetAllPostsByUserFailure() {
            deletePostFiles();
            //no failures for getting all posts by a user
        }

        @Test
        public void testDeletePostsByUserSuccess() {
            deletePostFiles();
            String createdUsername = "testPost4";
            String content = "testPostContent";

            Post post = postService.createPost(content, createdUsername);
            List<Post> posts = postService.getPostsByUser(createdUsername);
            int initialPostCount = posts.size();
            postService.deletePostsByUser(createdUsername);
            posts = postService.getAllPosts();
            int finalPostCount = posts.size();


            assertTrue("Ensure the post was deleted!", initialPostCount == finalPostCount + 1);

        }

        @Test
        public void testDeletePostByUserFailure() {
            deletePostFiles();
            String createdUsername = "testPost4";
            String anotherUsername = "another username";
            String content = "testPostContent";

            Post post1 = postService.createPost(content, createdUsername);
            Post post2 = postService.createPost(content, anotherUsername);
            postService.deletePostsByUser(createdUsername);

            List<Post> posts = postService.getAllPosts();
            int allPostsCount = posts.size();

            posts = postService.getPostsByUser(createdUsername);
            int userPostsCount = posts.size();

            assertTrue("Posts by " + createdUsername + " were not deleted!", allPostsCount == userPostsCount + 1);
        }

        @Test
        public void testEditPostSuccess() {
            deletePostFiles();
            String createdUsername = "testPost4";
            String content = "testPostContent";
            String newContent = "newPostContent";
            boolean exceptionThrown = false;

            Post post = postService.createPost(content, createdUsername);

            try {
                postService.editPost(post.getUuid(), newContent, createdUsername);
            } catch (UserNotAuthorizedException e) {
                exceptionThrown = true;
            } finally {
                assertTrue("Make sure UserNotAuthorizedException isn't thrown!", !exceptionThrown);
            }
        }

        @Test
        public void testEditPostFailure() {
            deletePostFiles();
            String createdUsername = "testPost4";
            String wrongUsername = "wrong username";
            String content = "testPostContent";
            String newContent = "newPostContent";

            Post post = postService.createPost(content, createdUsername);

            try {
                postService.editPost(post.getUuid(), newContent, wrongUsername);
            } catch (Exception e) {
                assertTrue("Ensure UserNotAuthorized exception is thrown!", e instanceof UserNotAuthorizedException);
            }
        }

        private void deletePostFiles() {
            File directory = new File("posts");
            directory.mkdir();
            File[] postFiles = directory.listFiles();
            for (File file : postFiles) {
                file.delete();
            }
        }

        private Post getDummyPost(String content, String createdUsername) {
            Post post = new Post(UUID.randomUUID(), new Date(), content, createdUsername, new ArrayList<Comment>());
            return post;
        }


    }

}