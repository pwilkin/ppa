package org.ilintar.study.question;

import javafx.scene.Node;
import org.ilintar.study.question.event.QuestionAnsweredEvent;
import org.ilintar.study.question.event.QuestionAnsweredEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Konrad on 2016-12-20.
 */

public abstract class QuestionImp implements Question {

    List<QuestionAnsweredEventListener> listeners = new ArrayList<>();

    protected String questionID;
    protected Map answers;

    public QuestionImp(String questionID, Map<Object, Answer> answers) {
        this.questionID = questionID;
        this.answers = answers;
    }

    @Override
    public void fireEvent() {
        QuestionAnsweredEvent event = new QuestionAnsweredEvent(this, getSelectedAnswer());
        for (QuestionAnsweredEventListener listener : this.listeners) {
            listener.handleEvent(event);
        }
    }

    public abstract Answer getSelectedAnswer();

    public abstract Node getRenderedQuestion();

    @Override
    public String getId() {
        return questionID;
    }

    @Override
    public void addQuestionAnsweredListener(QuestionAnsweredEventListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeQuestionAnsweredListener(QuestionAnsweredEventListener listener) {
        listeners.remove(listener);
    }
}
