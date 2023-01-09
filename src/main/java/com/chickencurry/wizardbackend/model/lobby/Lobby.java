package com.chickencurry.wizardbackend.model.lobby;

import java.util.*;

public class Lobby {

    private final String lobbyId;
    private final String lobbyName;
    private final String lobbyPassword;
    private final Map<String, Boolean> lobbyUsers;

    public Lobby(String userId, String lobbyId, String lobbyName, String lobbyPassword) {
        this.lobbyId = lobbyId;
        this.lobbyName = lobbyName;
        this.lobbyPassword = lobbyPassword;
        this.lobbyUsers = new HashMap<>();
        this.lobbyUsers.put(userId, false);
    }

    public List<String> getLobbyUserIds() {
        return lobbyUsers.keySet().stream().toList();
    }

    public String getLobbyName() {
        return lobbyName;
    }

    public boolean joinLobby(String userId, String lobbyPassword) {
        if(Objects.equals(this.lobbyPassword, lobbyPassword)) {
            lobbyUsers.put(userId, false);
            return true;
        }
        return false;
    }

    public void leaveLobby(String userId) {
        lobbyUsers.remove(userId);
    }

    public boolean toggleReady(String userId) {
        lobbyUsers.put(userId, !lobbyUsers.get(userId));
        for(Map.Entry<String, Boolean> entry: lobbyUsers.entrySet()) {
            if(!entry.getValue()) return false;
        }
        return true;
    }

}
