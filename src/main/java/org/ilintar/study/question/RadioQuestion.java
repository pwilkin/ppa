package org.ilintar.study.question;


import org.ilintar.study.question.event.QuestionAnsweredEventListener;


import javafx.scene.Node;
import javafx.scene.control.ToggleGroup;

public class RadioQuestion extends QuestionImp {
	
	protected ToggleGroup group;
	
	

	public RadioQuestion(Node renderedQuestion, String id, ToggleGroup group) {
		super(renderedQuestion, id);
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
