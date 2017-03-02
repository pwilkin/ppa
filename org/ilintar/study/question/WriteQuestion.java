package org.ilintar.study.question;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import org.ilintar.study.question.event.QuestionAnsweredEventListener;

/**
 * Created by mariusz on 2016-12-29.
 */
public class WriteQuestion implements Question {

    private VBox vBox;
    private TextArea textArea;
    private Button nextButton;

    public WriteQuestion(VBox vBox, TextArea textArea, Button nextButton) {
        this.vBox = vBox;
        this.textArea = textArea;
        this.nextButton = nextButton;
        passWriteResult(textArea, nextButton);
    }

    private void passWriteResult(final TextArea textArea, final Button nextButton) {

        textArea.textProperty().addListener(new ChangeListener<String>() {              //kiedykolwiek wartosc textProperty() się zmienia wywołaj listener
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) { //to samo mozna zrobic lambdą, przesylajac argumenty do metody changed
                String text = textArea.getText();                                       //z anonimowej klasy ChangeListener
                //System.out.println(text);
                nextButton.setUserData(text);                                           //ustawiamy property UserData, ktorą przechwyci handler w listenerze
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
