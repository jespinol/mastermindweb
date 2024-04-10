package org.jmel.mastermindweb.customfeedback;

import org.jmel.mastermind.core.feedbackstrategy.Feedback;

public record EncouragingFeedback(String message) implements Feedback {
    @Override
    public String toString() {
        return message;
    }
}
