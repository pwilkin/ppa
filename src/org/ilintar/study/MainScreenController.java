package org.ilintar.study;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.control.Label;
import org.ilintar.study.question.Answer;
import org.ilintar.study.question.IQuestion;
import org.ilintar.study.question.QuestionFactory;
import org.ilintar.study.question.RadioQuestionFactory;
import org.ilintar.study.question.event.QuestionAnsweredEvent;
import org.ilintar.study.question.event.QuestionAnsweredEventListener;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import org.ilintar.study.question.event.QuestionAnsweredEvent;
import org.ilintar.study.question.event.QuestionAnsweredEventListener;
import org.ilintar.study.question.event.QuestionAnsweredEvent;
import org.ilintar.study.question.event.QuestionAnsweredEventListener;

public class MainScreenController implements QuestionAnsweredEventListener {

    protected PrintWriter out;

	private int whichQuestion;

	public MainScreenController() throws FileNotFoundException {
        out = new PrintWriter("answers.answ");
		this.whichQuestion = 0;
	}

	private static Map<String, QuestionFactory> factoryMap;

	static {
		factoryMap = new HashMap<>();
		factoryMap.put("radio", new RadioQuestionFactory());
	}

	@FXML AnchorPane mainStudy;

    protected IQuestion currentQuestion;

	@FXML public void startStudy() {
		mainStudy.getChildren().clear();
		Node questionComponent = readQuestionFromFile(whichQuestion, getClass().getResourceAsStream("StudyDetails.sqf"));
		if (questionComponent != null){
            mainStudy.getChildren().add(questionComponent);
        }
        else {
            endStudy();
        }

	}

    private void endStudy() {
        mainStudy.getChildren().clear();
        mainStudy.getChildren().add(new Label("Thank you!"));
    }

    private Node readQuestionFromFile(int i, InputStream resourceAsStream) {
		BufferedReader br = new BufferedReader(new InputStreamReader(resourceAsStream));
		String currentLine;
		int which = 0;
		List<String> questionLines = new ArrayList<>();
		boolean readingQuestions = false;
		String questionType = null;
		String questionId = null;
		try {
			while ((currentLine = br.readLine()) != null) {
				if (currentLine.startsWith("StartQuestion")) { // begin reading questions
					if (readingQuestions) {
						throw new IllegalArgumentException("Invalid file format: StartQuestion without EndQuestion");
					}
					if (which == i) {
						readingQuestions = true;
						String[] elements = currentLine.split(" ");
						if (elements.length > 1) {
							String[] givenType = elements[1].split("=");
							if (givenType.length > 1) {
								questionType = givenType[1];
							}
							if (elements.length > 2){
								String[] givenID = elements[2].split("=");
								questionId = givenID[1];
							}
						}
						if (questionType == null) {
							throw new IllegalArgumentException("Invalid file format: StartQuestion type=<type>");
						}
						if (questionId == null) {
							throw new IllegalArgumentException("Invalid file format: StartQuestion ID=<ID>");
						}
					} else {
						which++;
					}
				} else {
					if (readingQuestions) {
						if (currentLine.startsWith("EndQuestion")) {
							if (factoryMap.containsKey(questionType)) {
								currentQuestion = factoryMap.get(questionType).createQuestion(questionLines, questionId);
								currentQuestion.addQuestionAnsweredListener(this);
								whichQuestion++;
								return currentQuestion.getRenderedQuestion();
							} else {
								throw new IllegalArgumentException("Do not have a factory for question type: " + questionType);
							}
						} else {						
							questionLines.add(currentLine.trim());
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void handleEvent(QuestionAnsweredEvent event) {
		IQuestion question = event.getQuestion();
		Answer answer = event.getAnswer();
		System.out.println(question.getId());
		System.out.println(answer.getAnswer());
        event.saveToFile();
        startStudy(); // this name is confusing, should be changed to 'changeQuestion' or smth
	}

}
