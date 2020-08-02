package test;


import model.Comment;
import model.Post;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import service.CommentService;
import service.CommentServiceImpl;
import service.UserServiceImpl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


public class CommentServiceTest {
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
        private CommentService commentService;

        @Before
        public void start() {
            commentService = new CommentServiceImpl();
        }


        @Test
        public void testFields() {
            Class<?> clazz = CommentService.class;
            Field[] fields = clazz.getDeclaredFields();
            assertTrue("Make sure the field for CommentService is empty!", fields.length == 0);
        }

        @Test
        public void testExistsAndInheritsFromObject() {
            Class<?> clazz = CommentService.class;
            assertTrue("Ensure that your file CommentService.java extends Objects!", Object.class.isInstance(clazz));
        }


        @Test
        public void testIsInterfaceHasAllMethods() {
            Class<?> clazz = CommentService.class;
            assertTrue("Ensure that your file CommentService.java is an interface!", clazz.isInterface());
            Method getCommentsByPost;
            Method createComment;
            Method deleteComment;
            Method storeComment;
            Method editComment;
            Method getCommentsByUser;

            try {
                getCommentsByPost = CommentService.class.getDeclaredMethod("getCommentsByPost", Post.class);
                if (!getCommentsByPost.getReturnType().equals(List.class)) {
                    fail("Ensure that your getCommentsByPost() method in CommentService return type is of type " +
                            "List!");
                    return;
                }
            } catch (NoSuchMethodException e) {
                fail("Ensure that you have a method called getCommentsByPost() in the CommentService interface!");
                return;
            }

            try {
                createComment = CommentService.class.getDeclaredMethod("createComment", UUID.class, String.class, String.class);
                if (!createComment.getReturnType().equals(Comment.class)) {
                    fail("Ensure that your createComment() method in CommentService return type is of type " +
                            "Comment!");
                    return;
                }
            } catch (NoSuchMethodException e) {
                fail("Ensure that you have a method called createComment() in the CommentService interface!");
                return;
            }

            try {
                deleteComment = CommentService.class.getDeclaredMethod("deleteComment", Comment.class, String.class);
                if (!deleteComment.getReturnType().equals(void.class)) {
                    fail("Ensure that your deleteComment() method in CommentService return type is of type " +
                            "void!");
                    return;
                }
            } catch (NoSuchMethodException e) {
                fail("Ensure that you have a method called deleteComment() in the CommentService interface!");
                return;
            }

            try {
                storeComment = CommentService.class.getDeclaredMethod("storeComment", Comment.class);
                if (!storeComment.getReturnType().equals(void.class)) {
                    fail("Ensure that your storeComment() method in CommentService return type is of type " +
                            "void!");
                    return;
                }
            } catch (NoSuchMethodException e) {
                fail("Ensure that you have a method called storeComment() in the CommentService interface!");
                return;
            }

            try {
                editComment = CommentService.class.getDeclaredMethod("editComment", UUID.class, UUID.class, String.class, String.class);
                if (!editComment.getReturnType().equals(void.class)) {
                    fail("Ensure that your editComment() method in CommentService return type is of type " +
                            "void!");
                    return;
                }
            } catch (NoSuchMethodException e) {
                fail("Ensure that you have a method called editComment() in the CommentService interface!");
                return;
            }

            try {
                getCommentsByUser = CommentService.class.getDeclaredMethod("getCommentsByUser", String.class);
                if (!getCommentsByUser.getReturnType().equals(List.class)) {
                    fail("Ensure that your getCommentsByUser() method in CommentService return type is of type " +
                            "List!");
                    return;
                }
            } catch (NoSuchMethodException e) {
                fail("Ensure that you have a method called getCommentsByUser() in the CommentService interface!");
                return;
            }
        }


    }

}