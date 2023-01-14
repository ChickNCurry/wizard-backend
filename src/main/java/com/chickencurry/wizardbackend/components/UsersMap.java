package com.chickencurry.wizardbackend.components;

import com.chickencurry.wizardbackend.model.user.User;
import com.chickencurry.wizardbackend.model.user.UserStatus;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class UsersMap {

    private final Map<String, User> usersMap;
    private final Map<String, UserStatus> userStatusMap;

    public UsersMap() {
        this.usersMap = new HashMap<>();
        this.userStatusMap = new HashMap<>();
    }

    public User getUser(String userName) {
        return usersMap.get(userName);
    }

    public void setUserPassword(String userName, String userPassword) {
        User user = usersMap.get(userName);
        user.setUserPassword(userPassword);
        usersMap.put(userName, user);
    }

    public String getUserDisplayName(String userName) {
        return usersMap.get(userName).getUserDisplayName();
    }

    public void setUserDisplayName(String userName, String userDisplayName) {
        usersMap.get(userName).setUserDisplayName(userDisplayName);
    }

    public UserStatus getUserStatus(String userName) {
        return userStatusMap.get(userName);
    }

    public String createUser(String userName, String userPassword) {
        usersMap.put(userName, new User(userName, userPassword));
        userStatusMap.put(userName, UserStatus.OFFLINE);
        return userName;
    }

    public String createUser(String userName, String userPassword, String userDisplayName) {
        usersMap.put(userName, new User(userName, userPassword, userDisplayName));
        userStatusMap.put(userName, UserStatus.OFFLINE);
        return userName;
    }

    public void deleteUser(String userName) {
        usersMap.remove(userName);
        userStatusMap.remove(userName);
    }

    public void loginUser(String userName) {
        userStatusMap.replace(userName, UserStatus.ONLINE);
    }

    public void logoutUser(String userName) {
        userStatusMap.replace(userName, UserStatus.OFFLINE);
    }

}
