package com.chickencurry.wizardbackend.controller;

import com.chickencurry.wizardbackend.model.game.GameAction;
import com.chickencurry.wizardbackend.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class GameController {

    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @MessageMapping("/set-ready")
    @SendTo("/game")
    public GameAction setReady(@Payload GameAction action) {
        return action;
    }

    @MessageMapping("/choose-trump-suit")
    @SendTo("/game")
    public GameAction chooseTrumpSuit(@Payload GameAction action) {
        return action;
    }

    @MessageMapping("/predict-tricks")
    @SendTo("/game")
    public GameAction predictTricks(@Payload GameAction action) {
        return action;
    }

    @MessageMapping("/play-card")
    @SendTo("/game")
    public GameAction playCard(@Payload GameAction action) {
        return action;
    }

}
