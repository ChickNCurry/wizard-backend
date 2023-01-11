package com.chickencurry.wizardbackend.service;

import com.chickencurry.wizardbackend.components.LobbiesMap;
import com.chickencurry.wizardbackend.model.lobby.LobbyIdNamePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LobbyService {

    private final GameService gameService;
    private final UserService userService;
    private final LobbiesMap lobbiesMap;

    @Autowired
    public LobbyService(GameService gameService, UserService userService, LobbiesMap lobbiesMap) {
        this.gameService = gameService;
        this.userService = userService;
        this.lobbiesMap = lobbiesMap;
    }

    public List<LobbyIdNamePair> getLobbies() {
        return lobbiesMap.getLobbies();
    }

    public String getLobbyName(String lobbyId) {
        return lobbiesMap.getLobbyName(lobbyId);
    }

    public List<String> getLobbyUserNames(String lobbyId) {
        return lobbiesMap.getLobbyUserIds(lobbyId).stream()
                .map(userId -> userService.getUser(userId).getUserName()).toList();
    }

    public String createLobby(String userId, String lobbyName, String lobbyPassword) {
        return lobbiesMap.createLobby(userId, lobbyName, lobbyPassword);
    }

    public boolean joinLobby(String userId, String lobbyId, String lobbyPassword) {
        return lobbiesMap.joinLobby(userId, lobbyId, lobbyPassword);
    }

    public void leaveLobby(String lobbyId, String userId) {
        lobbiesMap.leaveLobby(lobbyId, userId);
    }

    public void toggleReady(String lobbyId, String userId) {
        if(lobbiesMap.toggleReady(lobbyId, userId)) {
            gameService.createGame(lobbiesMap.getLobbyUserIds(lobbyId));
        };
    }

}
