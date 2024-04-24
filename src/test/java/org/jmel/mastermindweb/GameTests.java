package org.jmel.mastermindweb;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONObject;
import org.jmel.mastermindweb.dto.GameState;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

//@WebMvcTest(GameController.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GameTests {
    @LocalServerPort
    private int port = 8080;

    @Autowired
    private TestRestTemplate restTemplate;

    private final String URI = "http://localhost:";

    @Test
    void sessionWithDefaultGameIsCreatedSuccessfully() {
        UUID id = this.restTemplate.postForObject(URI + port + "/new", new JSONObject(), UUID.class);

        assertNotNull(id);
        assertEquals(id.getClass(), UUID.class);
    }

    @Test
    void sessionWithCustomizedGameIsCreatedSuccessfully() {
        JSONObject config = new JSONObject();
        config.put("codeLength", 5);
        config.put("numColors", 10);
        config.put("maxAttempts", 3);
        config.put("codeSupplierPreference", "LOCAL_RANDOM");
        config.put("feedbackStrategy", "HIGHER_LOWER");

        UUID id = this.restTemplate.postForObject(URI + port + "/new", config, UUID.class);

        assertNotNull(id);
        assertEquals(id.getClass(), UUID.class);
    }

    @Test
    void gameInfoReturnsValidGameState() throws Exception {
        UUID id = this.restTemplate.postForObject(URI + port + "/new", new JSONObject(), UUID.class);
        String infoResponse = this.restTemplate.getForObject(URI + port + "/gameInfo?id=" + id, String.class);

        GameState actual = new ObjectMapper().readValue(infoResponse, GameState.class);
        GameState expected = new GameState(4, 8, 10, false, 0);

        assertNotNull(id);
        assertNotNull(infoResponse);
        assertEquals(expected, actual);
    }

    @Test
    void gameInfoReturnsGameNotFound() {
        String infoResponse = this.restTemplate.getForObject(URI + port + "/gameInfo?id=" + UUID.randomUUID(), String.class);

        assertNotNull(infoResponse);
        assertEquals("Game not found", infoResponse);
    }

    @Test
    void gameAcceptsGuessAndReturnsFeedback() {
        UUID id = this.restTemplate.postForObject(URI + port + "/new", new JSONObject(), UUID.class);
        List<Integer> guess = List.of(1, 2, 3, 4);
        String feedback = this.restTemplate.postForObject(URI + port + "/guess?id=" + id, guess, String.class);

        assertNotNull(id);
        assertNotNull(feedback);
        assertTrue(feedback.matches("^\\d+ correct numbers, \\d+ correctly placed$"));
    }

    @Test
    void gameRejectsInvalidGuess() {
        UUID id = this.restTemplate.postForObject(URI + port + "/new", new JSONObject(), UUID.class);
        List<Integer> guess = List.of(1, 2, 3, 4, 5, 6, 7);
        String feedback = this.restTemplate.postForObject(URI + port + "/guess?id=" + id, guess, String.class);

        assertNotNull(id);
        assertNotNull(feedback);
        assertTrue(feedback.contains("Invalid code length!"));
    }

    @Test
    void gameIsWonWithCorrectGuess() {
        JSONObject config = new JSONObject();
        config.put("secretCode", List.of(1, 2, 3, 4));
        config.put("codeSupplierPreference", "USER_DEFINED");

        UUID id = this.restTemplate.postForObject(URI + port + "/new", config, UUID.class);
        List<Integer> guess = List.of(1, 2, 3, 4);
        String feedback = this.restTemplate.postForObject(URI + port + "/guess?id=" + id, guess, String.class);

        assertNotNull(id);
        assertNotNull(feedback);
        assertTrue(feedback.matches("^4 correct numbers, 4 correctly placed$"));
    }
}
