package org.jmel.mastermindweb.model;

import org.jmel.mastermind.core.Game;

import java.util.UUID;

public class GameSession {
    private UUID id;
    private Game game;
    private MastermindConfig configuration;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public MastermindConfig getConfiguration() {
        return configuration;
    }

    public void setConfiguration(MastermindConfig configuration) {
        this.configuration = configuration;
    }
}
