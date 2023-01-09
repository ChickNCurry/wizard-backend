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

    @MessageMapping("/choose-trump-suit")
    @SendTo("/game")
    public GameAction chooseTrumpSuit(@Payload GameAction action) {
        gameService.chooseTrumpSuit(action.getGameId(), action.getPlayerId(), action.getCardSuit());
        return action;
    }

    @MessageMapping("/predict-tricks")
    @SendTo("/game")
    public GameAction predictTricks(@Payload GameAction action) {
        gameService.predictTricks(action.getGameId(), action.getPlayerId(), action.getTricks());
        return action;
    }

    @MessageMapping("/play-card")
    @SendTo("/game")
    public GameAction playCard(@Payload GameAction action) {
        gameService.playCard(action.getGameId(), action.getPlayerId(), action.getCard());
        return action;
    }

}
