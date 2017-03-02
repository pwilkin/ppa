package org.ilintar.study.question.event;


import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import org.ilintar.study.MainScreenController;
import org.ilintar.study.question.AnswerHolder;

import java.io.IOException;

public class RadioQuestionAnswerListener implements QuestionAnsweredEventListener {

    private final AnswerHolder answerHolder;
    MainScreenController mainScreenController;


    public RadioQuestionAnswerListener(AnswerHolder answerHolder, MainScreenController mainScreenController) {
        this.answerHolder = answerHolder;
        this.mainScreenController = mainScreenController;
    }

    @Override
    public void handleEvent(ActionEvent event) {
        String answerCode = (String) ((Button) event.getSource()).getUserData();
        if (answerCode != null) {
            answerHolder.putAnswer(answerCode);
            //System.out.println("answerCode w handleEvent Radio = " + answerCode);
            try {
                mainScreenController.getNewQuestion();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            System.out.println("DupaRadio!");

        }
    }
}
