package test;


import exception.UserNotAuthorizedException;
import exception.UserNotFoundException;
import model.Comment;
import model.Post;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import service.PostService;
import service.PostServiceImpl;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


public class PostServiceTest {
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
        private PostService postService;

        @Before
        public void start() {
            postService = new PostServiceImpl();
        }


        @Test
        public void testFields() {
            Class<?> clazz = PostService.class;
            Field[] fields = clazz.getFields();
            assertTrue("Make sure the field for PostService is empty!", fields.length == 0);
        }

        @Test
        public void testIsInterfaceHasAllMethods() {
            Class<?> clazz = PostService.class;
            assertTrue("Ensure that your file PostService.java is an interface!", clazz.isInterface());
            Method getAllPosts;
            Method getPostsByUser;
            Method createPost;
            Method deletePostsByUser;
            Method deletePost;
            Method editPost;

            try {
                getAllPosts = PostService.class.getDeclaredMethod("getAllPosts", null);
                if (!getAllPosts.getReturnType().equals(List.class)) {
                    fail("Ensure that your getAllPosts() method in PostService return type is of type " +
                            "List!");
                    return;
                }
            } catch (NoSuchMethodException e) {
                fail("Ensure that you have a method called getAllPosts() in the PostService interface!");
                return;
            }

            try {
                getPostsByUser = PostService.class.getDeclaredMethod("getPostsByUser", String.class);
                if (!getPostsByUser.getReturnType().equals(List.class)) {
                    fail("Ensure that your getPostsByUser() method in PostService return type is of type " +
                            "List!");
                    return;
                }
            } catch (NoSuchMethodException e) {
                fail("Ensure that you have a method called getPostsByUser() in the PostService interface!");
                return;
            }

            try {
                Class[] cArg = new Class[2];
                cArg[0] = String.class;
                cArg[1] = String.class;
                createPost = PostService.class.getDeclaredMethod("createPost", cArg);
                if (!createPost.getReturnType().equals(Post.class)) {
                    fail("Ensure that your createPost() method in PostService return type is of type " +
                            "Post!");
                    return;
                }
            } catch (NoSuchMethodException e) {
                fail("Ensure that you have a method called createPost() in the PostService interface!");
                return;
            }

            try {
                deletePostsByUser = PostService.class.getDeclaredMethod("deletePostsByUser", String.class);
                if (!deletePostsByUser.getReturnType().equals(void.class)) {
                    fail("Ensure that your deletePostsByUser() method in PostService return type is of type " +
                            "void!");
                    return;
                }
            } catch (NoSuchMethodException e) {
                fail("Ensure that you have a method called deletePostsByUser() in the PostService interface!");
                return;
            }

            try {
                Class[] cArg = new Class[2];
                cArg[0] = Post.class;
                cArg[1] = String.class;
                deletePost = PostService.class.getDeclaredMethod("deletePost", cArg);
                if (!deletePost.getReturnType().equals(void.class)) {
                    fail("Ensure that your deletePost() method in PostService return type is of type " +
                            "void!");
                    return;
                }
            } catch (NoSuchMethodException e) {
                fail("Ensure that you have a method called deletePost() in the PostService interface!");
                return;
            }

            try {
                Class[] cArg = new Class[3];
                cArg[0] = UUID.class;
                cArg[1] = String.class;
                cArg[2] = String.class;
                editPost = PostService.class.getDeclaredMethod("editPost", cArg);
                if (!editPost.getReturnType().equals(void.class)) {
                    fail("Ensure that your editPost() method in PostService return type is of type " +
                            "void!");
                    return;
                }
            } catch (NoSuchMethodException e) {
                fail("Ensure that you have a method called editPost() in the PostService interface!");
                return;
            }
        }


    }

}