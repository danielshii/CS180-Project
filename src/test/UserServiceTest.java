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
import service.UserService;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


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
        private UserService userService;

        @Before
        public void start() {

        }

        @Test
        public void testFields() {
            Class<?> clazz = UserService.class;
            Field[] fields = clazz.getFields();
            assertTrue("Make sure the field for UserService is correct!", fields.length == 0);
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

    }

}