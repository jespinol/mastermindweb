package org.jmel.mastermindweb;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GameController.class)
public class RequestBodyTests {
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
                .andExpect(status().isOk())
                .andReturn(); // TODO: this should check that the session has the configuration above
    }

    @Test
    void gameAcceptsGuessAndReturnsFeedback() throws Exception {
        MvcResult sessionId = mockMvc.perform(post("/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isOk())
                .andReturn();

        UUID id = UUID.fromString(sessionId.getResponse().getContentAsString().replace("\"", ""));

        ObjectMapper mapper = new ObjectMapper();
        String bodyJson = mapper.writer().writeValueAsString(List.of(1, 2, 3, 4));

        MvcResult feedback = mockMvc.perform(post("/guess?id=" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bodyJson))
                .andExpect(status().isOk())
                .andReturn(); // TODO: This should check that the feedback is returned and correct
    }
}
