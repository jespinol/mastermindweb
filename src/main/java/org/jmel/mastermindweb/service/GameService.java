package org.jmel.mastermindweb.service;

import org.jmel.mastermind.core.Game;
import org.jmel.mastermind.core.feedbackstrategy.FeedbackStrategyImpl;
import org.jmel.mastermind.core.secretcodesupplier.*;
import org.jmel.mastermindweb.model.GameSession;
import org.jmel.mastermindweb.model.GameState;
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
                .feedbackStrategy(FeedbackStrategyImpl.valueOf(config.FeedbackStrategy()));

        CodeSupplier supplier = switch (CodeSupplierPreference.valueOf(config.getCodeSupplierPreference())) {
            case RANDOM_ORG_API -> ApiCodeSupplier.of(config.getCodeLength(), config.getNumColors());
            case LOCAL_RANDOM -> LocalRandomCodeSupplier.of(config.getCodeLength(), config.getNumColors());
            case USER_DEFINED -> UserDefinedCodeSupplier.of(config.getSecretCode());
            default ->
                    throw new IllegalArgumentException("Invalid code supplier: " + config.getCodeSupplierPreference());
        };
        builder.codeSupplier(supplier);

        return builder.build();
    }

    public static GameState getGameState(Game game) {
        return new GameState(game.codeLength(), game.numColors(), game.maxAttempts(), game.isGameWon(), game.movesCompleted());
    }
}
