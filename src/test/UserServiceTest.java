package test;

import exception.DuplicateUsernameException;
import exception.InvalidUserException;
import exception.UserNotFoundException;
import model.User;
import org.junit.Test;
import org.junit.After;

import java.lang.reflect.Field;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.junit.Assert;
import org.junit.Before;
import org.junit.rules.Timeout;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import service.UserService;
import service.UserServiceImpl;

import javax.swing.*;
import java.io.*;
import java.util.Random;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.Arrays;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;
import java.math.BigInteger;

import static org.junit.Assert.*;
import static org.junit.Assert.assertArrayEquals;


public class UserServiceTest {
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
        private final PrintStream originalOutput = System.out;
        private final InputStream originalSysin = System.in;

        @SuppressWarnings("FieldCanBeLocal")
        private ByteArrayInputStream testIn;

        @SuppressWarnings("FieldCanBeLocal")
        private ByteArrayOutputStream testOut;

        @Before
        public void outputStart() {
            testOut = new ByteArrayOutputStream();
            System.setOut(new PrintStream(testOut));
        }

        @After
        public void restoreInputAndOutput() {
            System.setIn(originalSysin);
            System.setOut(originalOutput);
        }

        private String getOutput() {
            return testOut.toString();
        }

        @SuppressWarnings("SameParameterValue")
        private void receiveInput(String str) {
            testIn = new ByteArrayInputStream(str.getBytes());
            System.setIn(testIn);
        }

        private static String multiline(String... inputLines) {
            StringBuilder sb = new StringBuilder();

            for (String line : inputLines) {
                sb.append(line);
                sb.append(System.lineSeparator());
            }

            return sb.toString();
        }

        @Test
        public void testExistsAndInheritsFromObject() {
            Class<?> clazz = UserService.class;
            assertTrue("Ensure that your file UserService.java extends Objects!", clazz.isInstance(Object.class));
        }

        @Test
        public void testFields() {
            Class<?> clazz = UserService.class;
            Field[] fields = clazz.getFields();
            assertTrue("Make sure the field for UserService is empty!", fields.length == 0);
        }

        @Test
        public void testIsInterfaceHasAllMethods() {
            Class<?> clazz = UserService.class;
            assertTrue("Ensure that your file UserService.java is an interface!", clazz.isInterface());
            Method getUser;
            Method createUser;
            Method deleteUser;
            Method login;

            try {
                getUser = UserService.class.getDeclaredMethod("getUser", String.class);
                if (!getUser.getReturnType().equals(User.class)) {
                    fail("Ensure that your getUser() method in UserService return type is of type " +
                            "User!");
                    return;
                }
            } catch (NoSuchMethodException e) {
                fail("Ensure that you have a method called getUser() in the UserService interface!");
                return;
            }

            try {
                Class[] cArg = new Class[2];
                cArg[0] = String.class;
                cArg[1] = String.class;
                createUser = UserService.class.getDeclaredMethod("createUser", cArg);
                if (!createUser.getReturnType().equals(User.class)) {
                    fail("Ensure that your createUser() method in UserService return type is of type " +
                            "User!");
                    return;
                }
            } catch (NoSuchMethodException e) {
                fail("Ensure that you have a method called createUser() in the UserService interface!");
                return;
            }

            try {
                deleteUser = UserService.class.getDeclaredMethod("deleteUser", User.class);
                if (!deleteUser.getReturnType().equals(void.class)) {
                    fail("Ensure that your deleteUser() method in UserService return type is of type " +
                            "void!");
                    return;
                }
            } catch (NoSuchMethodException e) {
                fail("Ensure that you have a method called deleteUser() in the UserService interface!");
                return;
            }

            try {
                Class[] cArg = new Class[2];
                cArg[0] = String.class;
                cArg[1] = String.class;
                login = UserService.class.getDeclaredMethod("login", cArg);
                if (!login.getReturnType().equals(User.class)) {
                    fail("Ensure that your login() method in UserService return type is of type " +
                            "User!");
                    return;
                }
            } catch (NoSuchMethodException e) {
                fail("Ensure that you have a method called login() in the UserService interface!");
                return;
            }
        }

        @Test
        public void testCreateUserSuccess() {
            String username = "testUser4";
            String password = "pass";
            UserService userService = new UserServiceImpl();
            User user = userService.createUser(username, password);
            User expectedUser = getDummyUser(username, password);
            assertTrue("Ensure the createUser() method returns the expected user!", user.equals(expectedUser));
        }

        @Test
        public void testCreateUserFailure() {
            try {
                String username = "testUser4";
                String password = "pass";
                UserService userService = new UserServiceImpl();
                User user = userService.createUser(username, password);
            } catch (Exception e) {
                assertTrue("Ensure DuplicateNameException is thrown!", e instanceof DuplicateUsernameException);
            }
        }

        @Test
        public void testLoginSuccess() {
            String username = "testUser4";
            String password = "pass";
            UserService userService = new UserServiceImpl();
            User user = userService.login(username, password);
            User expectedUser = getDummyUser(username, password);
            assertTrue("Ensure the login() method returns the expected user!", user.equals(expectedUser));
        }

        @Test
        public void testLoginFailure() {
            try {
                String username = "testUser4";
                String password = "incorrect password";
                UserService userService = new UserServiceImpl();
                User user = userService.login(username, password);
            } catch (Exception e) {
                assertTrue("Ensure InvalidUserException is thrown!", e instanceof InvalidUserException);
            }
        }

        @Test
        public void testGetUserSuccess() {
            String username = "testUser4";
            String password = "pass";
            UserService userService = new UserServiceImpl();
            User user = userService.getUser(username);
            User expectedUser = getDummyUser(username, password);
            assertTrue("Ensure the getUser() method returns the expected user!", user.equals(expectedUser));
        }

        @Test
        public void testGetUserFailure() {
            try {
                String username = "nonexistent username";
                UserService userService = new UserServiceImpl();
                User user = userService.getUser(username);
            } catch (Exception e) {
                assertTrue("Ensure UserNotFoundException is thrown!", e instanceof UserNotFoundException);
            }
        }

        @Test
        public void testDeleteSuccess() {
            String username = "testUser4";
            String password = "pass";
            User deletedUser = getDummyUser(username, password);
            UserService userService = new UserServiceImpl();
            userService.deleteUser(deletedUser);
            try {
                User nonExistUser = userService.getUser(username);
            } catch (Exception e) {
                assertTrue("Ensure the user was deleted!", e instanceof UserNotFoundException);
            }
        }

        @Test
        public void testDeleteFailure() {
            try {
                String username = "nonexistent user";
                String password = "incorrect password";
                UserService userService = new UserServiceImpl();
                userService.deleteUser(getDummyUser(username, password));
            } catch (Exception e) {
                assertTrue("Ensure UserNotFoundException is thrown!", e instanceof UserNotFoundException);
            }
        }

        private User getDummyUser(String username, String password) {
            User user = new User(username, password);
            return user;
        }


    }

}