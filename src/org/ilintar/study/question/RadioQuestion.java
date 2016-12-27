package org.ilintar.study.question;

import java.util.ArrayList;
import java.util.List;

import org.ilintar.study.question.event.QuestionAnsweredEvent;
import org.ilintar.study.question.event.QuestionAnsweredEventListener;


import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleGroup;

public class RadioQuestion extends QuestionImpl {
	
//	protected Node renderedQuestion;
//	protected String id;
//	protected List<QuestionAnsweredEventListener> listeners;
//	protected Button finishButton;
	protected ToggleGroup group;
	
	

	public RadioQuestion(Node renderedQuestion, String id, Button finishButton, ToggleGroup group) {
		super(renderedQuestion, id, finishButton);
		//listeners = new ArrayList<>();
		this.group = group;
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


	@Override
	public Answer getAnswer() {
		Answer answer = new Answer((String) group.getSelectedToggle().getUserData());
		return answer;
	}






}
