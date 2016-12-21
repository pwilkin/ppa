package org.ilintar.study.question;

import java.util.List;

import javafx.scene.Node;

public interface QuestionFactory {

	Question createQuestion(List<String> lines, String questionID);
	
}
