package enumeration;

public enum ObjectType {
    USER("users"),
    POST("posts"),
    COMMENT("comments");

    private String folderName;

    ObjectType(String folderName) {
        this.folderName = folderName;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }
}
