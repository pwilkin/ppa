package org.ilintar.study.question;

import java.util.List;

public interface QuestionFactory {
		
	Question createQuestion(List<String> lines);
	
}
