package org.ilintar.study.question;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class RadioQuestionFactory implements QuestionFactory {

	@Override
	public Question createQuestion(List<String> lines, String questionID) {
		VBox questionBox = new VBox();
		String questionString = lines.get(0);
		questionBox.getChildren().add(new Label(questionString));
		ToggleGroup answerGroup = new ToggleGroup();
		Map<Object, Answer> answers = new HashMap<>();
		for (int i = 1; i < lines.size(); i+=2) {
			String answerString = lines.get(i);
			String answerCode = lines.get(i+1);
			Answer answer = new AnswerImp(answerString, answerCode);
			RadioButton answerButton = new RadioButton(answer.getAnswerString());
			answers.put(answerButton, answer); // ?
			answerButton.setUserData(answer.getAnswerCode());
			answerButton.setToggleGroup(answerGroup);
			questionBox.getChildren().add(answerButton);
		}
		questionBox.onContextMenuRequestedProperty();
		Question question = new RadioQuestion (questionID, answers, questionBox);

		Button submit = new Button();
		submit.setText("Submit");
		submit.setOnAction(event -> question.fireEvent());

		questionBox.getChildren().add(submit);
		return question;
	}
//  TODO: Button if.
}
