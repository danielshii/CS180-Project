package test;


import model.Post;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.lang.reflect.Field;

import static org.junit.Assert.assertTrue;


public class PostTest {
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
            Class<?> clazz = Post.class;
            Field[] fields = clazz.getDeclaredFields();
            assertTrue("Make sure the fields are correct!", fields.length == 6);
        }

        @Test
        public void testExistsAndInheritsFromObject() {
            Class<?> clazz = Post.class;
            assertTrue("Ensure that your file Post.java extends Objects!", Object.class.isInstance(clazz));
        }


    }

}