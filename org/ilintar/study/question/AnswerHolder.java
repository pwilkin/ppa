package org.ilintar.study.question;

import java.util.Collection;
import java.util.Objects;


public interface AnswerHolder {
    void putAnswer(Object answer);
    Collection<Objects> getAnswers();
}
