package org.jmel.mastermindweb;

import org.jmel.mastermind.core.Game;
import org.jmel.mastermindweb.dto.MastermindConfig;
import org.jmel.mastermindweb.service.GameService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class GameServiceTests {
    @Autowired
    private GameService GameService;

    @Test
    void builderMethodReturnsGameWithDefaults() throws IOException {
        GameService.buildGame(new MastermindConfig());

        assert true;
    }

    @Test
    void builderMethodReturnsGameWithCustomConfig() throws IOException {
        MastermindConfig config = new MastermindConfig();
        config.setCodeLength(5);
        config.setNumColors(4);
        config.setMaxAttempts(3);
        config.setCodeSupplierPreference("LOCAL_RANDOM");
        config.setFeedbackStrategy("ORIGINAL_MASTERMIND");

        Game game = GameService.buildGame(config);

        assertAll(
                () -> assertEquals(game.codeLength(), 5),
                () -> assertEquals(game.numColors(), 4),
                () -> assertEquals(game.maxAttempts(), 3)
        );
    }

    @Test
    void builderMethodReturnsGameWithCustomConfigUserDefinedSecretCode() throws IOException {
        MastermindConfig config = new MastermindConfig();
        config.setCodeSupplierPreference("USER_DEFINED");
        config.setSecretCode(List.of(1, 2, 3, 4));

        Game game = GameService.buildGame(config);
        game.processGuess(List.of(1, 2, 3, 4));

        assertAll(
                () -> assertEquals(game.codeLength(), 4),
                () -> assertTrue(game.isGameWon())
        );
    }

    @Test
    void buildingGameWithInvalidConfigThrowsException() {
        MastermindConfig config = new MastermindConfig();
        config.setCodeLength(0);
        config.setNumColors(2);
        config.setMaxAttempts(0);
        config.setCodeSupplierPreference(null);
        config.setFeedbackStrategy(null);

        assertThrows(IllegalArgumentException.class, () -> GameService.buildGame(config));
    }
}
