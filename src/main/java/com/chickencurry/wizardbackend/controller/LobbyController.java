package com.chickencurry.wizardbackend.controller;

import com.chickencurry.wizardbackend.model.lobby.LobbyAction;
import com.chickencurry.wizardbackend.service.LobbyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class LobbyController {

    private final LobbyService lobbyService;

    @Autowired
    public LobbyController(LobbyService lobbyService) {
        this.lobbyService = lobbyService;
    }

    @MessageMapping("/toggle-ready")
    @SendTo("/game")
    public LobbyAction setReady(@Payload LobbyAction action) {
        lobbyService.toggleReady(action.getLobbyId(), action.getUserId());
        return action;
    }

}
