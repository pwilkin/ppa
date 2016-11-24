package org.ilintar.study.question;

import java.util.List;

import javafx.scene.Node;

public interface QuestionFactory {
		
	public Node createQuestion(List<String> lines);
	
}
