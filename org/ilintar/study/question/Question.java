package org.ilintar.study.question;

import javafx.scene.Node;
import org.ilintar.study.question.event.QuestionAnsweredEventNotifier;

public interface Question extends QuestionAnsweredEventNotifier {

	public Node getRenderedQuestion();
	public String getId();


	
}
