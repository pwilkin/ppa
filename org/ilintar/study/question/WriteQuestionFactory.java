package org.ilintar.study.question;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import org.ilintar.study.question.Question;
import org.ilintar.study.question.QuestionFactory;

import java.util.List;

/**
 * Created by mariusz on 2016-12-29.
 */
public class WriteQuestionFactory implements QuestionFactory {

    @Override
    public Question createQuestion(List<String> lines) {
        VBox question = new VBox();
        String questionText = lines.get(0);

        TextArea textArea = new TextArea();
        textArea.setMaxHeight(10);              // inaczej nachodzilo na nextbuttona

        question.getChildren().add(new Label(questionText));
        question.getChildren().add(textArea);

        Button nextButton = createNextButton();
        return new WriteQuestion(question, textArea, nextButton);
    }

    private Button createNextButton() {
        Button nextButton = new Button("Next");
        return nextButton;
    }
}
