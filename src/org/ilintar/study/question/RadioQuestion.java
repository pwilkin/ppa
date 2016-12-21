package org.ilintar.study.question;

import javafx.scene.Node;
import javafx.scene.control.Toggle;
import javafx.scene.layout.VBox;

import java.util.Map;

/**
 * Created by Konrad on 2016-12-20.
 */
public class RadioQuestion extends QuestionImp {

    private VBox questionBox;

    public RadioQuestion(String questionID, Map<Object, Answer> answers, VBox questionBox) {
        super(questionID, answers);
        this.questionBox = questionBox;
    }

    @Override
    public Answer getSelectedAnswer() {
        Toggle firstToggle = (Toggle) questionBox.getChildren().get(1);
        Toggle selectedToggle = firstToggle.getToggleGroup().getSelectedToggle();
        return (Answer) answers.get(selectedToggle);
    }

    @Override
    public Node getRenderedQuestion() {
        return questionBox;
    }
}
