package org.ilintar.study;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import org.ilintar.study.question.*;
import org.ilintar.study.question.event.QuestionAnsweredEvent;
import org.ilintar.study.question.event.QuestionAnsweredEventListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainScreenController  implements QuestionAnsweredEventListener {
	
	protected static Map<String, QuestionFactory> factoryMap;
	
	static {
		factoryMap = new HashMap<>();
		factoryMap.put("radio", new RadioQuestionFactory());
	}

	//Storage of already answered & gathered questions from current study;
    //questions are stored as their IDs to save memory; answers are stored as Answer objects.
	private Map<String, Answer> collectedAnswers = new HashMap<>();

    @FXML AnchorPane mainStudy;

    //Just a self-explanatory dummy variable for startStudy():
    int questionCounter = 0;
    //AND NOW FOR STH COMPLETELY DIFFERENT:
	@FXML public void startStudy() {
	    //Clear the pane:
		mainStudy.getChildren().clear();
		//Create a new Question object basing on the input file:
        RadioQuestion createdQuestion = (RadioQuestion) readQuestionFromFile(questionCounter,
                getClass().getResourceAsStream("StudyDetails.sqf"));
		//Add the question's graphical component to the pane:
        mainStudy.getChildren().add(createdQuestion.getRenderedQuestion());
        //Create new button for question ending:
        Button questionAnsweredButton = new Button("Zakończ pytanie");
        //Add the button to the pane:
        mainStudy.getChildren().add(questionAnsweredButton);
        //Set button's reaction:
        questionAnsweredButton.setOnAction((event) -> {
            mainStudy.getChildren().remove(createdQuestion);
            questionCounter++;
            RadioQuestion tempQuestion = (RadioQuestion) readQuestionFromFile(questionCounter,
                    getClass().getResourceAsStream("StudyDetails.sqf"));
            mainStudy.getChildren().add(tempQuestion.getRenderedQuestion());
        });
	}

	//The .sqf parser:
	private Question readQuestionFromFile(int questionCounter, InputStream resourceAsStream) {
		BufferedReader br = new BufferedReader(new InputStreamReader(resourceAsStream));
		String currentLine;
		int counter = 0;
		List<String> questionLines = new ArrayList<>();
		boolean readingQuestions = false;
		String questionType = null;
		try {
			while ((currentLine = br.readLine()) != null) {
				if (currentLine.startsWith("StartQuestion")) {
					if (readingQuestions) {
						throw new IllegalArgumentException("Invalid file format: StartQuestion without EndQuestion");
					}
					if (counter == questionCounter) {
						readingQuestions = true;
						String[] split = currentLine.split(" ");
						if (split.length > 1) {
							String[] split2 = split[1].split("=");
							if (split2.length > 1) {
								questionType = split2[1];
							}
						}
						if (questionType == null) {
							throw new IllegalArgumentException("Invalid file format: StartQuestion type=<type>");
						}
					} else {
						counter++;
					}
				} else {
					if (readingQuestions) {
						if (currentLine.startsWith("EndQuestion")) {
							if (factoryMap.containsKey(questionType)) {
								return factoryMap.get(questionType).createQuestion(questionLines);
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

	//Method from the QuestionAnsweredListener interface.
    //Puts the current question & its answer into the collectedAnswers HashMap and reads the next question.
	public void handleQuestionAnsweredEvent(QuestionAnsweredEvent e) {
	    this.collectedAnswers.put(e.getQuestion().getId(), e.getAnswer());
    }
	
}
