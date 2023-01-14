package com.chickencurry.wizardbackend.components;

import com.chickencurry.wizardbackend.model.game.GameState;
import com.chickencurry.wizardbackend.model.player.Player;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class GamesMap {

    private final Map<String, GameState> gamesMap;

    public GamesMap() {
        this.gamesMap = new HashMap<>();
    }

    public GameState getGame(String gameId) {
        return gamesMap.get(gameId);
    }

    public String createGame(List<Player> players) {
        String gameId = UUID.randomUUID().toString();
        gamesMap.put(gameId, new GameState(gameId, players));
        return gameId;
    }

    public void deleteGame(String gameId) {
        gamesMap.remove(gameId);
    }

}
