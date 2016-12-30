package org.ilintar.study.question.event;

import org.ilintar.study.question.Answer;
import org.ilintar.study.question.IQuestion;


public class QuestionAnsweredEvent {

	protected IQuestion question;
	protected Answer answer;
	
	public QuestionAnsweredEvent(IQuestion question, Answer answer) {
		this.question = question;
		this.answer = answer;
	}

	public IQuestion getQuestion() {
		return question;
	}

	public void setQuestion(IQuestion question) {
		this.question = question;
	}

	public Answer getAnswer() {
		return answer;
	}

	public void setAnswer(Answer answer) {
		this.answer = answer;
	}
	
	
	
}
