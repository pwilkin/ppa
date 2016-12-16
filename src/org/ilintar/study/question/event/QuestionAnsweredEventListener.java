package org.ilintar.study.question.event;

public interface QuestionAnsweredEventListener {
    void handleQuestionAnsweredEvent(QuestionAnsweredEvent e);
}
