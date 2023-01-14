package com.chickencurry.wizardbackend.components;

import com.chickencurry.wizardbackend.model.lobby.Lobby;
import com.chickencurry.wizardbackend.model.lobby.LobbyIdNamePair;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class LobbiesMap {

    private final Map<String, Lobby> lobbiesMap;

    public LobbiesMap() {
        this.lobbiesMap = new HashMap<>();
    }

    public List<LobbyIdNamePair> getLobbiesMap() {
        return lobbiesMap.entrySet().stream().map(
                entry -> new LobbyIdNamePair(entry.getKey(), entry.getValue().getLobbyName())).toList();
    }

    public String createLobby(String userName, String lobbyName, String lobbyPassword) {
        String lobbyId = UUID.randomUUID().toString();
        Lobby lobby = new Lobby(userName, lobbyId, lobbyName, lobbyPassword);
        lobbiesMap.put(lobbyId, lobby);
        return lobbyId;
    }

    public boolean joinLobby(String userName, String lobbyId, String password) {
        Lobby lobby = lobbiesMap.get(lobbyId);
        if(lobby.joinLobby(userName, password)) {
            lobbiesMap.replace(lobbyId, lobby);
            return true;
        }
        return false;
    }

    public void leaveLobby(String lobbyId, String userName) {
        Lobby lobby = lobbiesMap.get(lobbyId);
        lobby.leaveLobby(userName);
        lobbiesMap.replace(lobbyId, lobby);
    }

    public String getLobbyName(String lobbyId) {
        Lobby lobby = lobbiesMap.get(lobbyId);
        return lobby.getLobbyName();
    }

    public List<String> getLobbyUserNames(String lobbyId) {
        Lobby lobby = lobbiesMap.get(lobbyId);
        return lobby.getLobbyUserNames();
    }

    public boolean toggleReady(String lobbyId, String userId) {
        Lobby lobby = lobbiesMap.get(lobbyId);
        return lobby.toggleReady(userId);
    }

}
