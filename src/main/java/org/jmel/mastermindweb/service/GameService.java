package org.jmel.mastermindweb.service;

import org.jmel.mastermind.core.Game;
import org.jmel.mastermindweb.model.GameSession;
import org.jmel.mastermindweb.model.GameStateRecord;
import org.jmel.mastermindweb.model.MastermindConfig;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

@Service
public class GameService {
    public static GameSession createGameSession(MastermindConfig config) throws IOException {
        GameSession gameSession = new GameSession();
        gameSession.setId(UUID.randomUUID());
        gameSession.setConfiguration(config);
        gameSession.setGame(buildGame(config));

        return gameSession;
    }

    public static Game buildGame(MastermindConfig config) throws IOException {
        Game.Builder builder = new Game.Builder()
                .codeLength(config.getCodeLength())
                .numColors(config.getNumColors())
                .maxAttempts(config.getMaxAttempts())
                .codeSupplierPreference(config.getCodeSupplierPreference())
                .feedbackStrategy(config.FeedbackStrategy());

        if (!config.getSecretCode().isEmpty()) {
            builder.secretCode(config.getSecretCode());
        }

        return builder.build();
    }

    public static String getGameState(Game game) {
        GameStateRecord gameState = new GameStateRecord(game.codeLength(), game.numColors(), game.maxAttempts(), game.isGameWon(), game.movesCompleted());

        return gameState.toString();
    }
}
