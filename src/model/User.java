package model;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {

    private static final class SerializationProxy implements Serializable {
        static final long serialUuid;
        String username;
        String password;

        static {
            serialUuid = 0xBEEFBEEF;
        }

        SerializationProxy(User user) {
            Objects.requireNonNull(user, "null!");
            this.username = user.username;
            this.password = user.password;
        }

        Object readResolve() {
            return new User(this.username, this.password);
        }
    }

    private static final long serialUuid;
    private String username;
    private String password;

    static {
        serialUuid = 0xBEEFBEEF;
    }

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private Object writeReplace() {
        return new SerializationProxy(this);
    }

    @Override
    public boolean equals(Object user) {

        // If the object is compared with itself then return true   
        if (user == this) {
            return true;
        } 
  
        /* Check if o is an instance of User or not 
          "null instanceof [type]" also returns false */
        if (!(user instanceof User)) {
            return false;
        }

        // typecast o to User so that we can compare data members  
        User u = (User) user;
        return Objects.equals(this.getUsername(), u.getUsername()) && Objects.equals(this.getPassword(), u.getPassword());


    }
}
