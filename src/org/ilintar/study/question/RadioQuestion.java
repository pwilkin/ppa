package org.ilintar.study.question;

import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import org.ilintar.study.question.event.QuestionAnsweredEvent;
import org.ilintar.study.question.event.QuestionAnsweredEventListener;

import java.util.ArrayList;

/**
 * Created by RJ on 2016-12-01.
 */
//Class for standard radio questions.
public class RadioQuestion  implements Question {
    //Visible part of the question (javafx Node);
    private VBox empiricalPart;
    //QuestionID, specified at the constructor method:
    private String questionID;
    //Question's answer, initialised here & added later on:
    private Answer answer;
    //List of active listeners:
    private ArrayList<QuestionAnsweredEventListener> listeners;

    //Constructor method:
    public RadioQuestion() {
        this.listeners = new ArrayList<>();
    }

    //Adds new listener to the list:
    @Override
    public void addQuestionAnsweredListener(QuestionAnsweredEventListener listener) {
        listeners.add(listener);
    }

    //Removes the listener from the list:
    @Override
    public void removeQuestionAnsweredListener(QuestionAnsweredEventListener listener) {
        listeners.remove(listener);
    }

    //Create new event carrying the question itself & its answer; then return the event:
    public QuestionAnsweredEvent emitEvent() {
        return new QuestionAnsweredEvent(this, this.answer);
    }

    //Returns the question wrapped in a Node object (for further use by the controller);
    @Override
    public Node getRenderedQuestion(){
        return this.getEmpiricalPart();
    }

    //Getter for the question's ID:
    @Override
    public String getId() {
        return questionID;
    }

    //Setter for the question's answer;
    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    //Getter fot the graphical component:
    public VBox getEmpiricalPart() {
        return this.empiricalPart;
    }

    //Setter for the graphical component:
    public void setEmpiricalPart(VBox empiricalPart) {
        this.empiricalPart = empiricalPart;
    }
}
