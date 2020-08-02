package test;


import enumeration.ObjectType;
import exception.CommentNotFoundException;
import exception.PostNotFoundException;
import exception.UserNotAuthorizedException;
import exception.UserNotFoundException;
import model.Comment;
import model.Post;
import model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import service.*;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


public class FileServiceImplTest {
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
        private FileService fileService;
        private UserService userService;
        private PostService postService;
        private CommentService commentService;

        @Before
        public void start() {
            fileService = new FileServiceImpl();
            userService = new UserServiceImpl();
            postService = new PostServiceImpl();
            commentService = new CommentServiceImpl();
        }


        @Test
        public void testFields() {
            Class<?> clazz = FileService.class;
            Field[] fields = clazz.getFields();
            assertTrue("Make sure the field for FileService is empty!", fields.length == 0);
        }

        @Test
        public void testHasAllMethods() {
            Method getFile;

            Method saveUserToFile;
            Method savePostToFile;
            Method saveCommentToFile;

            Method deleteFile;
            Method deleteAllFilesByUser;
            Method getFolder;

            try {
                getFile = FileService.class.getDeclaredMethod("getFile", ObjectType.class, String.class);
                if (!getFile.getReturnType().equals(File.class)) {
                    fail("Ensure that your getFile() method in FileService return type is of type " +
                            "File!");
                    return;
                }
            } catch (NoSuchMethodException e) {
                fail("Ensure that you have a method called getFile() in the FileService interface!");
                return;
            }

            try {
                saveUserToFile = FileService.class.getDeclaredMethod("saveUserToFile", User.class);
                if (!saveUserToFile.getReturnType().equals(void.class)) {
                    fail("Ensure that your saveUserToFile() method in FileService return type is of type " +
                            "void!");
                    return;
                }
            } catch (NoSuchMethodException e) {
                fail("Ensure that you have a method called saveUserToFile() in the FileService interface!");
                return;
            }

            try {
                savePostToFile = FileService.class.getDeclaredMethod("savePostToFile", Post.class);
                if (!savePostToFile.getReturnType().equals(void.class)) {
                    fail("Ensure that your savePostToFile() method in FileService return type is of type " +
                            "void!");
                    return;
                }
            } catch (NoSuchMethodException e) {
                fail("Ensure that you have a method called savePostToFile() in the FileService interface!");
                return;
            }

            try {
                saveCommentToFile = FileService.class.getDeclaredMethod("saveCommentToFile", Comment.class);
                if (!saveCommentToFile.getReturnType().equals(void.class)) {
                    fail("Ensure that your saveCommentToFile() method in FileService return type is of type " +
                            "void!");
                    return;
                }
            } catch (NoSuchMethodException e) {
                fail("Ensure that you have a method called saveCommentToFile() in the FileService interface!");
                return;
            }

            try {
                deleteFile = FileService.class.getDeclaredMethod("deleteFile", ObjectType.class, String.class, String.class);
                if (!deleteFile.getReturnType().equals(void.class)) {
                    fail("Ensure that your deleteFile() method in FileService return type is of type " +
                            "void!");
                    return;
                }
            } catch (NoSuchMethodException e) {
                fail("Ensure that you have a method called deleteFile() in the FileService interface!");
                return;
            }

            try {
                deleteAllFilesByUser = FileService.class.getDeclaredMethod("deleteAllFilesByUser", User.class);
                if (!deleteAllFilesByUser.getReturnType().equals(void.class)) {
                    fail("Ensure that your deleteAllFilesByUser() method in FileService return type is of type " +
                            "void!");
                    return;
                }
            } catch (NoSuchMethodException e) {
                fail("Ensure that you have a method called deleteAllFilesByUser() in the FileService interface!");
                return;
            }

            try {
                getFolder = FileService.class.getDeclaredMethod("getFolder", String.class);
                if (!getFolder.getReturnType().equals(File.class)) {
                    fail("Ensure that your getFolder() method in FileService return type is of type " +
                            "File!");
                    return;
                }
            } catch (NoSuchMethodException e) {
                fail("Ensure that you have a method called getFolder() in the FileService interface!");
                return;
            }
        }

        @Test
        public void testGetFileSuccess() {
            deleteFiles();
            User user = userService.createUser("testUser", "pass");
            File file = fileService.getFile(ObjectType.USER, "testUser");
            assertTrue("Make sure the file is successfully fetched!", file != null);
        }

        @Test
        public void testGetFileFailure() {
            deleteFiles();
            User user = userService.createUser("testUser", "pass");
            File file = fileService.getFile(ObjectType.USER, "nonexistent user");
            assertTrue("Make sure the program returns null!", file == null);
        }

        @Test
        public void testSaveUserToFileSuccess() {
            deleteFiles();
            User user = new User("testUser", "pass");
            fileService.saveUserToFile(user);
            File directory = new File("users");
            directory.mkdir();
            File[] files = directory.listFiles();
            assertTrue("Make sure the user is successfully saved to a file!", files.length > 0);
        }

        @Test
        public void testSaveUserToFileFailure() {
            deleteFiles();
            //no failures for saving a user to a file
        }

        @Test
        public void testSavePostToFileSuccess() {
            deleteFiles();
            Post post = new Post(UUID.randomUUID(), new Date(), "post content", "testUser", new ArrayList<>());
            fileService.savePostToFile(post);
            File directory = new File("posts");
            directory.mkdir();
            File[] files = directory.listFiles();
            assertTrue("Make sure the post is successfully saved to a file!", files.length > 0);
        }

        @Test
        public void testSavePostToFileFailure() {
            deleteFiles();
            //no failures for saving a post to a file
        }

        @Test
        public void testSaveCommentToFileSuccess() {
            deleteFiles();
            Post post = postService.createPost("post content", "testUser");
            Comment comment = new Comment(UUID.randomUUID(), post.getUuid(), new Date(), "comment content", "testUser");
            fileService.saveCommentToFile(comment);
            File directory = new File("comments");
            directory.mkdir();
            File[] files = directory.listFiles();
            assertTrue("Make sure the comment is successfully saved to a file!", files.length > 0);
        }

        @Test
        public void testSaveCommentToFileFailure() {
            deleteFiles();
            //no failures for saving a comment to a file
        }

        @Test
        public void testDeleteFileSuccess() {
            deleteFiles();
            User user = userService.createUser("testUser", "pass");
            Post post = postService.createPost("post content", "testUser");
            Comment comment = commentService.createComment(post.getUuid(), "comment content", "testUser");
            fileService.deleteFile(ObjectType.USER, "testUser", "testUser");
            File directory = new File("users");
            directory.mkdir();
            File[] files = directory.listFiles();
            int userFileCount = files.length;
            fileService.deleteFile(ObjectType.POST, post.getUuid().toString(), "testUser");
            directory = new File("posts");
            directory.mkdir();
            files = directory.listFiles();
            int postFileCount = files.length;
            fileService.deleteFile(ObjectType.COMMENT, comment.getUuid().toString(), "testUser");
            directory = new File("comments");
            directory.mkdir();
            files = directory.listFiles();
            int commentFileCount = files.length;
            assertTrue("Make sure files are successfully deleted!", postFileCount == 0 && commentFileCount == 0 && userFileCount == 0);
        }

        @Test
        public void testDeleteFileFailure() {
            deleteFiles();
            boolean correctExceptionsThrown = false;
            User user = userService.createUser("testUser", "pass");
            Post post = postService.createPost("post content", "testUser");
            Comment comment = commentService.createComment(post.getUuid(), "comment content", "testUser");
            try {
                fileService.deleteFile(ObjectType.USER, "nonexistent user", "testUser");

                fileService.deleteFile(ObjectType.POST, post.getUuid().toString(), "testUser");

                fileService.deleteFile(ObjectType.COMMENT, comment.getUuid().toString(), "testUser");
            } catch (Exception e) {
                correctExceptionsThrown |= e instanceof UserNotAuthorizedException;
                correctExceptionsThrown |= e instanceof PostNotFoundException;
                correctExceptionsThrown |= e instanceof UserNotFoundException;
                correctExceptionsThrown |= e instanceof CommentNotFoundException;
                assertTrue("Make sure an exception is thrown!", correctExceptionsThrown);
            }
        }

        @Test
        public void testDeleteFilesByUserSuccess() {
            deleteFiles();
            User user = userService.createUser("testUser", "pass");
            Post post = postService.createPost("post content", "testUser");
            Comment comment = commentService.createComment(post.getUuid(), "comment content", "testUser");

            User user2 = userService.createUser("testUser2", "pass");
            Post post2 = postService.createPost("post content", "testUser2");
            Comment comment2 = commentService.createComment(post2.getUuid(), "comment content", "testUser2");

            fileService.deleteAllFilesByUser(user);
            File directory = new File("users");
            directory.mkdir();
            File[] files = directory.listFiles();
            int userFileCount = files.length;

            directory = new File("posts");
            directory.mkdir();
            files = directory.listFiles();
            int postFileCount = files.length;

            directory = new File("comments");
            directory.mkdir();
            files = directory.listFiles();
            int commentFileCount = files.length;
            assertTrue("Make sure files are successfully deleted!", postFileCount == 1 && commentFileCount == 1 && userFileCount == 1);
        }

        @Test
        public void testDeleteFilesByUserFailure() {
            deleteFiles();
            User user = userService.createUser("testUser", "pass");
            Post post = postService.createPost("post content", "testUser");
            Comment comment = commentService.createComment(post.getUuid(), "comment content", "testUser");

            User user2 = userService.createUser("testUser2", "pass");
            Post post2 = postService.createPost("post content", "testUser2");
            Comment comment2 = commentService.createComment(post2.getUuid(), "comment content", "testUser2");

            userService.deleteUser(user);
            try {
                fileService.deleteAllFilesByUser(user);
            } catch (Exception e) {
                assertTrue("Make sure UserNotFoundException is thrown!", e instanceof UserNotFoundException);
            }
        }

        private void deleteFiles() {
            File directory = new File("comments");
            directory.mkdir();
            File[] files = directory.listFiles();
            for (File file : files) {
                file.delete();
            }
            directory = new File("posts");
            directory.mkdir();
            files = directory.listFiles();
            for (File file : files) {
                file.delete();
            }
            directory = new File("users");
            directory.mkdir();
            files = directory.listFiles();
            for (File file : files) {
                file.delete();
            }
        }


    }

}