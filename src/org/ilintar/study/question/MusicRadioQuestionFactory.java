package org.ilintar.study.question;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

import java.util.List;

/**
 * Created by Konrad on 2017-01-02.
 */
public class MusicRadioQuestionFactory extends RadioQuestionFactory {
    @Override
    public MusicRadioQuestion createQuestion(List<String> lines, String id) {
        VBox questions = new VBox();
        String question1 = lines.get(0);
        questions.getChildren().add(new Label(question1));
//        String labelId = "id= " + id; // KZ: it definitely does not look like well in the user view
//        questions.getChildren().add(new Label(labelId));
        ToggleGroup group = new ToggleGroup();
        for (int i = 1; i < lines.size(); i += 2) {
            String answer = lines.get(i);
            String answerCode = lines.get(i + 1);
            RadioButton button = new RadioButton(answer);
            button.setUserData(answerCode);
            button.setToggleGroup(group);
            questions.getChildren().add(button);
        }
        MusicRadioQuestion question = new MusicRadioQuestion(questions, id, group);
        Button finishButton = new Button("Submit");
        finishButton.setOnAction((event) -> {
            question.fireEvent();
        });
        questions.getChildren().add(finishButton);
        questions.onContextMenuRequestedProperty();
        return question;
    }

}