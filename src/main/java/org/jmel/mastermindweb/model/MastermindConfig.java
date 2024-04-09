package org.jmel.mastermindweb.model;

import java.util.ArrayList;
import java.util.List;

public class MastermindConfig { // TODO: record? use builder?
    private int codeLength = 4;
    private int numColors = 8;
    private int maxAttempts = 10;
    private String codeSupplierPreference = "RANDOM_ORG_API";
    private List<Integer> secretCode = new ArrayList<>();
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
