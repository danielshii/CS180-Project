package enumeration;

import java.io.Serializable;

public enum ActionType implements Serializable {
    ADD_USER("ADD_USER"),
    UPDATE_USER("UPDATE_USER"),
    DELETE_USER("DELETE_USER"),
    LOGIN_USER("LOGIN_USER"),

    GET_ALL_POSTS("GET_ALL_POSTS"),
    GET_POSTS_BY_USER("GET_POSTS_BY_USER"),
    EDIT_POST("EDIT_POST"),
    DELETE_POST("DELETE_POST"),
    CREATE_POST("CREATE_POST"),

    GET_COMMENTS("GET_COMMENTS"),
    GET_COMMENTS_BY_USER("GET_COMMENTS_BY_USER"),
    ADD_COMMENT("ADD_COMMENT"),
    DELETE_COMMENT("DELETE_COMMENT"),
    EDIT_COMMENT("EDIT_COMMENT");

    private String actionCode;

    ActionType(String actionCode) {
        this.actionCode = actionCode;
    }

    public String getActionCode() {
        return actionCode;
    }

    public void setFolderName(String actionCode) {
        this.actionCode = actionCode;
    }
}
