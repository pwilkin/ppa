package org.ilintar.study.question;

import java.util.ArrayList;
import java.util.List;

import org.ilintar.study.question.event.QuestionAnsweredEvent;
import org.ilintar.study.question.event.QuestionAnsweredEventListener;


import javafx.scene.Node;

public abstract class QuestionImp implements IQuestion {
	
	protected Node renderedQuestion;
	protected String id;
	protected List<QuestionAnsweredEventListener> listeners;
	

	public QuestionImp(Node renderedQuestion, String id) {
		this.id = id;
		this.renderedQuestion = renderedQuestion;
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
	
	public void fireEvent() {
		QuestionAnsweredEvent event = new QuestionAnsweredEvent(this, getAnswer());
		for (QuestionAnsweredEventListener listener : listeners) {
			listener.handleEvent(event);
		}

	}


}


// maincontroler od Konrada liczenie pytań
// printwriter od Ewy

