package model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public class Comment implements Serializable {

    private static final class SerializationProxy implements Serializable {
        static final long serialUuid;
        UUID uuid;
        UUID postUuid;
        Date createdDate;
        String content;
        String createdUsername;

        static {
            serialUuid = 0xBEFEBEFE;
        }

        SerializationProxy(Comment comment) {
            Objects.requireNonNull(comment, "null!");
            this.content = comment.content;
            this.createdDate = comment.createdDate;
            this.createdUsername = comment.createdUsername;
            this.postUuid = comment.postUuid;
            this.uuid = comment.uuid;
        }

        Object readResolve() {
            return new Comment(this.uuid, this.postUuid, this.createdDate, this.content, this.createdUsername);
        }
    }

    private static final long serialUuid;
    private UUID uuid;
    private UUID postUuid;
    private Date createdDate;
    private String content;
    private String createdUsername;

    static {
        serialUuid = 0xBEEFBEEF;
    }

    public Comment() {
    }

    public Comment(UUID uuid, UUID postUuid, Date createdDate, String content, String createdUsername) {
        this.uuid = uuid;
        this.postUuid = postUuid;
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

    public UUID getPostUuid() {
        return postUuid;
    }

    public void setPostUuid(UUID postUuid) {
        this.postUuid = postUuid;
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

    @Override
    public String toString() {
        String string = "<html>%s<br/>Posted by: %s at %s</html>";
        return String.format(string, content, createdUsername, createdDate);
    }

    private Object writeReplace() {
        return new SerializationProxy(this);
    }
}
