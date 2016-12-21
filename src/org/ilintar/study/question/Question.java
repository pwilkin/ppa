package org.ilintar.study.question;

import org.ilintar.study.question.event.QuestionAnsweredEventNotifier;

import javafx.scene.Node;

public interface Question extends QuestionAnsweredEventNotifier {

	Node getRenderedQuestion();
	String getId();

	public void fireEvent();

	Answer getSelectedAnswer();
	
}
