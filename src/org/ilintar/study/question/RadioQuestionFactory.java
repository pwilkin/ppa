package org.ilintar.study.question;

import java.util.List;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

public class RadioQuestionFactory implements QuestionFactory {

	@Override
	public Node createQuestion(List<String> lines) {
		VBox questions = new VBox();
		String question = lines.get(0);
		questions.getChildren().add(new Label(question));
		ToggleGroup group = new ToggleGroup();
		for (int i = 1; i < lines.size(); i+=2) {
			String answer = lines.get(i);
			String answerCode = lines.get(i+1);
			RadioButton button = new RadioButton(answer);
			button.setUserData(answerCode);
			button.setToggleGroup(group);
			questions.getChildren().add(button);
		}
		questions.onContextMenuRequestedProperty();
		return questions;
	}

}
