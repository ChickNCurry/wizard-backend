package com.chickencurry.wizardbackend.controller;

import com.chickencurry.wizardbackend.model.chat.ChatMessage;
import com.chickencurry.wizardbackend.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    private final ChatService chatService;

    @Autowired
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @MessageMapping("/send-chat-message")
    @SendTo("/chat")
    public ChatMessage sendChatMessage(@Payload ChatMessage chatMessage) {
        chatService.pushToChat(chatMessage.getMessage());
        return chatMessage;
    }

}
