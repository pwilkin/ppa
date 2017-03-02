package org.ilintar.study.question;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import org.ilintar.study.question.event.QuestionAnsweredEventListener;

public class RadioQuestion implements Question {

    private VBox vBox;
    private ToggleGroup group;
    private Button nextButton;

    public RadioQuestion(VBox vBox, ToggleGroup group, Button nextButton) {
        this.vBox = vBox;
        this.group = group;
        this.nextButton = nextButton;
        passToggleResult(group, nextButton);
    }

    private void passToggleResult(final ToggleGroup group, final Button nextButton) {

        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                //System.out.println(group.getSelectedToggle());
                nextButton.setUserData(group.getSelectedToggle().getUserData().toString());
            }
        });
    }

    @Override
    public Node getRenderedQuestion() {
        vBox.getChildren().add(nextButton);
        return vBox;
    }

    @Override
    public String getId() {
        return vBox.getId();
    }

    @Override
    public void addQuestionAnsweredListener(QuestionAnsweredEventListener listener) {
        nextButton.setOnAction(listener::handleEvent);
    }

    @Override
    public void removeQuestionAnsweredListener(QuestionAnsweredEventListener listener) {

    }
}
