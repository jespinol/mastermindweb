package org.jmel.mastermindweb;

import org.jmel.mastermind.core.Game;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

@Service
public class GameService {
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

    public static GameSession createGameSession(MastermindConfig config) throws IOException {
        GameSession gameSession = new GameSession();
        gameSession.setId(UUID.randomUUID());
        gameSession.setConfiguration(config);
        gameSession.setGame(buildGame(config));

        return gameSession;
    }

    public static String getGameState(Game game) {
        if (game.isGameWon()) return "You already won!";
        if (game.movesCompleted() == game.maxAttempts()) return "Game over!";

        return "Game in progress. ";
    }
}
