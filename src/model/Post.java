package model;

import java.io.Serializable;
import java.util.*;


public class Post implements Serializable {

    private static final class SerializationProxy implements Serializable {
        static final long serialUuid;
        UUID uuid;
        Date createdDate;
        String content;
        String createdUsername;
        List<Comment> comments;

        static {
            serialUuid = 0xCAFEBABE;
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
            return new Post(this.uuid, this.createdDate, this.content, this.createdUsername, this.comments);
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

    public Post(UUID uuid, Date createdDate, String content, String createdUsername, List<Comment> comments) {
        this.uuid = uuid;
        this.createdDate = createdDate;
        this.content = content;
        this.createdUsername = createdUsername;
        this.comments = comments;
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

    private Object writeReplace() {
        return new SerializationProxy(this);
    }

    @Override
    public String toString() {
        return String.format("<html>%s<br/>Posted by %s on %s", content, createdUsername, createdDate.toString());
    }
}
