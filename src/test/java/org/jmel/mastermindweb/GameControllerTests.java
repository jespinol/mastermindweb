package org.jmel.mastermindweb;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jmel.mastermindweb.controller.GameController;
import org.jmel.mastermindweb.model.GameStateRecord;
import org.jmel.mastermindweb.model.MastermindConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.UUID;

import static org.jmel.mastermind.core.feedbackstrategy.FeedbackStrategyImpl.DEFAULT;
import static org.jmel.mastermind.core.secretcodesupplier.CodeSupplierPreference.LOCAL_RANDOM;
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
        mockMvc.perform(get("/new")
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

        mockMvc.perform(get("/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bodyJson))
                .andExpect(status().isOk())
                .andReturn(); // TODO: this should check that the session has the configuration above
    }

    @Test
    void gameAcceptsGuessAndReturnsFeedback() throws Exception {
        MvcResult sessionId = mockMvc.perform(get("/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isOk())
                .andReturn();


        ObjectMapper mapper = new ObjectMapper();
        String bodyJson = mapper.writer().writeValueAsString(List.of(1, 2, 3, 4));
        UUID id = UUID.fromString(sessionId.getResponse().getContentAsString().replace("\"", ""));
        MvcResult feedback = mockMvc.perform(post("/guess?id=" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bodyJson))
                .andExpect(status().isOk())
                .andReturn(); // TODO: This should check that the feedback is returned and correct
    }

    @Test
    void gameInfoReturnsValidGameState() throws Exception {
        MvcResult idResponse = mockMvc.perform(get("/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isOk())
                .andReturn();

        UUID id = UUID.fromString(idResponse.getResponse().getContentAsString().replace("\"", ""));
        String infoResponse = mockMvc.perform(get("/gameInfo?id=" + id))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        String expectedResponse = new GameStateRecord(4, 8, 10, false, 0).toString();

        assertEquals(expectedResponse, infoResponse);
    }

    @Test
    void gameInfoReturnsGameNotFound() throws Exception {
        String response = mockMvc.perform(get("/gameInfo?id=" + UUID.randomUUID()))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals("Game not found", response);
    }
}
