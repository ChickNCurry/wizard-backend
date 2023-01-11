package com.chickencurry.wizardbackend.controller;

import com.chickencurry.wizardbackend.model.lobby.LobbyAction;
import com.chickencurry.wizardbackend.model.lobby.LobbyIdNamePair;
import com.chickencurry.wizardbackend.service.LobbyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/lobby")
public class LobbyController {

    private final LobbyService lobbyService;

    @Autowired
    public LobbyController(LobbyService lobbyService) {
        this.lobbyService = lobbyService;
    }

    @GetMapping(path = "/get-lobbies")
    public List<LobbyIdNamePair> getLobbies() {
        return lobbyService.getLobbies();
    }

    @GetMapping(path = "/get-lobby-name")
    public String getLobbyName(@RequestBody String lobbyId) {
        return lobbyService.getLobbyName(lobbyId);
    }

    @GetMapping(path = "/get-lobby-user-names")
    public List<String> getLobbyUserNames(@RequestBody String lobbyId) {
        return lobbyService.getLobbyUserNames(lobbyId);
    }

    @PostMapping(path = "/create-lobby")
    public String createLobby(@RequestBody Map<String, String> lobbyInfo) {
        return lobbyService.createLobby(
                lobbyInfo.get("userId"), lobbyInfo.get("lobbyName"), lobbyInfo.get("lobbyPassword"));
    }

    @PostMapping(path = "/join-lobby")
    public boolean joinLobby(@RequestBody Map<String, String> lobbyInfo) {
        return lobbyService.joinLobby(
                lobbyInfo.get("userId"), lobbyInfo.get("lobbyId"), lobbyInfo.get("lobbyPassword"));
    }

    @PostMapping(path = "/leave-lobby")
    public void leaveLobby(@RequestBody Map<String, String> lobbyInfo) {
        lobbyService.leaveLobby(lobbyInfo.get("lobbyId"), lobbyInfo.get("userId"));
    }

    @MessageMapping("/toggle-ready")
    @SendTo("/game")
    public LobbyAction toggleReady(@Payload LobbyAction action) {
        lobbyService.toggleReady(action.getLobbyId(), action.getUserId());
        return action;
    }

}
