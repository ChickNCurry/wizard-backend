package com.chickencurry.wizardbackend.model.lobby;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LobbyAction {
    private String lobbyId;
    private String userId;
    private LobbyActionType actionType;
}
