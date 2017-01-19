package org.ilintar.study.question.event;

import org.ilintar.study.question.Answer;
import org.ilintar.study.question.IQuestion;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


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

	public void saveToFile() {

		try(FileWriter fw = new FileWriter("answers.answ", true);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter out = new PrintWriter(bw)){
			out.println("Id: " + question.getId() + " answer: " + answer.getAnswer());
		}
		catch( IOException e ){
			System.err.println("IOException: " + e.getMessage());
		}

	}


}