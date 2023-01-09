package com.chickencurry.wizardbackend.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChatService {

    private final List<String> chat;

    public ChatService() {
        this.chat = new ArrayList<>();
    }

    public List<String> getChat() {
        return chat;
    }

    public void pushToChat(String message) {
        chat.add(message);
    }

}
