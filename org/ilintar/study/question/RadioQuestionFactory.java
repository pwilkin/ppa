package org.ilintar.study.question;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

import java.util.List;

public class RadioQuestionFactory implements QuestionFactory {

	@Override
	public Question createQuestion(List<String> lines) {
		VBox question = new VBox(); //VBox lays out its children in a single vertical column
		String questionText = lines.get(0);
		question.getChildren().add(new Label(questionText));
		ToggleGroup group = new ToggleGroup(); //grupa przycisków wewnątrz tylko jeden może być zaznaczony
		for (int i = 1; i < lines.size(); i+=2) {
			String answer = lines.get(i);
			String answerCode = lines.get(i+1);
			RadioButton button = new RadioButton(answer);
			button.setUserData(answerCode);
			button.setToggleGroup(group);
			question.getChildren().add(button);
		}
		question.onContextMenuRequestedProperty();
		Button nextButton = createNextButton();
		return new RadioQuestion(question, group, nextButton);
	}

	private Button createNextButton() {
		Button nextButton = new Button("Next");
		return nextButton;
	}
}
