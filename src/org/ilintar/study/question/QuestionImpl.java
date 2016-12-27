package org.ilintar.study.question;

import java.util.ArrayList;
import java.util.List;

import org.ilintar.study.question.event.QuestionAnsweredEvent;
import org.ilintar.study.question.event.QuestionAnsweredEventListener;


import javafx.scene.Node;
import javafx.scene.control.Button;


public abstract class QuestionImpl implements IQuestion {
	
	protected Node renderedQuestion;
	protected String id;
	protected List<QuestionAnsweredEventListener> listeners;
	protected Button finishButton;
	

	public QuestionImpl(Node renderedQuestion, String id, Button finishButton) {
		this.id = id;
		this.renderedQuestion = renderedQuestion;
		this.finishButton = finishButton;
		this.finishButton.setOnAction((event) -> {fireEvent(getAnswer());});
		listeners = new ArrayList<>();
	}

	@Override
	public void addQuestionAnsweredListener(QuestionAnsweredEventListener listener) {
		listeners.add(listener);

	}

	@Override
	public void removeQuestionAnsweredListener(QuestionAnsweredEventListener listener) {
		listeners.remove(listener);

	}

	@Override
	public Node getRenderedQuestion() {
		return renderedQuestion;
	}

	@Override
	public String getId() {
		return id;
	}

	public abstract Answer getAnswer();
	
	public void fireEvent(Answer answer) {
		QuestionAnsweredEvent event = new QuestionAnsweredEvent(this, answer);
		for (QuestionAnsweredEventListener listener : listeners) {
			listener.handleEvent(event);
		}

	}


}
