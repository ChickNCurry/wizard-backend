package com.chickencurry.wizardbackend.components;

import com.chickencurry.wizardbackend.model.user.User;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class UsersMap {

    private final Map<String, User> users;

    public UsersMap() {
        this.users = new HashMap<>();
    }

    public User getUser(String userId) {
        return users.get(userId);
    }

    public String createUser(String userName, String userPassword) {
        String userId = UUID.randomUUID().toString();
        users.put(userId, new User(userId, userName, userPassword));
        return userId;
    }

    public void deleteUser(String userId) {
        users.remove(userId);
    }

}