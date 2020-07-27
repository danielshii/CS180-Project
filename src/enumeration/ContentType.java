package enumeration;

public enum ContentType {
    USER("users"),
    POST("posts"),
    COMMENT("comments");

    private String folderName;

    ContentType(String folderName) {
        this.folderName = folderName;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }
}
