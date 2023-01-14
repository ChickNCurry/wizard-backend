package com.chickencurry.wizardbackend.service;

import com.chickencurry.wizardbackend.model.user.User;
import com.chickencurry.wizardbackend.components.UsersMap;
import com.chickencurry.wizardbackend.model.user.UserStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UsersMap usersMap;

    @Autowired
    public UserService(UsersMap usersMap) {
        this.usersMap = usersMap;
    }

    public User getUser(String userName) {
        return usersMap.getUser(userName);
    }

    public void setUserPassword(String userName, String userPassword) {
        usersMap.setUserPassword(userName, userPassword);
    }

    public String getUserDisplayName(String userName) {
        return usersMap.getUserDisplayName(userName);
    }

    public void setUserDisplayName(String userName, String userDisplayName) {
        usersMap.setUserDisplayName(userName, userDisplayName);
    }

    public UserStatus getUserStatus(String userName) {
        return usersMap.getUserStatus(userName);
    }

    public String createUser(String userName, String userPassword) {
        return usersMap.createUser(userName, userPassword);
    }

    public String createUser(String userName, String userPassword, String userDisplayName) {
        return usersMap.createUser(userName, userPassword, userDisplayName);
    }

    public void deleteUser(String userName) {
        usersMap.deleteUser(userName);
    }

    public boolean loginUser(String userName, String userPassword) {
        User user = usersMap.getUser(userName);
        if(user != null && userPassword.equals(user.getUserPassword())) {
            usersMap.loginUser(userName);
            return true;
        }
        return false;
    }

    public void logoutUser(String userName) {
        usersMap.logoutUser(userName);
    }

}
