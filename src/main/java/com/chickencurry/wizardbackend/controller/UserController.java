package com.chickencurry.wizardbackend.controller;

import com.chickencurry.wizardbackend.model.user.User;
import com.chickencurry.wizardbackend.model.user.UserStatus;
import com.chickencurry.wizardbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/get-user")
    public User getUser(@RequestBody String userName) {
        return userService.getUser(userName);
    }

    @PostMapping(path = "/set-user-password")
    public void setUserPassword(@RequestBody Map<String, String> body) {
        userService.setUserPassword(body.get("userName"), body.get("userPassword"));
    }

    @GetMapping(path = "/get-user-display-name")
    public String getUserDisplayName(@RequestBody String userName) {
        return userService.getUserDisplayName(userName);
    }

    @PostMapping(path = "/set-user-display-name")
    public void setUserDisplayName(@RequestBody Map<String, String> body) {
        userService.setUserDisplayName(body.get("userName"), body.get("userDisplayName"));
    }

    @GetMapping(path = "/get-user-status")
    public UserStatus getUserStatus(@RequestBody String userName) {
        return userService.getUserStatus(userName);
    }

    @PutMapping(path = "/create-user")
    public String createUser(@RequestBody Map<String, String> body) {
        String userDisplayName = body.get("userDisplayName");
        if(userDisplayName == null) {
            return userService.createUser(body.get("userName"), body.get("userPassword"));
        }
        return userService.createUser(body.get("userName"), body.get("userPassword"), userDisplayName);
    }

    @DeleteMapping(path = "/delete-user")
    public void deleteUser(@RequestBody String userName) {
        userService.deleteUser(userName);
    }

    @PostMapping(path = "/login-user")
    public boolean loginUser(@RequestBody Map<String, String> body) {
        return userService.loginUser(body.get("userName"), body.get("userPassword"));
    }

    @PostMapping(path = "/logout-user")
    public void logoutUser(@RequestBody String userName) {
        userService.logoutUser(userName);
    }

}
