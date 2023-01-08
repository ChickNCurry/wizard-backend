package com.chickencurry.wizardbackend.controller;

import com.chickencurry.wizardbackend.model.chat.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @MessageMapping("/chat-message")
    @SendTo("/chat")
    public ChatMessage receiveChatMessage(@Payload ChatMessage message){
        return message;
    }

}
