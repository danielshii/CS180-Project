package test;


import exception.UserNotAuthorizedException;
import model.Comment;
import model.Post;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import service.*;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


public class CommentServiceImplTest {
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
        private UserService userService;
        private PostService postService;
        private CommentService commentService;

        @Before
        public void start() {
            userService = new UserServiceImpl();
            postService = new PostServiceImpl();
            commentService = new CommentServiceImpl();
        }


        @Test
        public void testFields() {
            Class<?> clazz = CommentService.class;
            Field[] fields = clazz.getFields();
            assertTrue("Make sure the field for CommentServiceImpl is empty!", fields.length == 0);
        }

        @Test
        public void testHasAllMethods() {
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

        @Test
        public void testGetCommentsByPostSuccess() {
            deleteFiles();
            Post post1 = postService.createPost("content", "testUser");
            Post post2 = postService.createPost("more content", "testUser");
            commentService.createComment(post1.getUuid(), "testComment", "testUser2");
            commentService.createComment(post2.getUuid(), "testComment2", "testUser2");
            List<Comment> comments = commentService.getCommentsByPost(post1);
            assertTrue("Make sure comments are fetched from the proper post!", comments.size() == 1);
        }

        @Test
        public void testGetCommentsByPostFailure() {
            deleteFiles();
            //there are no failures for fetching comments
        }

        @Test
        public void testCreateCommentSuccess() {
            deleteFiles();
            Post post1 = postService.createPost("content", "testUser");
            commentService.createComment(post1.getUuid(), "testComment", "testUser2");
            List<Comment> comments = commentService.getCommentsByPost(post1);
            assertTrue("Make sure a comment was created!", comments.size() == 1);
        }

        @Test
        public void testCreateCommentFailure() {
            deleteFiles();
            //no failures for creating comments
        }

        @Test
        public void testDeleteCommentSuccess() {
            deleteFiles();
            Post post1 = postService.createPost("content", "testUser");
            Comment comment = commentService.createComment(post1.getUuid(), "testComment", "testUser2");
            List<Comment> comments = commentService.getCommentsByPost(post1);
            int initialCommentCount = comments.size();
            commentService.deleteComment(comment, "testUser2");
            comments = commentService.getCommentsByPost(post1);
            int finalCommentCount = comments.size();
            assertTrue("Make sure the comment was deleted!", initialCommentCount > finalCommentCount);
        }

        @Test
        public void testDeleteCommentFailure() {
            deleteFiles();
            Post post1 = postService.createPost("content", "testUser");
            Comment comment = commentService.createComment(post1.getUuid(), "testComment", "testUser2");
            try {
                commentService.deleteComment(comment, "another user");
            } catch (Exception e) {
                assertTrue("Make sure a UserNotAuthorizedException is thrown!", e instanceof UserNotAuthorizedException);
            }
        }

        @Test
        public void testStoreCommentSuccess() {
            deleteFiles();
            Post post1 = postService.createPost("content", "testUser");
            Comment comment = new Comment(UUID.randomUUID(), post1.getUuid(), new Date(), "content", "testUser2");
            commentService.storeComment(comment);
            File directory = new File("comments");
            directory.mkdir();
            File[] commentFiles = directory.listFiles();
            assertTrue("Make sure the comment was properly stored!", commentFiles.length > 0);

        }

        @Test
        public void testStoreCommentFailure() {
            deleteFiles();
            //no failures for storing comments

        }

        @Test
        public void testEditCommentSuccess() {

            deleteFiles();
            String createdUsername = "testPost4";
            String content = "testPostContent";
            String newContent = "newPostContent";
            boolean exceptionThrown = false;

            Post post = postService.createPost(content, createdUsername);
            Comment comment = commentService.createComment(post.getUuid(), "test comment", "testUser");

            try {
                commentService.editComment(comment.getUuid(), post.getUuid(), "comment edited", "testUser");
            } catch (UserNotAuthorizedException e) {
                exceptionThrown = true;
            } finally {
                assertTrue("Make sure UserNotAuthorizedException isn't thrown!", !exceptionThrown);
            }
        }

        @Test
        public void testEditCommentFailure() {

            deleteFiles();
            String createdUsername = "testPost4";
            String content = "testPostContent";
            String newContent = "newPostContent";
            boolean exceptionThrown = false;

            Post post = postService.createPost(content, createdUsername);
            Comment comment = commentService.createComment(post.getUuid(), "test comment", "testUser");

            try {
                commentService.editComment(comment.getUuid(), post.getUuid(), "comment edited", "testUser2");
            } catch (Exception e) {
                assertTrue("Make sure UserNotAuthorizedException is thrown!", e instanceof UserNotAuthorizedException);
            }
        }

        @Test
        public void testGetCommentsByUserSuccess() {
            deleteFiles();
            Post post1 = postService.createPost("content", "testUser");
            commentService.createComment(post1.getUuid(), "testComment", "testUser");
            commentService.createComment(post1.getUuid(), "testComment2", "testUser2");
            List<Comment> commentsByPost = commentService.getCommentsByPost(post1);
            List<Comment> commentsByUser = commentService.getCommentsByUser("testUser");
            assertTrue("Make sure comments are fetched from the proper user!", commentsByPost.size() != commentsByUser.size());
        }

        @Test
        public void testGetCommentsByUserFailure() {
            deleteFiles();
            //no failures for fetching comments by a user
        }

        private void deleteFiles() {
            File directory = new File("comments");
            directory.mkdir();
            File[] userFiles = directory.listFiles();
            for (File file : userFiles) {
                file.delete();
            }
            directory = new File("posts");
            directory.mkdir();
            userFiles = directory.listFiles();
            for (File file : userFiles) {
                file.delete();
            }
            directory = new File("users");
            directory.mkdir();
            userFiles = directory.listFiles();
            for (File file : userFiles) {
                file.delete();
            }
        }


    }

}