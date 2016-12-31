package org.ilintar.study.question.event;

public interface QuestionAnsweredEventNotifier {
	//produkuje zdarzenia, jest źródłem zdarzeń
	//wysyła zdarzenie do jednego lub więcej listenerów
	//obiekt ktory jest notifierem musi rejestrowac w sobie listenerow
	// fire...event --> wez listenerow i wywolaj w nim handleEvent


	void addQuestionAnsweredListener(QuestionAnsweredEventListener listener);
	void removeQuestionAnsweredListener(QuestionAnsweredEventListener listener);

}
