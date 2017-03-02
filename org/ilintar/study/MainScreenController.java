package org.ilintar.study;

import com.sun.org.apache.xerces.internal.xs.StringList;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import org.ilintar.study.question.*;
import org.ilintar.study.question.event.RadioQuestionAnswerListener;
import org.ilintar.study.question.event.WriteQuestionAnswerListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainScreenController {

    protected static Map<String, QuestionFactory> factoryMap;

    private AnswerHolder answerHolder = new SimpleAnswerHolder();
    private BufferedReader openedFile;
    private Node currentQuestionComponent;

    private int resultsHandlerCount = 0;

    static {
        factoryMap = new HashMap<>();
        factoryMap.put("radio", new RadioQuestionFactory());
        factoryMap.put("radiowithtime", new RadioWithTimeQuestionFactory());
        factoryMap.put("write", new WriteQuestionFactory());
        factoryMap.put("writewithtime", new WriteWithTimeQuestionFactory());
        // / słowo klucz wskazujace której konkretnie fabryki chcemy użyć
    }

    @FXML
    AnchorPane mainStudy;

    @FXML
    public void startStudy() throws IOException {
        mainStudy.getChildren().clear();
        openedFile = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("StudyDetails.sqf")));
        currentQuestionComponent = readNextQuestionFromFile();
        mainStudy.getChildren().add(currentQuestionComponent);
    }

    private Node readNextQuestionFromFile() throws IOException {
        String questionStartLine = getQuestionStartLine();
        if (questionStartLine != null) {
            String questionType = getQuestionType(questionStartLine);
            List<String> questionLines = readQuestionLines();
            return createQuestion(questionLines, questionType);
        }
        else {
            //finalScreen();
            return null;
        }
    }

    private String getQuestionStartLine() throws IOException {
        String currentLine;
        currentLine = openedFile.readLine();
        if (currentLine == null)
            return null;
        if (!currentLine.startsWith("StartQuestion"))
            throw new IllegalArgumentException("Question does not start properly");
        return currentLine;
    }

    private String getQuestionType(String startLine) {
        String questionType = null;
        String[] split = startLine.split(" ");
        if (split.length > 1) {
            String[] split2 = split[1].split("=");
            if (split2.length > 1) {
                questionType = split2[1];
            }
        }
        if (factoryMap.containsKey(questionType))
            return questionType;
        else
            throw new IllegalArgumentException("InvalidQuestionType");
    }

    private List<String> readQuestionLines() throws IOException {
        List<String> questionLines = new ArrayList<>();
        String currentLine;
        while ((currentLine = openedFile.readLine()) != null) {
            if (currentLine.startsWith("EndQuestion"))
                return questionLines;
            else
                questionLines.add(currentLine.trim());
        }
        throw new IllegalArgumentException("No end question mark");
    }

    private Node createQuestion(List<String> questionLines, String questionType) {
        Question q = factoryMap.get(questionType).createQuestion(questionLines);
        if(questionType.equals("radio")) {
            q.addQuestionAnsweredListener(new RadioQuestionAnswerListener(answerHolder, this));
        }
        else if (questionType.equals("write")) {
            q.addQuestionAnsweredListener(new WriteQuestionAnswerListener(answerHolder, this));
        }
        //TODO: dodac nastepne typy pytan, wygenerowalem juz klasy
        return q.getRenderedQuestion();
    }

    public void getNewQuestion() throws IOException {
        mainStudy.getChildren().remove(currentQuestionComponent);
        currentQuestionComponent = readNextQuestionFromFile();
        if (currentQuestionComponent == null) {
            finalScreen();
            return;
        }
        mainStudy.getChildren().add(currentQuestionComponent);
    }

    private void finalScreen(){
        mainStudy.getChildren().add(new Text(50 , 50, "Thank you for participating in this study.\n"));
        Button exitButton = new Button("Exit");
        Button resultsButton = new Button("See results");
        //int resultsHandlerCount = 0;

        exitButton.relocate(mainStudy.getWidth()/2, mainStudy.getHeight()/2);
        resultsButton.relocate(mainStudy.getWidth()/4, mainStudy.getHeight()/2);

        //exitButton.setOnAction(e -> System.out.println());
        exitButton.setOnAction(new EventHandler<javafx.event.ActionEvent>() {   //zamienne z lambdą
            @Override
            public void handle(javafx.event.ActionEvent event) {
                Platform.exit();
            }
        });
        resultsButton.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                    resultsScreen();
            }
        });

        try {
            mainStudy.getChildren().add(exitButton);
            mainStudy.getChildren().add(resultsButton);
        } catch(Exception e) {
            e.printStackTrace();
        }

    }
    private void resultsScreen(){           //TODO: wyniki w oknie a nie konsoli

        mainStudy.getChildren().clear();
        TextArea txta = new TextArea();
        txta.setEditable(false);
        mainStudy.getChildren().add(new Text(10,10, "Thank you for participating in this study.\n\nResults:"));
        int i = 0;
        for (Object o: answerHolder.getAnswers()) {               //ufff, getAnswers() zwraca odpowiedzi jako ArrayList
            txta.setText(txta.getText() + "Question " + i + ": " + o.toString() + "\n");
            i++;
        }
        mainStudy.getChildren().add(txta);
    }

    public void generateStudy(ActionEvent actionEvent) { //TODO: interfejs do generowania .sqf dla naszego programu

        mainStudy.getChildren().clear();
        mainStudy.getChildren().add(new Text(50,50,"It will be done"));



    }
        //TODO: moznaby tez zrobic przycisk "Go back" ktory bylby odziedziczone przez niektóre okna (ale to trzeba zrobic w mainie
        //TODO: uzywajac stage.setScene()?

}

	

