package com.chickencurry.wizardbackend.service;

import com.chickencurry.wizardbackend.model.user.User;
import com.chickencurry.wizardbackend.components.UsersMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UsersMap usersMap;

    @Autowired
    public UserService(UsersMap usersMap) {
        this.usersMap = usersMap;
    }

    public User getUser(String userId) {
        return usersMap.getUser(userId);
    }

    public String createUser(String userName, String userPassword) {
        return usersMap.createUser(userName, userPassword);
    }

    public void deleteUser(String userId) {
        usersMap.deleteUser(userId);
    }

}
