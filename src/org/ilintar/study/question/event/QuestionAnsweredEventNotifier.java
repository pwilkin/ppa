package org.ilintar.study.question.event;



public interface QuestionAnsweredEventNotifier {
	
	public void addQuestionAnsweredListener(QuestionAnsweredEventListener listener);
	public void removeQuestionAnsweredListener(QuestionAnsweredEventListener listener);
	
	public void fireEvent();
}
