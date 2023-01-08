package com.chickencurry.wizardbackend.controller;

import com.chickencurry.wizardbackend.model.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/chat-message")
    @SendTo("/chat")
    public ChatMessage receiveMessage(@Payload ChatMessage message){
        return message;
    }

    /*
    @MessageMapping("/private-message")
    public ChatMessage recMessage(@Payload ChatMessage message){
        simpMessagingTemplate.convertAndSendToUser(message.getReceiver(),"/private",message);
        System.out.println(message.toString());
        return message;
    }
     */
}
