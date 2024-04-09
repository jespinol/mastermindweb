package org.jmel.mastermindweb;

import org.springframework.stereotype.Component;

@Component
public class MastermindConfig {
    private int codeLength;
    private int numColors;
    private int maxAttempts;
    private String codeSupplierPreference;
    private String secretCode;
    private String feedbackStrategy;

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

    public String getSecretCode() {
        return secretCode;
    }

    public void setSecretCode(String secretCode) {
        this.secretCode = secretCode;
    }

    public String getFeedbackStrategy() {
        return feedbackStrategy;
    }

    public void setFeedbackStrategy(String feedbackStrategy) {
        this.feedbackStrategy = feedbackStrategy;
    }
}
