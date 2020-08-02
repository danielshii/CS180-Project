package test;


import model.Comment;
import model.Post;
import model.User;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


public class UserTest {
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


        @Test
        public void testFields() {
            Class<?> clazz = User.class;
            Field[] fields = clazz.getDeclaredFields();
            assertTrue("Make sure the fields are correct!", fields.length == 3);
        }

        @Test
        public void testExistsAndInheritsFromObject() {
            Class<?> clazz = User.class;
            assertTrue("Ensure that your file User.java extends Objects!", Object.class.isInstance(clazz));
        }


    }

}