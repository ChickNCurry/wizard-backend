package com.chickencurry.wizardbackend.components;

import com.chickencurry.wizardbackend.model.lobby.Lobby;
import com.chickencurry.wizardbackend.model.lobby.LobbyIdNamePair;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class LobbiesMap {

    private final Map<String, Lobby> lobbies;

    public LobbiesMap() {
        this.lobbies = new HashMap<>();
    }

    public List<LobbyIdNamePair> getLobbies() {
        return lobbies.entrySet().stream().map(
                entry -> new LobbyIdNamePair(entry.getKey(), entry.getValue().getLobbyName())).toList();
    }

    public String createLobby(String userId, String lobbyName, String lobbyPassword) {
        String lobbyId = UUID.randomUUID().toString();
        Lobby lobby = new Lobby(userId, lobbyId, lobbyName, lobbyPassword);
        lobbies.put(lobbyId, lobby);
        return lobbyId;
    }

    public boolean joinLobby(String userId, String lobbyId, String password) {
        Lobby lobby = lobbies.get(lobbyId);
        if(lobby.joinLobby(userId, password)) {
            lobbies.replace(lobbyId, lobby);
            return true;
        }
        return false;
    }

    public void leaveLobby(String lobbyId, String user) {
        Lobby lobby = lobbies.get(lobbyId);
        lobby.leaveLobby(user);
        lobbies.replace(lobbyId, lobby);
    }

    public String getLobbyName(String lobbyId) {
        Lobby lobby = lobbies.get(lobbyId);
        return lobby.getLobbyName();
    }

    public List<String> getLobbyUserIds(String lobbyId) {
        Lobby lobby = lobbies.get(lobbyId);
        return lobby.getLobbyUserIds();
    }

    public boolean toggleReady(String lobbyId, String userId) {
        Lobby lobby = lobbies.get(lobbyId);
        return lobby.toggleReady(userId);
    }

}
