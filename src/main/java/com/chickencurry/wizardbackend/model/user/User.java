package com.chickencurry.wizardbackend.model.user;

public class User {
    private final String userName;
    private String userPassword;
    private String userDisplayName;

    public User(String userName, String userPassword, String userDisplayName) {
        this.userName = userName;
        this.userPassword = userPassword;
        this.userDisplayName = userDisplayName;
    }

    public User(String userName, String userPassword) {
        this.userName = userName;
        this.userPassword = userPassword;
        this.userDisplayName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserDisplayName() {
        return userDisplayName;
    }

    public void setUserDisplayName(String userDisplayName) {
        this.userDisplayName = userDisplayName;
    }

}
