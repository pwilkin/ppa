package org.ilintar.study;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import org.ilintar.study.question.*;
import org.ilintar.study.question.event.QuestionAnsweredEvent;
import org.ilintar.study.question.event.QuestionAnsweredEventListener;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;


import javafx.scene.control.TitledPane;

public class MainScreenController implements QuestionAnsweredEventListener {

    protected PrintWriter out;
	private int whichQuestion;
	private String fileName;

	public MainScreenController() throws FileNotFoundException {
        out = new PrintWriter("answers.answ");
		this.whichQuestion = -1;
	}

	private static Map<String, QuestionFactory> factoryMap;
	static {
		factoryMap = new HashMap<>();
		factoryMap.put("radio", new RadioQuestionFactory());
		factoryMap.put("music", new MusicRadioQuestionFactory());
		factoryMap.put("image", new ImageRadioQuestionFactory());
	}

	@FXML AnchorPane mainStudy;
	@FXML Label fileNameLabel; 
	@FXML TitledPane titledPane;

    public IQuestion currentQuestion;

	int trackNumber = 1; // it's weird place for this var, but it don't destroy anything

	@FXML public void startStudy() {
		if (fileName == null) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Jest error");
			alert.setHeaderText("Brak pliku");
			alert.setContentText("Proszę wybrać plik z pytaniami");
			alert.showAndWait();
		} else {
			displayQuestion();
	    }
	}
	
	@FXML public void displayQuestion() {
        whichQuestion++;
		mainStudy.getChildren().clear();
		Node questionComponent = readQuestionFromFile(whichQuestion, getClass().getResourceAsStream(fileName));
		if (questionComponent != null){
			String questionTitle = "Pytanie " + String.valueOf(whichQuestion+1);
			titledPane.setText(questionTitle);
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
                                    MusicRadioQuestion musicQuestion = (MusicRadioQuestion) currentQuestion;
                                    musicQuestion.runTrack(trackNumber) ;
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
		IQuestion question = event.getQuestion(); // KS: this line!
		Answer answer = event.getAnswer();
		System.out.println(question.getId());
		System.out.println(answer.getAnswer());
        event.saveToFile();
		if (question instanceof MusicRadioQuestion){ // KS: we can use question = event.getQuestion(); instead of currentquestion here, can't we?
            // KZ: Yes, we can. ;)
            ((MusicRadioQuestion) question).terminateTrack();
		}
        displayQuestion(); 
	}

	public void chooseFile() {
		 FileChooser fileChooser = new FileChooser();
		 String currentDir = System.getProperty("user.dir") + File.separator + "src" + File.separator + "org" + File.separator + "ilintar" + File.separator + "study"+ File.separator;
         File file = new File(currentDir); // Should open in our working directory by default now.
         fileChooser.setInitialDirectory(file);
		 fileChooser.setTitle("Open Resource File");
		 fileChooser.getExtensionFilters().addAll(
				 new ExtensionFilter("Question Files", "*.sqf"),
		         new ExtensionFilter("Text Files", "*.txt"),
		         new ExtensionFilter("All Files", "*.*"));
		 File selectedFile = fileChooser.showOpenDialog(mainStudy.getScene().getWindow());
		 if (selectedFile != null){
			 fileName = selectedFile.getName();
			 fileNameLabel.setText(fileName);
	}}
	
}
