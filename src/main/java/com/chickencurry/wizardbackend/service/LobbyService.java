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
        return lobbiesMap.getLobbiesMap();
    }

    public String getLobbyName(String lobbyId) {
        return lobbiesMap.getLobbyName(lobbyId);
    }

    public List<String> getLobbyUserDisplayNames(String lobbyId) {
        return lobbiesMap.getLobbyUserNames(lobbyId).stream()
                .map(userName -> userService.getUser(userName).getUserDisplayName()).toList();
    }

    public String createLobby(String userName, String lobbyName, String lobbyPassword) {
        return lobbiesMap.createLobby(userName, lobbyName, lobbyPassword);
    }

    public boolean joinLobby(String userName, String lobbyId, String lobbyPassword) {
        return lobbiesMap.joinLobby(userName, lobbyId, lobbyPassword);
    }

    public void leaveLobby(String lobbyId, String userName) {
        lobbiesMap.leaveLobby(lobbyId, userName);
    }

    public void toggleReady(String lobbyId, String userName) {
        if(lobbiesMap.toggleReady(lobbyId, userName)) {
            gameService.createGame(lobbiesMap.getLobbyUserNames(lobbyId));
        };
    }

}
