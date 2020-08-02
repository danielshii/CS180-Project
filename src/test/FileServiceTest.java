package test;


import enumeration.ObjectType;
import model.Comment;
import model.Post;
import model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import service.FileService;
import service.FileServiceImpl;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


public class FileServiceTest {
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

        @Before
        public void start() {
            fileService = new FileServiceImpl();
        }


        @Test
        public void testFields() {
            Class<?> clazz = FileService.class;
            Field[] fields = clazz.getFields();
            assertTrue("Make sure the field for FileService is empty!", fields.length == 0);
        }

        @Test
        public void testIsInterfaceHasAllMethods() {
            Class<?> clazz = FileService.class;
            assertTrue("Ensure that your file FileService.java is an interface!", clazz.isInterface());
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


    }

}