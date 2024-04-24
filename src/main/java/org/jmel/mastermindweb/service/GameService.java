package org.jmel.mastermindweb.service;

import org.jmel.mastermind.core.Game;
import org.jmel.mastermind.core.feedbackstrategy.FeedbackStrategyImpl;
import org.jmel.mastermind.core.secretcodesupplier.ApiCodeSupplier;
import org.jmel.mastermind.core.secretcodesupplier.CodeSupplier;
import org.jmel.mastermind.core.secretcodesupplier.LocalRandomCodeSupplier;
import org.jmel.mastermind.core.secretcodesupplier.UserDefinedCodeSupplier;
import org.jmel.mastermindweb.customfeedback.EncouragingStrategyImpl;
import org.jmel.mastermindweb.dto.GameState;
import org.jmel.mastermindweb.dto.MastermindConfig;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

@Service
public class GameService {
    public enum CodeSupplierPreference {RANDOM_ORG_API, LOCAL_RANDOM, USER_DEFINED}

    public Game createGame(MastermindConfig config) throws IOException {

        return buildGame(config);
    }

    public Game buildGame(MastermindConfig config) throws IOException {
        Game.Builder builder = new Game.Builder()
                .codeLength(config.getCodeLength())
                .numColors(config.getNumColors())
                .maxAttempts(config.getMaxAttempts());

        if (config.FeedbackStrategy().equals("ENCOURAGING")) {
            builder.feedbackStrategy(new EncouragingStrategyImpl());
        } else {
            builder.feedbackStrategy(FeedbackStrategyImpl.valueOf(config.FeedbackStrategy()));
        }

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

    public GameState getGameState(Game game) {
        return new GameState(game.codeLength(), game.numColors(), game.maxAttempts(), game.isGameWon(), game.movesCompleted());
    }
}
