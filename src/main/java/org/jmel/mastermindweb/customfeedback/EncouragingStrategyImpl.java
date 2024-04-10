package org.jmel.mastermindweb.customfeedback;

import org.jmel.mastermind.core.Code;
import org.jmel.mastermind.core.feedbackstrategy.Feedback;
import org.jmel.mastermind.core.feedbackstrategy.FeedbackStrategy;

import java.util.List;

public class EncouragingStrategyImpl implements FeedbackStrategy {
    @Override
    public Feedback get(Code c1, Code c2) {
        List<String> messages = List.of(
                "Keep going!",
                "Great job!",
                "You're doing amazing!",
                "Great effort!",
                "Hang in there!");

        return new EncouragingFeedback(messages.get((int) (Math.random() * messages.size())));
    }

}
