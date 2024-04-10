package org.jmel.mastermindweb.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class MastermindConfig { // TODO: record? use builder?
    @JsonProperty(required = false)
    private int codeLength = 4;

    @JsonProperty(required = false)
    private int numColors = 8;

    @JsonProperty(required = false)
    private int maxAttempts = 10;

    @JsonProperty(required = false)
    private String codeSupplierPreference = "RANDOM_ORG_API";

    @JsonProperty(required = false)
    private List<Integer> secretCode = new ArrayList<>();

    @JsonProperty(required = false)
    private String feedbackStrategy = "DEFAULT";

    public int getCodeLength() {
        return codeLength;
    }

    public void setCodeLength(int codeLength) {
        this.codeLength = codeLength;
    }

    public int getNumColors() {
        return numColors;
    }

    public void setNumColors(int numColors) {
        this.numColors = numColors;
    }

    public int getMaxAttempts() {
        return maxAttempts;
    }

    public void setMaxAttempts(int maxAttempts) {
        this.maxAttempts = maxAttempts;
    }

    public String getCodeSupplierPreference() {
        return codeSupplierPreference;
    }

    public void setCodeSupplierPreference(String codeSupplierPreference) {
        this.codeSupplierPreference = codeSupplierPreference;
    }

    public List<Integer> getSecretCode() {
        return secretCode;
    }

    public void setSecretCode(List<Integer> secretCode) {
        this.secretCode = secretCode;
    }

    public String FeedbackStrategy() {
        return feedbackStrategy;
    }

    public void setFeedbackStrategy(String feedbackStrategy) {
        this.feedbackStrategy = feedbackStrategy;
    }
}
