package org.ilintar.study;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.control.Label;
import org.ilintar.study.question.*;
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
		this.whichQuestion = -1;
	}

	private static Map<String, QuestionFactory> factoryMap;

	static {
		factoryMap = new HashMap<>();
		factoryMap.put("radio", new RadioQuestionFactory());
		factoryMap.put("music", new MusicRadioQuestionFactory());
	}

	@FXML AnchorPane mainStudy;

    protected IQuestion currentQuestion;

	int trackNumber = 1; // it's weird place for this var, but it don't destroy anything

    String studyDetails = "StudyDetails.sqf"; // should be set by clicking corresponding button
//    String studyDetails = "MusicStudyDetails.sqf";

	@FXML public void startStudy() {
        whichQuestion++;
		mainStudy.getChildren().clear();
		Node questionComponent = readQuestionFromFile(whichQuestion, getClass().getResourceAsStream(studyDetails));
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
								if (questionType.equals("music")){
									MusicRadioQuestion currentQuestion = (MusicRadioQuestion) factoryMap.get(questionType).createQuestion(questionLines, questionId);
									currentQuestion.runTrack(trackNumber) ;
									trackNumber++;
								}
								currentQuestion.addQuestionAnsweredListener(this);
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
		if (currentQuestion instanceof MusicRadioQuestion){
            System.out.println(((MusicRadioQuestion)currentQuestion).getMediaPlayer());
            System.out.println(currentQuestion.getId());
            ((MusicRadioQuestion) currentQuestion).terminateTrack();
		}
        startStudy(); // this name is confusing, should be changed to 'changeQuestion' or smth
	}

}
