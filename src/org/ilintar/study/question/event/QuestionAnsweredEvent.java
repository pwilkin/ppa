package org.ilintar.study.question.event;

import org.ilintar.study.question.Answer;
import org.ilintar.study.question.Question;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

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

	public void saveToFile() {
		String questionID = question.getId();
		String answerID = answer.getAnswerCode();
		try {
			Files.write(Paths.get("answers.answ"), questionID.getBytes(), StandardOpenOption.APPEND);
			Files.write(Paths.get("answers.answ"), answerID.getBytes(), StandardOpenOption.APPEND);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}