package test;

import exception.DuplicateUsernameException;
import exception.InvalidUserException;
import exception.UserNotFoundException;
import model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import service.FileService;
import service.UserServiceImpl;


import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


public class UserServiceImplTest {
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
        private UserServiceImpl userService;

        @Before
        public void start() {
            userService = new UserServiceImpl();
        }


        @Test
        public void testExistsAndInheritsFromObject() {
            Class<?> clazz = UserServiceImpl.class;
            assertTrue("Ensure that your file UserServiceImpl.java extends Objects!", Object.class.isInstance(clazz));
        }

        @Test
        public void testFields() {
            Class<?> clazz = UserServiceImpl.class;
            Field[] fields = clazz.getFields();
            assertTrue("Make sure the field for UserServiceImpl is correct!", fields.length == 0);
        }

        @Test
        public void testHasAllMethods() {
            Method getUser;
            Method createUser;
            Method deleteUser;
            Method login;

            try {
                getUser = UserServiceImpl.class.getDeclaredMethod("getUser", String.class);
                if (!getUser.getReturnType().equals(User.class)) {
                    fail("Ensure that your getUser() method in UserServiceImpl return type is of type " +
                            "User!");
                    return;
                }
            } catch (NoSuchMethodException e) {
                fail("Ensure that you have a method called getUser() in the UserServiceImpl interface!");
                return;
            }

            try {
                Class[] cArg = new Class[2];
                cArg[0] = String.class;
                cArg[1] = String.class;
                createUser = UserServiceImpl.class.getDeclaredMethod("createUser", cArg);
                if (!createUser.getReturnType().equals(User.class)) {
                    fail("Ensure that your createUser() method in UserServiceImpl return type is of type " +
                            "User!");
                    return;
                }
            } catch (NoSuchMethodException e) {
                fail("Ensure that you have a method called createUser() in the UserServiceImpl interface!");
                return;
            }

            try {
                deleteUser = UserServiceImpl.class.getDeclaredMethod("deleteUser", User.class);
                if (!deleteUser.getReturnType().equals(void.class)) {
                    fail("Ensure that your deleteUser() method in UserServiceImpl return type is of type " +
                            "void!");
                    return;
                }
            } catch (NoSuchMethodException e) {
                fail("Ensure that you have a method called deleteUser() in the UserServiceImpl interface!");
                return;
            }

            try {
                Class[] cArg = new Class[2];
                cArg[0] = String.class;
                cArg[1] = String.class;
                login = UserServiceImpl.class.getDeclaredMethod("login", cArg);
                if (!login.getReturnType().equals(User.class)) {
                    fail("Ensure that your login() method in UserServiceImpl return type is of type " +
                            "User!");
                    return;
                }
            } catch (NoSuchMethodException e) {
                fail("Ensure that you have a method called login() in the UserServiceImpl interface!");
                return;
            }
        }

        @Test
        public void testCreateUserSuccess() {
            deleteUserFiles();
            String username = "testUser4";
            String password = "pass";

            User user = userService.createUser(username, password);
            User expectedUser = getDummyUser(username, password);
            assertTrue("Ensure the createUser() method returns the expected user!", user.equals(expectedUser));
        }

        @Test
        public void testCreateUserFailure() {
            deleteUserFiles();
            try {
                String username = "testUser4";
                String duplicateUsername = username;
                String password = "pass";

                User user = userService.createUser(username, password);
                User duplicateUser = userService.createUser(duplicateUsername, password);
            } catch (Exception e) {
                assertTrue("Ensure DuplicateNameException is thrown!", e instanceof DuplicateUsernameException);
            }
        }

        @Test
        public void testLoginSuccess() {
            deleteUserFiles();
            String username = "testUser4";
            String password = "pass";
            User user = userService.createUser(username, password);
            User expectedUser = userService.login(username, password);
            assertTrue("Ensure the login() method returns the expected user!", user.equals(expectedUser));
        }

        @Test
        public void testLoginFailure() {
            deleteUserFiles();
            try {
                String username = "testUser4";
                String password = "pass";
                User testUser = userService.createUser(username, password);

                String incorrectPassword = "incorrect password";

                User user = userService.login(username, incorrectPassword);
            } catch (Exception e) {
                assertTrue("Ensure InvalidUserException is thrown!", e instanceof InvalidUserException);
            }
        }

        @Test
        public void testGetUserSuccess() {
            deleteUserFiles();
            String username = "testUser4";
            String password = "pass";
            User user = userService.createUser(username, password);
            User expectedUser = userService.getUser(username);

            assertTrue("Ensure the getUser() method returns the expected user!", user.equals(expectedUser));
        }

        @Test
        public void testGetUserFailure() {
            deleteUserFiles();
            try {
                String username = "nonexistent username";

                User user = userService.getUser(username);
            } catch (Exception e) {
                assertTrue("Ensure UserNotFoundException is thrown!", e instanceof UserNotFoundException);
            }
        }

        @Test
        public void testDeleteSuccess() {
            deleteUserFiles();
            String username = "testUser4";
            String password = "pass";
            User deletedUser = userService.createUser(username, password);

            userService.deleteUser(deletedUser);
            try {
                User nonExistUser = userService.getUser(username);
            } catch (Exception e) {
                assertTrue("Ensure the user was deleted!", e instanceof UserNotFoundException);
            }
        }

        @Test
        public void testDeleteFailure() {
            deleteUserFiles();
            try {
                String username = "nonexistent user";
                String password = "incorrect password";

                userService.deleteUser(getDummyUser(username, password));
            } catch (Exception e) {
                assertTrue("Ensure UserNotFoundException is thrown!", e instanceof UserNotFoundException);
            }
        }

        private void deleteUserFiles() {
            File directory = new File("users");
            directory.mkdir();
            File[] userFiles = directory.listFiles();
            for (File file : userFiles) {
                file.delete();
            }
        }

        private User getDummyUser(String username, String password) {
            User user = new User(username, password);
            return user;
        }


    }

}