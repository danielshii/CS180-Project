package model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


public class Post {

    private static final class SerializationProxy implements Serializable {
        static final long serialUuid;
        UUID uuid;
        Date createdDate;
        String content;
        String createdUsername;
        List<Comment> comments;

        static {
            serialUuid = 0xBEEFBEEF;
        }

        SerializationProxy(Post post) {
            Objects.requireNonNull(post, "null!");
            this.comments = post.comments;
            this.content = post.content;
            this.createdDate = post.createdDate;
            this.createdUsername = post.createdUsername;
            this.uuid = post.uuid;
        }

        Object readResolve() {
            return new Post(this.createdDate, this.content, this.createdUsername);
        }
    }

    private static final long serialUuid;
    private UUID uuid;
    private Date createdDate;
    private String content;
    private String createdUsername;
    private List<Comment> comments;

    static {
        serialUuid = 0xBEEFBEEF;
    }

    public Post() {
    }

    public Post(Date createdDate, String content, String createdUsername) {
        this.createdDate = createdDate;
        this.content = content;
        this.createdUsername = createdUsername;

    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreatedUsername() {
        return createdUsername;
    }

    public void setCreatedUsername(String createdUsername) {
        this.createdUsername = createdUsername;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    private Object writeReplace()
    {
        return new SerializationProxy(this);
    }

    @Override
    public String toString() {
        return String.format("%s || Posted by %s on %s", content, createdUsername, createdDate.toString());
    }
}
