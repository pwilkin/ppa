package org.ilintar.study.question;

import java.util.List;



public interface QuestionFactory {
		
	public IQuestion createQuestion(List<String> lines, String id);
	
}
