package org.jmel.mastermindweb.model;

import org.jmel.mastermind.core.feedbackstrategy.FeedbackStrategy;
import org.jmel.mastermind.core.secretcodesupplier.CodeSupplierPreference;

import java.util.ArrayList;
import java.util.List;

import static org.jmel.mastermind.core.feedbackstrategy.FeedbackStrategyImpl.DEFAULT;
import static org.jmel.mastermind.core.secretcodesupplier.CodeSupplierPreference.RANDOM_ORG_API;

public class MastermindConfig { // TODO: record? use builder?
    private int codeLength = 4;
    private int numColors = 8;
    private int maxAttempts = 10;
    private CodeSupplierPreference codeSupplierPreference = RANDOM_ORG_API;
    private List<Integer> secretCode = new ArrayList<>();
    private FeedbackStrategy feedbackStrategy = DEFAULT;

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

    public CodeSupplierPreference getCodeSupplierPreference() {
        return codeSupplierPreference;
    }

    public void setCodeSupplierPreference(CodeSupplierPreference codeSupplierPreference) {
        this.codeSupplierPreference = codeSupplierPreference;
    }

    public List<Integer> getSecretCode() {
        return secretCode;
    }

    public void setSecretCode(List<Integer> secretCode) {
        this.secretCode = secretCode;
    }

    public FeedbackStrategy FeedbackStrategy() {
        return feedbackStrategy;
    }

    public void setFeedbackStrategy(FeedbackStrategy feedbackStrategy) {
        this.feedbackStrategy = feedbackStrategy;
    }
}
