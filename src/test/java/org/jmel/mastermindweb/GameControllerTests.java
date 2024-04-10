package org.jmel.mastermindweb;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jmel.mastermindweb.controller.GameController;
import org.jmel.mastermindweb.dto.GameState;
import org.jmel.mastermindweb.dto.MastermindConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GameController.class)
public class GameControllerTests {
    @Autowired
    MockMvc mockMvc;

    @Test
    void sessionWithDefaultGameIsCreatedSuccessfully() throws Exception {
        mockMvc.perform(post("/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isOk());
    }

    @Test
    void sessionWithCustomizedGameIsCreatedSuccessfully() throws Exception {
        MastermindConfig config = new MastermindConfig();
        config.setCodeLength(4);
        config.setNumColors(8);
        config.setMaxAttempts(10);
        config.setCodeSupplierPreference("LOCAL_RANDOM");
        config.setFeedbackStrategy("DEFAULT");

        ObjectMapper mapper = new ObjectMapper();
        String bodyJson = mapper.writer().writeValueAsString(config);

        mockMvc.perform(post("/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bodyJson))
                .andExpect(status().isOk());
    }

    @Test
    void gameAcceptsGuessAndReturnsFeedback() throws Exception {
        MvcResult sessionId = mockMvc.perform(post("/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isOk())
                .andReturn();


        ObjectMapper mapper = new ObjectMapper();
        String bodyJson = mapper.writer().writeValueAsString(List.of(1, 2, 3, 4));
        UUID id = UUID.fromString(sessionId.getResponse().getContentAsString().replace("\"", ""));
        mockMvc.perform(post("/guess?id=" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bodyJson))
                .andExpect(status().isOk());
    }

    @Test
    void gameInfoReturnsValidGameState() throws Exception {
        MvcResult idResponse = mockMvc.perform(post("/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isOk())
                .andReturn();

        UUID id = UUID.fromString(idResponse.getResponse().getContentAsString().replace("\"", ""));
        String infoResponse = mockMvc.perform(get("/gameInfo?id=" + id))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        GameState actual = new ObjectMapper().readValue(infoResponse, GameState.class);
        GameState expected = new GameState(4, 8, 10, false, 0);

        assertEquals(expected, actual);
    }

    @Test
    void gameInfoReturnsGameNotFound() throws Exception {
        mockMvc.perform(get("/gameInfo?id=" + UUID.randomUUID()))
                .andExpect(status().isNotFound());

    }
}
