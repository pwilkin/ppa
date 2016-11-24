package org.ilintar.study.question.event;

import org.ilintar.study.question.Answer;
import org.ilintar.study.question.Question;

public class QuestionAnsweredEvent {

	protected Question question;
	protected Answer answer;
	
	public QuestionAnsweredEvent(Question question, Answer answer) {
		this.question = question;
		this.answer = answer;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public Answer getAnswer() {
		return answer;
	}

	public void setAnswer(Answer answer) {
		this.answer = answer;
	}
	
	
	
}
