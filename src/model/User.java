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

    private Object writeReplace()
    {
        return new SerializationProxy(this);
    }
}
