package com.chickencurry.wizardbackend.model.lobby;

import java.util.*;

public class Lobby {

    private final String lobbyId;
    private final String lobbyName;
    private final String lobbyPassword;
    private final Map<String, Boolean> lobbyUsers;

    public Lobby(String userName, String lobbyId, String lobbyName, String lobbyPassword) {
        this.lobbyId = lobbyId;
        this.lobbyName = lobbyName;
        this.lobbyPassword = lobbyPassword;
        this.lobbyUsers = new HashMap<>();
        this.lobbyUsers.put(userName, false);
    }

    public List<String> getLobbyUserNames() {
        return lobbyUsers.keySet().stream().toList();
    }

    public String getLobbyName() {
        return lobbyName;
    }

    public boolean joinLobby(String userName, String lobbyPassword) {
        if(Objects.equals(this.lobbyPassword, lobbyPassword)) {
            lobbyUsers.put(userName, false);
            return true;
        }
        return false;
    }

    public void leaveLobby(String userName) {
        lobbyUsers.remove(userName);
    }

    public boolean toggleReady(String userName) {
        lobbyUsers.put(userName, !lobbyUsers.get(userName));
        for(Map.Entry<String, Boolean> entry: lobbyUsers.entrySet()) {
            if(!entry.getValue()) return false;
        }
        return true;
    }

}
